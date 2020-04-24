package com.carbonchain.server.service.wallet;


import com.carbonchain.server.service.wallet.db.entity.Account;

/**
 * created by collin on 2020/3/18 12:03
 * Description: 代币类型
 * version: 1.0
 */
public enum WalletType {

    eth("m/44'/60'/0'/0/0", Account.ETHER),
    btc("m/44'/0'/0'/0/0", Account.BTC),
    btcTest("m/44'/1'/0'/0/0", Account.BTC_TEST);

    private final String mPath;
    private final int mAccountType;

    WalletType(String mPath, int mAccountType){
        this.mPath = mPath;
        this.mAccountType = mAccountType;
    }

    public static WalletType of(int type) {
        switch (type) {
            case Account.ETHER:
                return eth;
            case Account.BTC:
                return btc;
            case Account.BTC_TEST:
                return btcTest;
        }
        throw new IllegalArgumentException("not support coin type : " + type);
    }

    public String getPath() {
        return mPath;
    }

    public int getAccountType() {
        return mAccountType;
    }


}
