package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by collin on 2020/4/15 10:55
 * Description: eventBus消息封装
 * version: 1.0
 */
public class MessageWrap<T> {
    public  String eventType;
    public T data;

    public MessageWrap(String eventType, T data) {
        this.eventType = eventType;
        this.data = data;
    }
}
