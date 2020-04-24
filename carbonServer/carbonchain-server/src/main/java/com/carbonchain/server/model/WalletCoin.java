package com.carbonchain.server.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * wallet_coin
 * @author 
 */
@Data
@TableName(value = "wallet_coin")
public class WalletCoin implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String symbol;

    private String address;

    private String logoUrl;

    private Double totalSupply;

    private Integer decimals;

    private String remark;

    private int walletType;

    private int serialNum;//排序顺序号

    private int enable;//0不可用 1可用

}