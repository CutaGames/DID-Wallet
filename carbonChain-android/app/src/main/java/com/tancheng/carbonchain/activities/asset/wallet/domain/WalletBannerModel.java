package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by tc_collin on 2020/4/14 18:28
 * Description: banner数据
 * version: 1.0
 */
public class WalletBannerModel {
    private int walletTypeId;//钱包类型
    private long walletId = -1;//钱包id
    private int bgResource;
    private String walletName;
    private String walletAddr;
    private String balance;

    public int getWalletTypeId() {
        return walletTypeId;
    }

    public void setWalletTypeId(int walletTypeId) {
        this.walletTypeId = walletTypeId;
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }

    public int getBgResource() {
        return bgResource;
    }

    public void setBgResource(int bgResource) {
        this.bgResource = bgResource;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletBannerModel{" +
                "bgResource=" + bgResource +
                ", walletName='" + walletName + '\'' +
                ", walletAddr='" + walletAddr + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
