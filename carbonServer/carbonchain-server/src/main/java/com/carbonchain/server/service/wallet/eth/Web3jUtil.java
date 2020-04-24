package com.carbonchain.server.service.wallet.eth;
import com.carbonchain.server.service.wallet.WalletConstants;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * created by collin on 2020/3/18 14:48
 * Description: Web3j工具类
 * version: 1.0
 */
public class Web3jUtil {
    static Web3j web3j;
    private static String nodeUrl = WalletConstants.ETH_RPC_URL_TEST;

    public static Web3j getClint() {
        if (web3j != null) {
            return web3j;
        }
        web3j = Web3j.build(new HttpService(nodeUrl));
        return web3j;
    }
}
