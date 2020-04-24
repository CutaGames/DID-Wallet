package com.carbonchain.server.service.wallet.pojo.response;

import com.carbonchain.server.model.WalletAccount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by tc_collin on 2020/3/24 11:46
 * Description:
 * version: 1.0
 */
@Data
public class WalletInfoModel extends WalletAccount {

    List<CoinResponse> walletCoinlist;

    @Data
   public static class CoinResponse{
        private Integer id;
        private String name;
        private String symbol;
        private String address;
        private String logoUrl;
        private Integer walletId;
        private String userId;
        private BigDecimal balance;
    }
}
