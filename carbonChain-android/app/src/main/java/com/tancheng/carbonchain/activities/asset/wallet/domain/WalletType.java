package com.tancheng.carbonchain.activities.asset.wallet.domain;

import com.tancheng.carbonchain.R;

import java.util.Arrays;
import java.util.List;

/**
 * created by collin on 2020/3/18 12:03
 * Description: 代币类型 钱包类型
 * version: 1.0
 */
public enum WalletType {
    BTC(1,"BTC","m/44'/0'/0'/0/0", R.mipmap.coin_icon_btc,R.mipmap.bg_banner_btc),
    ETH(2,"ETH","m/44'/60'/0'/0/0", R.mipmap.coin_icon_eth,R.mipmap.bg_banner_eth);
//    BTC_TEST(99,"BTC_TEST","m/44'/1'/0'/0/0", R.mipmap.coin_icon_btc,R.mipmap.bg_banner_btc),
//    XRP(100,"XRP","m/44'/1'/0'/0/0", R.mipmap.coin_icon_xrp,R.mipmap.bg_banner_btc);

    private final int mType;//钱包类型
    private final String name;//钱包名称
    private final String mPath;//钱包路径 bip44
    private final int logoPath;//钱包logo
    private final int bannerBg;//钱包banner

    WalletType(int mType,String name,String mPath, int logoPath,int bannerBg ){
        this.mPath = mPath;
        this.name = name;
        this.mType = mType;
        this.logoPath = logoPath;
        this.bannerBg = bannerBg;
    }

    public static WalletType of(int type) {
        switch (type) {
            case 1:
                return BTC;
            case 2:
                return ETH;
//            case 99:
//                return BTC_TEST;
//            case 100:
//                return XRP;
        }
        throw new IllegalArgumentException("not support coin type : " + type);
    }

    public static List<WalletType> walletTypeList(){
        WalletType[] values = WalletType.values();
        List<WalletType> walletTypes = Arrays.asList(values);
        return walletTypes;
    }

    public String getPath() {
        return mPath;
    }

    public int getWalletType() {
        return mType;
    }

    public int getLogo() {
        return logoPath;
    }

    public int getBannerBg(){
        return bannerBg;
    }

    public String getName(){
        return name;
    }

}
