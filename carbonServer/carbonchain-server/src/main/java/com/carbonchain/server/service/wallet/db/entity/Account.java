package com.carbonchain.server.service.wallet.db.entity;

/**
 * created by tc_collin on 2020/3/18 12:56
 * Description: 账户
 * version: 1.0
 */
public class Account {
    public static final int ETHER = 1;
    public static final int BTC = 2;
    public static final int BTC_TEST = -1;

    protected String id;
    protected String mnemonic;
    protected String name;
    protected int type;
    protected String privateKey;
    protected String extra = "";
    private String address;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        if (extra == null) {
            extra = "";
        }
        this.extra = extra;
    }

}
