package com.carbonchain.server.service.wallet;

import com.carbonchain.server.service.wallet.eth.EthWalletCreator;
import com.carbonchain.server.service.wallet.eth.EtherService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by tc_collin on 2020/3/18 15:15
 * Description: 钱包创建工厂
 * version: 1.0
 */
@Service
public class WalletCreatorFactory {
    private static Map<WalletType, IwalletCreator> sMap;
    private static Map<WalletType, IchainService> chainServiceMap;

    public static IwalletCreator getWalletCreator(WalletType type) {
        if (sMap == null) {
            synchronized (WalletCreatorFactory.class) {
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
            synchronized (WalletCreatorFactory.class) {
                if (chainServiceMap == null) {
                    chainServiceMap = new ConcurrentHashMap<>();
                    initServiceMap();
                }
            }
        }
        return chainServiceMap.get(type);
    }

    private static void initMap() {
        sMap.put(WalletType.eth, new EthWalletCreator());
    }

    private static void initServiceMap(){
        chainServiceMap.put(WalletType.eth, new EtherService());
    }

}
