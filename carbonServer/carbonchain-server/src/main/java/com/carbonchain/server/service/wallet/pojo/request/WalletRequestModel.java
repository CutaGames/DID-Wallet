package com.carbonchain.server.service.wallet.pojo.request;

import com.carbonchain.server.service.wallet.pojo.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * created by tc_collin on 2020/3/23 14:06
 * Description: wallet相关请求参数
 * version: 1.0
 */
@Data
public class WalletRequestModel extends BaseModel implements Serializable {
  private String walletId;
  private String walletType;
  private String pwd;
  private String coinId;
  private String walletAddress;
  private int action;//0删除 1添加
}
