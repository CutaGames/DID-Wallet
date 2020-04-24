package com.carbonchain.server.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName(value = "wallet_account")//指定表名
public class WalletAccount {
    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private Integer id;

    private String userId;

    private String mnemonic;

    private String name;

    private String address;

    private Integer isback;

    private String publicKey;

    private String path;

    private String remark;

    private Integer type;

    private String coins;//支持的代币币种


}