package com.carbonchain.server.service.wallet.pojo;

/**
 * created by tc_collin on 2020/3/23 10:28
 * Description: 代币信息
 * version: 1.0
 */
public class Erc20Coin {
    private int id;
    private String name;//代币中文名
    public String symbol;//代币符号
    public String address;//代笔合约地址
    private String logoUrl;//代笔logo地址
    public String totalSupply;//代币总供应量
    public long decimals;//小数位数
    public String coinType;//所属链

}
