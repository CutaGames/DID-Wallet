package com.carbonchain.server.service.wallet;

/**
 * created by tc_collin on 2020/3/18 15:11
 * Description: 常量
 * version: 1.0
 */
public class WalletConstants {
    //ETH节点地址
    public static final String ETH_RPC_URL = "http://gateway.bitrenren.com:8545";
    public static final String ETH_RPC_URL_TEST = "https://ropsten.infura.io/v3/4d3c41358dc34e41a5a309a49eeda6da";

    //btc节点地址
    public static final String BTC_RPC_URL = "http://root:123456@127.0.0.1:8332/";
    public static final String BTC_RPC_URL_TEST = "http://admin:123456@127.0.0.1:8332/";

    //钱包账户表
    public static final String WALLET_DB_TABLE = "wallet";

    //钱包币种列表币种分隔符
    public static final String COIIN_SPLIT_CHAR = ",";

}
