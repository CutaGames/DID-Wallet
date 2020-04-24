package com.tancheng.carbonchain.activities.asset.wallet.service;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.WalletBalance;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IchainService;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.Web3jUtil;
import com.tancheng.carbonchain.db.gen.TransationRecordDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletBalnceDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;
import com.tancheng.carbonchain.utils.network.NormalCallBack;
import com.tancheng.carbonchain.utils.network.OkHttpManager;

import org.greenrobot.eventbus.EventBus;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java8.util.Optional;
import okhttp3.Call;
import okhttp3.Response;

/**
 * created by tc_collin on 2020/4/11 13:51
 * Description: 数据同步service
 * version: 1.0
 */
public class SyncService implements Runnable {

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doSyncTokenPrice();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                doSyncStatusJob();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                doCheckBalanceJob();
            }
        }).start();
    }

    /**
     * 定时任务
     * 定时更新转账记录状态
     */
    private void doSyncStatusJob() {
        LogUtils.i("定时任务  doSyncStatusJob...");
        while (true) {
            try {
                TransationRecordDaoUtils txDao = TransationRecordDaoUtils.getIntence();
                List<TransactionRecord> datas = txDao.getPaddingTx(WalletType.ETH.getWalletType());
                int count = datas.size();
                if (count < 1) {
                    Thread.sleep(1000 * 60 * 3);
                }
                ExecutorService executorService = Executors.newCachedThreadPool();
                CountDownLatch countDownLatch = new CountDownLatch(count);
                Web3j web3Clint = Web3jUtil.getClint();
                for (int i = 0; i < count; i++) {
                    int index = i;
                    executorService.execute(() -> {
                        try {
                            TransactionRecord txLocalRecord = datas.get(index);
                            WalletType walletType = WalletType.of(txLocalRecord.getWalletType());
                            TransactionRecord txChainRecord = WalletServiceeFactory.getChainService(walletType).getTxByHash(txLocalRecord.getTxHash());
                            if (txChainRecord != null) {
                                txLocalRecord.setStatus(1);
                                txLocalRecord.setBlockNumber(txChainRecord.getBlockNumber());
                            }
                            txDao.updateTransation(txLocalRecord);
                        } catch (Exception e) {
                            LogUtils.e("parse tx error ：" + e.getMessage());
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await(30, TimeUnit.MINUTES);
                executorService.shutdown();
                //发送更新Ui消息
                MessageWrap<String> message = new MessageWrap<>(EventBusMsgType.UPDATE_WALLET, null);
                EventBus.getDefault().post(message);

                Thread.sleep(1000 * 60 * 1);
            } catch (Exception exception) {
                exception.printStackTrace();
                LogUtils.e(exception.getMessage());
            }
        }
    }

    /**
     * 定时更新币价到sp
     * key = symbol+"_price"
     */
    private void doSyncTokenPrice() {
        while (true) {
            List<TokenType> coinList = TokenType.getTokenList();
            for (TokenType tokenType : coinList) {
                String symbol = tokenType.getSymbol();
                int tokenId = tokenType.getId();
                String priceUrl = "https://web-api.coinmarketcap.com/v1/tools/price-conversion?amount=1&convert_id=" + WalletConstants.CNY + "&id=" + tokenId;

                OkHttpManager.getInstance().getRequest(priceUrl, new NormalCallBack<String>() {
                    @Override
                    protected void onSuccess(Call call, Response response, String responseData) {
                        if (!StringUtils.isEmpty(responseData)) {
                            Gson gson = new Gson();
                            JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
                            JsonObject data = jsonObject.getAsJsonObject("data");
                            if (data != null) {
                                JsonObject quoteElement = data.getAsJsonObject("quote");
                                if (quoteElement != null) {
                                    JsonObject jsonElement = quoteElement.getAsJsonObject(WalletConstants.CNY + "");
                                    if (jsonElement != null) {
                                        JsonElement price = jsonElement.get("price");
                                        if (price != null) {
                                            BigDecimal asBigDecimal = price.getAsBigDecimal();
                                            SPUtils.getInstance().put(symbol + "_price", asBigDecimal.toPlainString());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    protected void onEror(Call call, int statusCode, Exception e) {
                        if (e != null && !TextUtils.isEmpty(e.getMessage())) {
                            LogUtils.e("币价请求失败：statusCode" + statusCode + "   exception:" + e.getMessage());
                        } else {
                            LogUtils.e("币价请求失败：" + statusCode);
                        }
                    }

                    @Override
                    protected void inProgress(int progress, long total, int id) {

                    }
                }, null);
            }

            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                LogUtils.e(e != null ? e.getMessage() : "同步余额异常");
            }
        }
    }

    /**
     * 定时检查钱包余额
     * key = symbol+"_balance"
     */
    private void doCheckBalanceJob() {
        LogUtils.i("【定时任务】-检查钱包余额...");
        while (true) {
            WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
            List<ETHWallet> wallets = walletDaoUtils.getWallets();
            for (ETHWallet wallet : wallets) {
                String walletAddr = wallet.getAddress();
                int walletType = wallet.getWalletType();
                String coinIds = wallet.getCoinIds();
                String[] split = coinIds.split(WalletConstants.COIIN_SPLIT_CHAR);
                for (String tokenId : split) {
                    TokenType tokenType = TokenType.of(Integer.parseInt(tokenId));
                    String symbol = tokenType.getSymbol();
                    String contractAddr = tokenType.getAddress();
                    WalletType of = WalletType.of(walletType);
                    IchainService chainService = WalletServiceeFactory.getChainService(of);
                    BigDecimal balance = chainService.getBalance(walletAddr, contractAddr);
                    if (balance != null) {
                        WalletBalance walletBalance = WalletBalnceDaoUtils.getIntence().getWalletBalance(walletAddr, symbol);
                        if (walletBalance == null) {
                            WalletBalance newWalletBalance = new WalletBalance();
                            newWalletBalance.setSymbol(symbol);
                            newWalletBalance.setWalletAddress(walletAddr);
                            newWalletBalance.setBalance(0.00);
                            newWalletBalance.setPrice(0.00);
                            WalletBalnceDaoUtils.getIntence().insert(newWalletBalance);
                                    LogUtils.i("新增余额：", newWalletBalance.getBalance() + " : " + newWalletBalance.getWalletAddress());
                        } else {
                            walletBalance.setBalance(balance.doubleValue());
                            WalletBalnceDaoUtils.getIntence().upadteBalance(walletBalance);
                                    LogUtils.i("更新余额：", walletBalance.getBalance() + " : " + walletBalance.getWalletAddress());
                        }
                    }
//                    }
                }

                MessageWrap<String> message = new MessageWrap<>(EventBusMsgType.UPDATE_WALLET, null);
                EventBus.getDefault().post(message);

                try {
                    Thread.sleep(1000 * 60 * 2);
                } catch (InterruptedException e) {
                    LogUtils.e(e != null ? e.getMessage() : "同步余额异常");
                }
            }
        }
    }

}
