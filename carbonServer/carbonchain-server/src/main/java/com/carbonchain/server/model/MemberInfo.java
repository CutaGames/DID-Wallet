package com.carbonchain.server.model;

import java.util.Date;

public class MemberInfo {
    private String memberId;

    private String publicKey;

    private String nickName;

    private String role;

    private String password;

    private String bindedDevice;

    private String authSignPuk;

    private String decodeSecretKey;

    private String securityQuestions;

    private Integer state;

    private Date createTime;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBindedDevice() {
        return bindedDevice;
    }

    public void setBindedDevice(String bindedDevice) {
        this.bindedDevice = bindedDevice;
    }

    public String getAuthSignPuk() {
        return authSignPuk;
    }

    public void setAuthSignPuk(String authSignPuk) {
        this.authSignPuk = authSignPuk;
    }

    public String getDecodeSecretKey() {
        return decodeSecretKey;
    }

    public void setDecodeSecretKey(String decodeSecretKey) {
        this.decodeSecretKey = decodeSecretKey;
    }

    public String getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(String securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}