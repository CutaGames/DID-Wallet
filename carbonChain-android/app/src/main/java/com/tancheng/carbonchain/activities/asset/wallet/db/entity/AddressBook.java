package com.tancheng.carbonchain.activities.asset.wallet.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * created by tc_collin on 2020/4/2 10:12
 * Description: 地址簿
 * version: 1.0
 */
@Entity
public class AddressBook {
    @Id(autoincrement = true)
    private Long id;

    private String name;//名称

    private String remark;//备注

    private String address;//地址

    private int walletType;//钱包类型id

    @Generated(hash = 140609479)
    public AddressBook(Long id, String name, String remark, String address,
            int walletType) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.address = address;
        this.walletType = walletType;
    }

    @Generated(hash = 44583721)
    public AddressBook() {
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWalletType() {
        return this.walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

}
