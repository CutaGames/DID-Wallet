package com.carbonchain.server.service.wallet.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * created by tc_collin on 2020/3/20 10:02
 * Description: 转账参数
 * version: 1.0
 */
@Data
public class TransferData implements Serializable {
    public String coinType;
    public String from;
    public String to;
    public BigDecimal value;
    public String gas;
    public String gasPrice;
    public TransactionContract contract;
}
