package com.tancheng.carbonchain.activities.asset.wallet.service.eth;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TransactionModel;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TransferData;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IchainService;
import com.tancheng.carbonchain.db.gen.TransationRecordDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java8.util.Optional;

/**
 * created by collin on 2020/3/18 14:48
 * Description: eth chainService
 * version: 1.0
 */
public class ETHChainServiceImpl implements IchainService {
    private final BigInteger defaultGasLimit = new BigInteger("21000");

    /**
     * 转账
     *
     * @param transferData
     * @return
     */
    @Override
    public boolean transferToken(TransferData transferData) {
        try {
            //查询keystore文件地址
            ETHWallet wallet = WalletDaoUtils.getIntence().getWalletByAddr(transferData.getFrom());
            if (wallet == null) {
                LogUtils.e("未查到转账钱包地址信息");
                return false;
            }

            RawTransaction rawTransaction = buildRawTransation(transferData);
            if (rawTransaction == null) {
                return false;
            }
            String txHash = signAndSend(rawTransaction, wallet.getKeystorePath(), transferData.getPassword());
            if (StringUtils.isEmpty(txHash)) {
                return false;
            }
            //入库
            TransactionRecord transaction = new TransactionRecord();
            transaction.setStatus(0);
            transaction.setValue(transferData.getValue().doubleValue());
            transaction.setTxHash(txHash);
            transaction.setToAddr(transferData.getTo());
            transaction.setFromAddr(transferData.getFrom());
            transaction.setWalletType(WalletType.ETH.getWalletType());
            transaction.setDirect(0);
            transaction.setTokenId((int) transferData.getTokenInfo().getId());
            transaction.setTimeStamp(new Date());
            transaction.setRemark(transferData.getRemark());
            TransationRecordDaoUtils txDaoUtil = TransationRecordDaoUtils.getIntence();
            txDaoUtil.insert(transaction);
            LogUtils.i("交易hash=> " + txHash);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("转帐代币出错：{}", e);
            return false;
        }
    }

    /**
     * 构建元交易
     *
     * @param transferData
     * @return
     */
    public RawTransaction buildRawTransation(TransferData transferData) {
        try {
            String from = transferData.getFrom();
            TokenType tokenInfo = transferData.getTokenInfo();
            String to = transferData.getTo();
            BigDecimal amount = transferData.getValue();

            Web3j web3j = Web3jUtil.getClint();
            EthGasPrice send = web3j.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            // BigInteger gasPrice = BigInteger.valueOf(50);
            // 获得nonce
            BigInteger nonce = getNonce(from);
            RawTransaction rawTransaction = null;
            if (tokenInfo == null || StringUtils.isEmpty(tokenInfo.getAddress())) { //转账ETH
                // value 转换
                BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
                // 构建交易
                Transaction transaction = Transaction.createEtherTransaction(from, nonce, gasPrice, null, to, value);
                // 计算gasLimit
                BigInteger gasLimit = getTransactionGasLimit(transaction);
                // 查询调用者余额，检测余额是否充足
                BigDecimal ethBalance = getBalance(from, "");
                BigDecimal balance = Convert.toWei(ethBalance, Convert.Unit.ETHER);
                // balance < amount + gasLimit ??
                if (balance.compareTo(amount.add(new BigDecimal(gasLimit.toString()))) < 0) {
                    throw new RuntimeException("余额不足，请核实");
                }
                rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, "");
            } else {//转账erc20
                // 构建方法调用信息
                String method = "transfer";
                // 构建输入参数
                List<Type> inputArgs = new ArrayList<>();
                inputArgs.add(new Address(to));
                inputArgs.add(new Uint256(amount.multiply(BigDecimal.TEN.pow(tokenInfo.getDecimals())).toBigInteger()));
                // 合约返回值容器
                List<TypeReference<?>> outputArgs = new ArrayList<>();
                String funcABI = FunctionEncoder.encode(new Function(method, inputArgs, outputArgs));
                Transaction transaction = Transaction.createFunctionCallTransaction(from, nonce, gasPrice, null, tokenInfo.getAddress(), funcABI);
                BigInteger gasLimit = getTransactionGasLimit(transaction);
                LogUtils.i("gasLimit:", gasLimit);
                // 获得余额
                BigDecimal ethBalance = getBalance(from, "");
                BigDecimal tokenBalance = getBalance(from, tokenInfo.getAddress());
                BigInteger balance = Convert.toWei(ethBalance, Convert.Unit.ETHER).toBigInteger();
                if (balance.compareTo(gasLimit) < 0) {
                    throw new RuntimeException("手续费不足，请核实");
                }
                if (tokenBalance.compareTo(amount) < 0) {
                    throw new RuntimeException("代币不足，请核实");
                }
                rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, tokenBalance.toBigInteger(), funcABI);
            }
            return rawTransaction;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("转帐代币出错：{}", e);
            return null;
        }
    }

    /**
     * 对交易签名，并发送交易
     *
     * @param rawTransaction
     * @return
     */
    public String signAndSend(RawTransaction rawTransaction, String filePath, String passwrd) {
        String txHash = "";
        String signData = signData(rawTransaction, filePath, passwrd);
        if (!"".equals(signData)) {
            try {
                EthSendTransaction send = Web3jUtil.getClint().ethSendRawTransaction(signData).send();
                txHash = send.getTransactionHash();
                if (StringUtils.isEmpty(txHash)) {
                    LogUtils.e("转帐异常：{}", send.toString());
                } else {
                    LogUtils.i("转帐信息：{}", send.toString());
                }
            } catch (IOException e) {
                LogUtils.e("交易异常：{}", e);
                throw new RuntimeException("交易异常");
            }
        }
        return txHash;
    }

    public String signData(RawTransaction rawTransaction, String filePath, String passwrd) {
        try {
            // 私钥方式
            //  String privateKey = "d67ab15171cbbd24c063537d6055748ca21604213ae29ea2ddb69685a19f91c1";
            //  if (privateKey.startsWith("0x")) {
            //      privateKey = privateKey.substring(2);
            //  }
            //  ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
            // Credentials credentials = Credentials.create(ecKeyPair);

            //keystore方式
            LogUtils.e("keystore文件：" + filePath);
            Credentials credentials = WalletUtils.loadCredentials(passwrd, filePath);
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String signData = Numeric.toHexString(signMessage);
            return signData;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("加载keystore文件异常！");
            return null;
        }
    }

    /**
     * 钱包余额
     *
     * @param walletAddr   钱包地址
     * @param contractAddr erc20代币合约地址
     * @return
     */
    @Override
    public BigDecimal getBalance(String walletAddr, String contractAddr) {
        if (StringUtils.isEmpty(contractAddr)) {//ETH余额
            try {
                EthGetBalance ethGetBalance = Web3jUtil.getClint().ethGetBalance(walletAddr, DefaultBlockParameterName.LATEST).send();
                return Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER);
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e("ETH余额获取失败...");
                return BigDecimal.ZERO;
            }
        } else {//erc20 余额
            BigDecimal balanceValue = BigDecimal.ZERO;
            try {
                String methodName = "balanceOf";
                List<Type> inputParameters = new ArrayList<>();
                List<TypeReference<?>> outputParameters = new ArrayList<>();
                Address address = new Address(walletAddr);
                inputParameters.add(address);
                TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
                };
                outputParameters.add(typeReference);
                Function function = new Function(methodName, inputParameters, outputParameters);
                String data = FunctionEncoder.encode(function);
                Transaction transaction = Transaction.createEthCallTransaction(walletAddr, contractAddr, data);
                EthCall ethCall = Web3jUtil.getClint().ethCall(transaction, DefaultBlockParameterName.LATEST).send();
                List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
                if (results.size() > 0)
                    balanceValue = (BigDecimal) results.get(0).getValue();
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                LogUtils.e("erc20余额获取失败...");
                return balanceValue;
            }
            return balanceValue;
        }
    }

    /**
     * 获取账户的Nonce
     *
     * @param walletAddr
     * @return
     */
    public BigInteger getNonce(String walletAddr) {
        try {
            EthGetTransactionCount getNonce = Web3jUtil.getClint().ethGetTransactionCount(walletAddr, DefaultBlockParameterName.PENDING).send();
            if (getNonce == null) {
                throw new RuntimeException("net error");
            }
            return getNonce.getTransactionCount();
        } catch (IOException e) {
            throw new RuntimeException("net error");
        }
    }

    /**
     * 估算手续费上限
     *
     * @param transaction
     * @return
     */
    public BigInteger getTransactionGasLimit(Transaction transaction) {
        try {
            EthEstimateGas ethEstimateGas = Web3jUtil.getClint().ethEstimateGas(transaction).send();
            if (ethEstimateGas.hasError()) {
                throw new RuntimeException(ethEstimateGas.getError().getMessage());
            }
            BigInteger gasLimit = null;
            if (ethEstimateGas != null && ethEstimateGas.getAmountUsed() != null) {
                gasLimit = ethEstimateGas.getAmountUsed().add(new BigInteger("200"));
                LogUtils.i("gaslimit:{}", gasLimit);
            }
            return gasLimit;
        } catch (IOException e) {
            LogUtils.e("计算gasLimit error:" + e.getMessage());
            throw new RuntimeException("net error");
        }
    }

    /**
     * 通过hash获取交易详情
     *
     * @param txHash
     * @return
     */
    @Override
    public TransactionRecord getTxByHash(String txHash) {
        Web3j web3Clint = Web3jUtil.getClint();
        TransactionRecord record = null;
        try {
            EthTransaction ethTransaction = web3Clint.ethGetTransactionByHash(txHash).send();
            Optional<org.web3j.protocol.core.methods.response.Transaction> optional = ethTransaction.getTransaction();
            org.web3j.protocol.core.methods.response.Transaction transaction = optional.get();
            BigInteger blockNumber = transaction.getBlockNumber();
            if (blockNumber != null) {
                record = new TransactionRecord();
                record.setStatus(1);
                record.setBlockNumber(blockNumber.toString());
            }
            return record;
        } catch (Exception exception) {
            LogUtils.e("parse tx error ：" + exception.getMessage());
        }
        return null;
    }


    public List<TransactionModel> getTransactions(String address, String contractAddr) {
        List<TransactionModel> transactionList = Collections.emptyList();
        //todo 交易记录
        try {
            Web3j clint = Web3jUtil.getClint();
            EthBlock.Block block = clint.blockFlowable(false).blockingLast().getBlock();
            LogUtils.i(block.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    /**
     * 查询最新币价
     *
     * @param tokenId
     * @param unit
     * @return
     */
    public BigDecimal getLastPrice(int tokenId, String unit) {
        /**
         *  通过CoinMarketCap 平台查询币价
         *  获取币种idString uri = "https://pro-api.coinmarketcap.com/v1/fiat/map";
         *  根据币种id和单位id查询
         *  "https://web-api.coinmarketcap.com/v1/tools/price-conversion?amount=1&convert_id=2781&id=825";
         *  amount 数量
         *  convert_id 币价折算单位id
         *  id 币种id
         */
        return BigDecimal.ZERO;
    }

}
