package com.tancheng.carbonchain.activities.asset.wallet.service.btc;

import com.spark.blockchain.rpcclient.BitcoinRPCClient;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import java.math.BigDecimal;
import java.net.MalformedURLException;
//import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

/**
 * created by tc_collin on 2020/4/16 15:54
 * Description:
 * version: 1.0
 */
public class BTCClintUtil {

    private static BitcoinRPCClient client;
    private static String nodeUrl = WalletConstants.BTC_RPC_URL_TEST;

    /**
     * 获取节点
     * @return
     * @throws MalformedURLException
     */
    public static BitcoinRPCClient getClint() throws MalformedURLException {
        if (client != null) {
            return client;
        }
        client =new BitcoinRPCClient(nodeUrl);
        return client;
    }

    public static NetworkParameters getNetworkParams() {
        if (WalletConstants.ENV.equals("dev")) {
            return TestNet3Params.get();  //测试网络
        }else{
            return MainNetParams.get();  //主网络
        }
    }

    public static String getWalletPath() {
        String path = WalletType.BTC.getPath();
        if (WalletConstants.ENV.equals("dev")) {
            path = "m/44'/1'/0'/0/0";  //测试网地址
        }
        return path;
    }

    /**
     * 聪转btc
     *
     * @param sat
     * @return
     */
    public static String sat2btc(long sat) {
        BigDecimal satValue = new BigDecimal(sat);
        BigDecimal divide = satValue.divide(BigDecimal.TEN.pow(Coin.SMALLEST_UNIT_EXPONENT));
        return divide.toPlainString();
    }

    /**
     * btc转聪
     *
     * @param btcValue
     * @return
     */
    public static long btc2sat(String btcValue) {
        BigDecimal bigDecimal = new BigDecimal(btcValue);
        BigDecimal multiply = bigDecimal.multiply(BigDecimal.TEN.pow(Coin.SMALLEST_UNIT_EXPONENT));
        return multiply.longValue();
    }

}
