package com.tancheng.carbonchain.activities.asset.wallet.service;

import com.blankj.utilcode.util.LogUtils;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * created by tc_collin on 2020/4/21 17:41
 * Description:
 * version: 1.0
 */
public class LoadBalanceRunable implements Runnable {

    private String address;
    private WalletType walletType;

    public LoadBalanceRunable(String address, WalletType walletType) {
        this.address = address;
        this.walletType = walletType;
    }

    @Override
    public void run() {
        MessageWrap<String> message = new MessageWrap<>(EventBusMsgType.UPDATE_WALLET, null);
        IchainService chainService = WalletServiceeFactory.getChainService(walletType);
        List<TokenType> tokenList = TokenType.getTokenList();
        for (TokenType tokenType : tokenList) {
            if (tokenType.getWalletType() == walletType.getWalletType()) {
                chainService.getBalance(address, tokenType.getAddress());
                LogUtils.e("导入后，更新余额...");
            }
        }
        EventBus.getDefault().post(message);
    }
}
