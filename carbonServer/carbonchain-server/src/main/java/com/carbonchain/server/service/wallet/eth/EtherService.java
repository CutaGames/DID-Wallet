package com.carbonchain.server.service.wallet.eth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.carbonchain.server.mapper.WalletTransactionMapper;
import com.carbonchain.server.model.WalletTransaction;
import com.carbonchain.server.service.wallet.IchainService;
import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.pojo.TransactionContract;
import com.carbonchain.server.service.wallet.pojo.TransactionModel;
import com.carbonchain.server.service.wallet.pojo.TransferData;
import com.carbonchain.server.service.wallet.pojo.Wallet;
import com.carbonchain.server.service.wallet.db.WalletDbHelper;
import com.carbonchain.server.service.wallet.db.entity.Account;
import com.carbonchain.server.service.wallet.utils.WalletEncryptUtils;
import com.carbonchain.server.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by tc_collin on 2020/3/18 14:48
 * Description: eth chainService
 * version: 1.0
 */
@Slf4j
@Service
public class EtherService implements IchainService {
    private final BigInteger defaultGasLimit = new BigInteger("21000");

    @Autowired
   private WalletTransactionMapper transactionMapper;


    /**
     * 创建并保存到DB
     *
     * @param name
     * @param passwd
     * @param note
     */
    public Wallet createRandomAccount(String name, String passwd, String note) {
        try {
            JSONObject extra = new JSONObject();
            extra.put("note", note);
            EthWalletHelper ethWalletHelper = new EthWalletHelper();
            Wallet wallet = ethWalletHelper.createWalletAccount();
            Account account = convert2Account(wallet, name, passwd, extra.toString());
            //入库
            WalletDbHelper.inst().insertAccount(account);
            return wallet;
        } catch (JSONException e) {
            e.printStackTrace();
            log.error("wallet create error:" + e.getMessage());
        }
        return null;
    }

    private Account convert2Account(Wallet wallet, String name, String passwd, String extra) {
        Account account = new Account();
        account.setId(WalletEncryptUtils.getWalletId(wallet.getAddress()));
        account.setName(name);
        account.setAddress(wallet.getAddress());
        account.setType(wallet.getType().getAccountType());
        account.setExtra(extra);
        try {
            account.setMnemonic(WalletEncryptUtils.encrypt(wallet.getMnemonic(), account.getId(), passwd));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("wallet convert2Account setMnemonic error:" + e.getMessage());
        }

        try {
            account.setPrivateKey(WalletEncryptUtils.encrypt(wallet.getPrivateKey(), account.getId(), passwd));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("wallet convert2Account encrypt private error:" + e.getMessage());
        }
        return account;
    }

    /**
     * 转账
     *
     * @param transferData
     * @return
     */
    @Override
    public boolean transferToken(TransferData transferData) {
        try {
            RawTransaction rawTransaction = buildRawTransation(transferData);
            String txHash = signAndSend(rawTransaction);
            //入库
            WalletTransaction transaction = new WalletTransaction();
            transaction.setStatus(0);
            transaction.setValue(transferData.getValue().doubleValue());
            transaction.setTxHash(txHash);
            transaction.setToAddr(transferData.getTo());
            transaction.setFromAddr(transferData.getFrom());
            transaction.setWalletType(WalletType.eth.getAccountType());
            transactionMapper.insert(transaction);
            log.info("交易hash" + txHash);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("转帐代币出错：{}", e);
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
            TransactionContract contract = transferData.getContract();
            String to = transferData.getTo();
            BigDecimal amount = transferData.getValue();

            Web3j web3j = Web3jUtil.getClint();
            EthGasPrice send = web3j.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            // BigInteger gasPrice = BigInteger.valueOf(50);
            // 获得nonce
            BigInteger nonce = getNonce(from);
            RawTransaction rawTransaction = null;
            if (contract == null || StringUtils.isEmpty(contract.address)) { //转账ETH
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
                inputArgs.add(new Uint256(amount.multiply(BigDecimal.TEN.pow(contract.getDecimals())).toBigInteger()));
                // 合约返回值容器
                List<TypeReference<?>> outputArgs = new ArrayList<>();
                String funcABI = FunctionEncoder.encode(new Function(method, inputArgs, outputArgs));
                Transaction transaction = Transaction.createFunctionCallTransaction(from, nonce, gasPrice, null, contract.getAddress(), funcABI);
                BigInteger gasLimit = getTransactionGasLimit(transaction);
                log.error("gasLimit:", gasLimit);
                // 获得余额
                BigDecimal ethBalance = getBalance(from, "");
                BigDecimal tokenBalance = getBalance(from, contract.getAddress());
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
            log.error("转帐代币出错：{}", e);
            return null;
        }
    }

    /**
     * 对交易签名，并发送交易
     *
     * @param rawTransaction
     * @return
     */
    public String signAndSend(RawTransaction rawTransaction) {
        String txHash = "";
        String signData = signData(rawTransaction);
        if (!"".equals(signData)) {
            try {
                EthSendTransaction send = Web3jUtil.getClint().ethSendRawTransaction(signData).send();
                txHash = send.getTransactionHash();
                if (StringUtils.isEmpty(txHash)) {
                    log.error("转帐异常：{}", JSON.toJSONString(send));
                } else {
                    log.info("转帐信息：{}", JSON.toJSONString(send));
                }
            } catch (IOException e) {
                log.error("交易异常：{}", e);
                throw new RuntimeException("交易异常");
            }
        }
        return txHash;
    }

    public String signData(RawTransaction rawTransaction) {
        // TODO: 2020/3/20 私钥
        String privateKey = "d67ab15171cbbd24c063537d6055748ca21604213ae29ea2ddb69685a19f91c1";
        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        Credentials credentials = Credentials.create(ecKeyPair);

        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String signData = Numeric.toHexString(signMessage);
        return signData;
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
                log.error("ETH余额获取失败...");
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
                log.error("erc20余额获取失败...");
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
                log.info("gaslimit:{}", gasLimit);
            }
            return gasLimit;
        } catch (IOException e) {
            log.error("计算gasLimit error:" + e.getMessage());
            throw new RuntimeException("net error");
        }
    }

    public List<TransactionModel> getTransactions(String address, String contractAddr) {
        List<TransactionModel> transactionList = Collections.emptyList();
        //todo 交易记录
        try {
            Web3j clint = Web3jUtil.getClint();
            EthBlock.Block block = clint.blockFlowable(false).blockingLast().getBlock();
            LogUtil.businessLog.info(block.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return transactionList;
    }

}
