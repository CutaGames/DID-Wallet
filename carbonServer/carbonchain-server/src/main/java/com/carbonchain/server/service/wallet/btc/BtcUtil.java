package com.carbonchain.server.service.wallet.btc;

import com.carbonchain.server.service.wallet.WalletConstants;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.net.MalformedURLException;

/**
 * created by tc_collin on 2020/3/21 16:01
 * Description:
 * version: 1.0
 */
public class BtcUtil {
    private static BitcoinJSONRPCClient client;
    private static String nodeUrl = WalletConstants.BTC_RPC_URL_TEST;

    /**
     * 获取节点
     * @return
     * @throws MalformedURLException
     */
    public static BitcoinJSONRPCClient getClint() throws MalformedURLException {
        if (client != null) {
            return client;
        }
        client =new BitcoinJSONRPCClient(nodeUrl);
        return client;
    }

}
