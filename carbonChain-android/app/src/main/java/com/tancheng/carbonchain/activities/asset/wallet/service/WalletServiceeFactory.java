package com.tancheng.carbonchain.activities.asset.wallet.service;

import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.btc.BTCChainServiceImpl;
import com.tancheng.carbonchain.activities.asset.wallet.service.btc.BTCChainServiceThreePartImpl;
import com.tancheng.carbonchain.activities.asset.wallet.service.btc.BTCWalletServiceImpl;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHWalletServiceImpl;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHChainServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by tc_collin on 2020/3/18 15:15
 * Description: 钱包服务工厂类
 * version: 1.0
 */
public class WalletServiceeFactory {
    private static Map<WalletType, IwalletService> sMap;
    private static Map<WalletType, IchainService> chainServiceMap;

    public static IwalletService getWalletCreator(WalletType type) {
        if (sMap == null) {
            synchronized (WalletServiceeFactory.class) {
                if (sMap == null) {
                    sMap = new ConcurrentHashMap<>();
                    initMap();
                }
            }
        }
        return sMap.get(type);
    }

    public static IchainService getChainService(WalletType type) {
        if (chainServiceMap == null) {
            synchronized (WalletServiceeFactory.class) {
                if (chainServiceMap == null) {
                    chainServiceMap = new ConcurrentHashMap<>();
                    initServiceMap();
                }
            }
        }
        return chainServiceMap.get(type);
    }

    private static void initMap() {
        sMap.put(WalletType.ETH, new ETHWalletServiceImpl());
        sMap.put(WalletType.BTC, new BTCWalletServiceImpl());
    }

    private static void initServiceMap(){
        chainServiceMap.put(WalletType.ETH, new ETHChainServiceImpl());
        chainServiceMap.put(WalletType.BTC, new BTCChainServiceImpl());
    }

}
