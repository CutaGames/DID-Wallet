package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by tc_collin on 2020/3/30 11:11
 * Description:
 * version: 1.0
 */
public class TokenModel {
    private int id;
    private String name;//名称
    private String symbol;//代号
    private String address;//合约地址
    private int logoUrl;//logo地址
    private Integer decimals;//小数位数
    private int walletType;//所属钱包类型
    private boolean isAdd;//是否已添加
}
