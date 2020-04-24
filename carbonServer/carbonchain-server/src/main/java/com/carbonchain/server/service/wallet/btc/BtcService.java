package com.carbonchain.server.service.wallet.btc;

import com.carbonchain.server.mapper.WalletTransactionMapper;
import com.carbonchain.server.model.WalletTransaction;
import com.carbonchain.server.service.wallet.IchainService;
import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.pojo.TransactionModel;
import com.carbonchain.server.service.wallet.pojo.TransferData;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by tc_collin on 2020/3/20 14:31
 * Description: 比特币相关服务
 * version: 1.0
 */
@Service
public class BtcService implements IchainService {

    @Autowired
    private WalletTransactionMapper transactionMapper;

    @Override
    public boolean transferToken(TransferData transferData) {
        String from = transferData.getFrom();
        String to = transferData.getTo();
        BigDecimal value = transferData.getValue();

        String rawTransaction = createRawTransaction(from, to, value.longValue(), 100);
        String txId = signRawTransaction(rawTransaction);
        //入库
        WalletTransaction transaction = new WalletTransaction();
        transaction.setStatus(0);
        transaction.setValue(transferData.getValue().doubleValue());
        transaction.setTxHash(txId);
        transaction.setToAddr(transferData.getTo());
        transaction.setFromAddr(transferData.getFrom());
        transaction.setWalletType(WalletType.eth.getAccountType());
        transactionMapper.insert(transaction);
        return false;
    }

    @Override
    public BigDecimal getBalance(String address, String contract) {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            BitcoinJSONRPCClient clint = BtcUtil.getClint();
            BitcoindRpcClient.AddressBalance addressBalance = clint.getAddressBalance("address");
            long balance1 = addressBalance.getBalance();
            balance = BigDecimal.valueOf(balance1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * 获取地址相关的交易记录
     *
     * @param address
     */
    public List<TransactionModel> getTransactions(String address) {
        List<TransactionModel> transactionList = Collections.emptyList();
        try {
            BitcoinJSONRPCClient clint = BtcUtil.getClint();
            List<BitcoindRpcClient.Transaction> transactions = clint.listTransactions();
            for (BitcoindRpcClient.Transaction tx : transactions) {
                String address1 = tx.address();
                if (address1.equals(address)) {
                    //todo 交易记录
                    return transactionList;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    /**
     * 构建裸交易
     *
     * @param unSpentBTCList
     * @param from
     * @param to
     * @param privateKey
     * @param value
     * @param fee
     */
    public void buildRawTransaction(List<UTXO> unSpentBTCList, String from, String to, String privateKey, long value, long fee) {
        boolean mainNet = false;
        NetworkParameters networkParameters = null;
        if (!mainNet)
            networkParameters = MainNetParams.get();
        else
            networkParameters = TestNet3Params.get();
        Transaction transaction = new Transaction(networkParameters);
        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(networkParameters, privateKey);
        ECKey ecKey = dumpedPrivateKey.getKey();

        long totalMoney = 0;
        List<UTXO> utxos = new ArrayList<>();
        //遍历未花费列表，组装合适的item
        for (UTXO us : unSpentBTCList) {
            if (totalMoney >= (value + fee))
                break;
            UTXO utxo = new UTXO(us.getHash(), us.getIndex(), us.getValue(), us.getHeight(), us.isCoinbase(), us.getScript());
            utxos.add(utxo);
            totalMoney += us.getValue().value;
        }

        transaction.addOutput(Coin.valueOf(value), Address.fromString(networkParameters, to));
        // transaction.

        //消费列表总金额 - 已经转账的金额 - 手续费 就等于需要返回给自己的金额了
        long balance = totalMoney - value - fee;
        //输出-转给自己
        if (balance > 0) {
            transaction.addOutput(Coin.valueOf(balance), Address.fromString(networkParameters, from));
        }
        //输入未消费列表项
        for (UTXO utxo : utxos) {
            TransactionOutPoint outPoint = new TransactionOutPoint(networkParameters, utxo.getIndex(), utxo.getHash());
            transaction.addSignedInput(outPoint, utxo.getScript(), ecKey, Transaction.SigHash.ALL, true);
        }
    }

    /**
     * 创建元交易
     *
     * @param from
     * @param to
     * @param value
     * @param fee
     */
    private String createRawTransaction(String from, String to, long value, long fee) {
        BitcoinJSONRPCClient client = new BitcoinJSONRPCClient(true);
        List<BitcoindRpcClient.TxInput> inputs = Collections.emptyList();
        List<BitcoindRpcClient.TxOutput> outputs = Collections.emptyList();

        List<BitcoindRpcClient.AddressUtxo> addressUtxo = client.getAddressUtxo("");
        long totalMoney = 0;
        //遍历未花费列表，组装合适的item
        for (BitcoindRpcClient.AddressUtxo us : addressUtxo) {
            if (totalMoney >= (value + fee))
                break;
            inputs.add(new BitcoindRpcClient.BasicTxInput(us.getTxid(), us.getOutputIndex()));
            //todo 验证getSatoshis()
            totalMoney += us.getSatoshis();
        }
        outputs.add(new BitcoindRpcClient.BasicTxOutput(to, BigDecimal.valueOf(value)));
        //消费列表总金额 - 已经转账的金额 - 手续费 就等于需要返回给自己的金额了
        long balance = totalMoney - value - fee;
        //输出-转给自己
        if (balance > 0) {
            outputs.add(new BitcoindRpcClient.BasicTxOutput(from, BigDecimal.valueOf(balance)));
        }
        String rawTransaction = client.createRawTransaction(inputs, outputs);
        return rawTransaction;
    }

    /**
     * 签名交易
     *
     * @param rawTransaction
     * @return
     */
    private String signRawTransaction(String rawTransaction) {
        try {
            BitcoinJSONRPCClient clint = BtcUtil.getClint();
            clint.estimateFee(6);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return rawTransaction;
    }

    /**
     * 广播交易
     *
     * @param hex
     */
    private String sendRawTransaction(String hex) {
        BitcoinJSONRPCClient client = new BitcoinJSONRPCClient(true);
        return client.sendRawTransaction(hex);
    }

}
