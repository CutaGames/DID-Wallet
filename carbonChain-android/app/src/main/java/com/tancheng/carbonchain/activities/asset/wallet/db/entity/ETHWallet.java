package com.tancheng.carbonchain.activities.asset.wallet.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * created by collin on 2020/3/26 16:04
 * Description: 钱包实体类
 * version: 1.0
 */
@Entity
public class ETHWallet {

    @Id(autoincrement = true)
    private Long id;
    public String address;
    @Unique
    private String name;
    private String password;
    private String keystorePath;
    private String mnemonic;
    private String privateKey;
    private String coinIds;//已添加的代币列表
    private String pwdTips;//密码提示符
    private int walletType;
    private boolean isCurrent;
    private boolean isBackup;

    @Generated(hash = 1061728530)
    public ETHWallet(Long id, String address, String name, String password,
            String keystorePath, String mnemonic, String privateKey, String coinIds,
            String pwdTips, int walletType, boolean isCurrent, boolean isBackup) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.password = password;
        this.keystorePath = keystorePath;
        this.mnemonic = mnemonic;
        this.privateKey = privateKey;
        this.coinIds = coinIds;
        this.pwdTips = pwdTips;
        this.walletType = walletType;
        this.isCurrent = isCurrent;
        this.isBackup = isBackup;
    }

    @Generated(hash = 1963897189)
    public ETHWallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean isBackup() {
        return isBackup;
    }

    public void setBackup(boolean backup) {
        isBackup = backup;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public boolean getIsBackup() {
        return this.isBackup;
    }

    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }

    public int getWalletType() {
        return this.walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public String getCoinIds() {
        return this.coinIds;
    }

    public void setCoinIds(String coinIds) {
        this.coinIds = coinIds;
    }


    public String getPwdTips() {
        return pwdTips;
    }

    public void setPwdTips(String pwdTips) {
        this.pwdTips = pwdTips;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
