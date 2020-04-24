package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by tc_collin on 2020/4/15 11:39
 * Description: eventBus事件类型
 * version: 1.0
 */
public interface EventBusMsgType {
    //更新钱包
    String UPDATE_WALLET = "updateWalletInfo";
    //显示隐藏资产
    String HIDE_BALANCE = "hideBalance";
    //加载资产
    String LOAD_BALANCE = "loadBalance";
}
