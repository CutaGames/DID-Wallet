package com.carbonchain.server.service.wallet.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionModel implements Serializable {
    public String hash;
    public String blockNumber;
    public long timeStamp;
    public int nonce;
    public String from;
    public String to;
    public String value;
    public String gas;
    public String gasPrice;
    public String gasUsed;
    public String input;
    public TransactionOperation[] operations;
    public String contract;
    public String error;

}
