package com.tancheng.carbonchain.activities.asset.wallet.service.eth;

import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * created by collin on 2020/3/18 14:48
 * Description: Web3j工具类
 * version: 1.0
 */
public class Web3jUtil {

    static Web3j web3j;

    public static Web3j getClint() {
        if (web3j != null) {
            return web3j;
        }
        web3j = Web3j.build(new HttpService(getNodeUrl()));
        return web3j;
    }

    private static String getNodeUrl() {
        if (WalletConstants.ENV.equals("dev")) {
            return WalletConstants.ETH_RPC_URL_TEST;
        } else {
            return WalletConstants.ETH_RPC_URL;
        }
    }

    public static String getTxURL(){
        if (WalletConstants.ENV.equals("dev")) {
            return WalletConstants.ETH_TX_URL;
        } else {
            return WalletConstants.ETH_TX_URL_TEST;
        }
    }
}
