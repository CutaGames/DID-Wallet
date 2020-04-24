package com.tancheng.carbonchain.activities.asset.wallet.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * wallet_coin
 * 币种表
 *
 * @author
 */
@Entity
public class WalletCoin {

    @Id(autoincrement = true)
    private Long id;

    private String name;//名称

    private String symbol;//代号

    private String address;//合约地址

    private String logoUrl;//logo地址

    private Double totalSupply;//总供应量

    private Integer decimals;//小数位数

    private String remark;

    private int walletType;//所属钱包类型

    private int serialNum;//排序顺序号

    private int enable;//0不可用 1可用

    @Generated(hash = 1493527586)
    public WalletCoin(Long id, String name, String symbol, String address,
            String logoUrl, Double totalSupply, Integer decimals, String remark,
            int walletType, int serialNum, int enable) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.address = address;
        this.logoUrl = logoUrl;
        this.totalSupply = totalSupply;
        this.decimals = decimals;
        this.remark = remark;
        this.walletType = walletType;
        this.serialNum = serialNum;
        this.enable = enable;
    }

    @Generated(hash = 1595126670)
    public WalletCoin() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Double getTotalSupply() {
        return this.totalSupply;
    }

    public void setTotalSupply(Double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Integer getDecimals() {
        return this.decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getWalletType() {
        return this.walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public int getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getEnable() {
        return this.enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

}