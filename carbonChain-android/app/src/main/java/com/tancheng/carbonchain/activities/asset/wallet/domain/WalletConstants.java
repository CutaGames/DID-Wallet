package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by tc_collin on 2020/3/18 15:11
 * Description: 常量
 * version: 1.0
 */
public class WalletConstants {
    public static final String ENV = "dev";//dev prd

    //ETH节点地址
    public static final String ETH_RPC_URL = "http://gateway.bitrenren.com:8545";
    public static final String ETH_RPC_URL_TEST = "https://ropsten.infura.io/v3/4d3c41358dc34e41a5a309a49eeda6da";

    //ETH交易详情
    public static final String ETH_TX_URL = "http://gateway.bitrenren.com:8545";
    public static final String ETH_TX_URL_TEST = "https://ropsten.etherscan.io/tx/";

    //btc节点地址
    public static final String BTC_RPC_URL = "https://cn.etherscan.com/tx/";
    public static final String BTC_RPC_URL_TEST = "http://admin:123456789@10.0.2.2:8332/";

    //现金单位 通过coinMarketCap查询
    public static final String USD = "2781";
    public static final String CNY = "2787";
    public static final String JPY = "2797";
    public static final String HKD = "2792";

    //钱包账户表
    public static final String WALLET_DB_TABLE = "wallet";

    /*******     Shareprefile相关key         **********/
    //钱包币种列表币种分隔符
    public static final String COIIN_SPLIT_CHAR = ",";
    public static final String HIDE_PASSWORD_TIPS = "hidePasswordTips";//显示隐藏密码提示语
    public static final String HIDE_BALANCE = "hideBalance";//显示隐藏余额


}
