package com.tancheng.carbonchain.activities.asset.wallet.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
/**
 * created by tc_collin on 2020/4/13 15:42
 * Description:
 * version: 1.0
 */
@Entity
public class WalletBalance {
    @Id(autoincrement = true)
    private Long id;
    private String walletAddress;
    private String symbol;
    private double balance;
    private double price;
    @Generated(hash = 1556915824)
    public WalletBalance(Long id, String walletAddress, String symbol,
            double balance, double price) {
        this.id = id;
        this.walletAddress = walletAddress;
        this.symbol = symbol;
        this.balance = balance;
        this.price = price;
    }
    @Generated(hash = 1403405958)
    public WalletBalance() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWalletAddress() {
        return this.walletAddress;
    }
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    public String getSymbol() {
        return this.symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
