package com.tancheng.carbonchain.activities.asset.wallet.service;

import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TransferData;

import java.math.BigDecimal;

/**
 * created by tc_collin on 2020/3/20 13:37
 * Description: 链上相关服务
 * version: 1.0
 */
public interface IchainService {
    //转账
    boolean transferToken(TransferData transaction);

    //余额
    BigDecimal getBalance(String addr, String contract);

    //交易记录
    TransactionRecord getTxByHash(String txHash);

}
