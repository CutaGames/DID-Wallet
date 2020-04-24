package com.tancheng.carbonchain.activities.asset.wallet.domain;


public class TransactionOperation {
    public String transactionId;
    public String viewType;
    public String from;
    public String to;
    public String value;
    public TransactionContract contract;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TransactionContract getContract() {
        return contract;
    }

    public void setContract(TransactionContract contract) {
        this.contract = contract;
    }
}
