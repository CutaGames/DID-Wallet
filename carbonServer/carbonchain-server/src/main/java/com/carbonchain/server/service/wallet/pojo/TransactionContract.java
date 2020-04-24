package com.carbonchain.server.service.wallet.pojo;

import lombok.Data;

/**
 * created by tc_collin on 2020/3/19 20:43
 * Description: erc20代币合约
 * version: 1.0
 */
@Data
public class TransactionContract {
    public String address;
    public String name;
    public String totalSupply;
    public int decimals;
    public String symbol;
}
