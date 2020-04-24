package com.tancheng.carbonchain;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.blankj.utilcode.util.LogUtils;
import com.spark.blockchain.rpcclient.Bitcoin;
import com.spark.blockchain.rpcclient.BitcoinRPCClient;
import com.tancheng.carbonchain.activities.asset.wallet.service.btc.BTCClintUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.tancheng.carbonchain", appContext.getPackageName());
    }

    @Test
    public void createTable() throws Exception {
        BitcoinRPCClient clint = BTCClintUtil.getClint();
        Bitcoin.RawTransaction transaction = clint.getTransaction("8dd19100df6af9a339b991f31cadf506fe5f052aee4a43644bc8a17a7f4d8208");
    }

    @Test
    public void getBalance() throws Exception {
        BitcoinRPCClient clint = BTCClintUtil.getClint();
        double balance= clint.getBalance("mmEhSocSE8d6c3Hfv2zCtHVKXeoBcmpAmP");
        LogUtils.e(balance);
    }



}
