package com.carbonchain.server.service.wallet;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.carbonchain.server.mapper.WalletAccountMapper;
import com.carbonchain.server.mapper.WalletCoinMapper;
import com.carbonchain.server.mapper.WalletTransactionMapper;
import com.carbonchain.server.model.WalletAccount;
import com.carbonchain.server.model.WalletCoin;
import com.carbonchain.server.model.WalletTransaction;
import com.carbonchain.server.service.wallet.eth.Web3jUtil;
import com.carbonchain.server.service.wallet.pojo.Wallet;
import com.carbonchain.server.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * created by collin on 2020/3/24 17:57
 * Description: 同步钱包地址相关的交易
 * version: 1.0
 */
@Component
@Slf4j
public class MyTask {

    @Autowired
    private WalletTransactionMapper transactionMapper;
    @Autowired
    private WalletCoinMapper coinMapper;
    @Autowired
    private WalletAccountMapper accountMapper;

    @Scheduled(initialDelay = 100 * 3, fixedDelayString = "60000")
    public void syncTransaction() {
        EntityWrapper<WalletTransaction> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("status", 1);
        List<WalletTransaction> walletTransactions = transactionMapper.selectList(entityWrapper);
        for (WalletTransaction tx : walletTransactions) {
            WalletType walletType = WalletType.of(tx.getWalletType());
            switch (walletType) {
                case eth:
                    try {
                        String txHash = tx.getTxHash();
                        Web3j web3Client = Web3jUtil.getClint();
                        EthTransaction ethTransaction = web3Client.ethGetTransactionByHash(txHash).send();
                        if (ethTransaction != null) {
                            Transaction transaction = ethTransaction.getResult();
                            //判断是否是eth地址
                            String input = ethTransaction.getResult().getInput();
                            if (StringUtils.isNotEmpty(input) && input.length() > 10) {
                                //token转帐信息
                                String hexAmount = input.substring(78, input.length());
                                String hex = "0x" + hexAmount;
                                BigDecimal decimal = new BigDecimal(new BigInteger(hex.substring(2), 16));
                                BigDecimal precisionDecimal = new BigDecimal(10).pow(8);
                                BigDecimal tokenValue = decimal.divide(precisionDecimal).setScale(5, RoundingMode.DOWN);
                                //更新
                                tx.setBlockNumber(transaction.getBlockNumber().toString());
                                tx.setGas(transaction.getGas().longValue());
                                tx.setValue(tokenValue.doubleValue());
                                tx.setStatus(2);
                                transactionMapper.updateAllColumnById(tx);
                            } else {
                                tx.setBlockNumber(transaction.getBlockNumber().toString());
                                tx.setGas(transaction.getGas().longValue());
                                tx.setValue(Convert.fromWei(new BigDecimal(transaction.getValue()), Convert.Unit.ETHER).doubleValue());
                                tx.setStatus(2);
                                transactionMapper.updateAllColumnById(tx);
                            }
                        }
                    } catch (Exception exception) {
                        LogUtil.businessLog.error(exception.getMessage());
                    }
                    break;

                case btc:
                    BitcoinJSONRPCClient client = new BitcoinJSONRPCClient(true);
                    String txHash = tx.getTxHash();
                    BitcoindRpcClient.Transaction transaction = client.getTransaction(txHash);
                    if (transaction != null) {
                        tx.setTimeStamp(transaction.time().toString());
                        tx.setStatus(2);
                        transactionMapper.updateAllColumnById(tx);
                    }
                    break;
            }
        }
    }
}
