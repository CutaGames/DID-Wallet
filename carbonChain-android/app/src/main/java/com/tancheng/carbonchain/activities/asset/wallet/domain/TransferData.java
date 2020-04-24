package com.tancheng.carbonchain.activities.asset.wallet.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * created by collin on 2020/3/20 10:02
 * Description: 转账参数
 * version: 1.0
 */
public class TransferData implements Serializable {
    private String coinType;
    private String from;
    private String to;
    private BigDecimal value;
    private String gas;
    private String gasPrice;
    private String remark;
    private TokenType tokenInfo;
    private String password;//密码

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TokenType getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenType tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
