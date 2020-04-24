package com.carbonchain.server.service.wallet;

import com.carbonchain.server.service.wallet.pojo.TransferData;

import java.math.BigDecimal;

/**
 * created by tc_collin on 2020/3/20 13:37
 * Description: chain转账相关服务
 * version: 1.0
 */
public interface IchainService {
    //转账
    boolean transferToken(TransferData transaction);

    //余额
    BigDecimal getBalance(String addr, String contract);

    //交易记录
    //void getTxRecords();



}
