package com.tancheng.carbonchain.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfoBean {
    private String did;
    private String tocken;

    @Generated(hash = 233136831)
    public UserInfoBean(String did, String tocken) {
        this.did = did;
        this.tocken = tocken;
    }
    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public String getDid() {
        return this.did;
    }
    public void setDid(String did) {
        this.did = did;
    }
    public String getTocken() {
        return this.tocken;
    }
    public void setTocken(String tocken) {
        this.tocken = tocken;
    }
}
