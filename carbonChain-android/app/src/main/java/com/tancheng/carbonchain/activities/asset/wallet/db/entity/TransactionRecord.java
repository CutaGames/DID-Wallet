package com.tancheng.carbonchain.activities.asset.wallet.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * created by tc_collin on 2020/4/7 9:52
 * Description: 转账记录
 * version: 1.0
 */
@Entity
public class TransactionRecord {
    @Id(autoincrement = true)
    private Long id;

    private int tokenId;

    private Integer walletType;

    private String coinAddress;

    private String fromAddr;

    private String toAddr;

    private Double value;

    private String txHash;

    private Date timeStamp;

    private Integer status;//0 初始化 1 pendding 2 success 3 fail

    private Integer direct;//方向  0发送 1收款

    private Long gas;

    private Long gasLimit;

    private String fee;//手续费

    private String blockNumber;

    private Integer error;

    private String remark;

    @Generated(hash = 1472449946)
    public TransactionRecord(Long id, int tokenId, Integer walletType, String coinAddress,
            String fromAddr, String toAddr, Double value, String txHash, Date timeStamp, Integer status,
            Integer direct, Long gas, Long gasLimit, String fee, String blockNumber, Integer error,
            String remark) {
        this.id = id;
        this.tokenId = tokenId;
        this.walletType = walletType;
        this.coinAddress = coinAddress;
        this.fromAddr = fromAddr;
        this.toAddr = toAddr;
        this.value = value;
        this.txHash = txHash;
        this.timeStamp = timeStamp;
        this.status = status;
        this.direct = direct;
        this.gas = gas;
        this.gasLimit = gasLimit;
        this.fee = fee;
        this.blockNumber = blockNumber;
        this.error = error;
        this.remark = remark;
    }

    @Generated(hash = 1215017002)
    public TransactionRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public Integer getWalletType() {
        return this.walletType;
    }

    public void setWalletType(Integer walletType) {
        this.walletType = walletType;
    }

    public String getCoinAddress() {
        return this.coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    public String getFromAddr() {
        return this.fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getToAddr() {
        return this.toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTxHash() {
        return this.txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDirect() {
        return this.direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public Long getGas() {
        return this.gas;
    }

    public void setGas(Long gas) {
        this.gas = gas;
    }

    public Long getGasLimit() {
        return this.gasLimit;
    }

    public void setGasLimit(Long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getBlockNumber() {
        return this.blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getError() {
        return this.error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

}
