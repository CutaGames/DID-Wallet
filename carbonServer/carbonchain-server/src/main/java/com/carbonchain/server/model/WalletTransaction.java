package com.carbonchain.server.model;

import lombok.Data;

import java.io.Serializable;

/**
 * wallet_transaction
 *
 * @author
 */
@Data
public class WalletTransaction implements Serializable {

    private Integer id;

    private Integer walletType;

    private String coinAddress;

    private String fromAddr;

    private String toAddr;

    private Double value;

    private String txHash;

    private String timeStamp;

    private Integer status;// 0 pendding 1 success 2 fail

    private Integer direct;//方向  0发送 1收款

    private Long gas;

    private Long gasLimit;

    private String blockNumber;

    private Integer error;

    private String remark;

}