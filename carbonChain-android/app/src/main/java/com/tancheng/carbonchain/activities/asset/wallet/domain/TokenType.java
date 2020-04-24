package com.tancheng.carbonchain.activities.asset.wallet.domain;

import com.tancheng.carbonchain.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by collin on 2020/3/18 12:03
 * Description: 代币类型 钱包类型
 * version: 1.0
 */
public enum TokenType {

    BTC(1, "Bitcoin", "BTC", "", R.mipmap.coin_icon_btc, 18, WalletType.BTC.getWalletType(),R.color.theme_color_btc),
    ETH(1027, "Ethereum", "ETH", "", R.mipmap.coin_icon_eth, 18, WalletType.ETH.getWalletType(),R.color.theme_color_eth),
    USDT(825, "Tether USD", "USDT", "0xdac17f958d2ee523a2206206994597c13d831ec7", R.mipmap.coin_icon_usdt, 6, WalletType.ETH.getWalletType(),R.color.theme_color_usdt),
    TCT(827, "touch Chain ", "TCT", "0x0f576b383112d2001abc00e5e3a7a391dfea28b1", R.mipmap.coin_icon_usdt, 1, WalletType.ETH.getWalletType(),R.color.theme_color_usdt);

    private final int id;//币种id需要到coinMarkCap上查该币种的id，用于查询币种币价
    private final String name;//名称
    private final String symbol;//代号
    private final String address;//合约地址
    private final int logoUrl;//logo地址
    private final Integer decimals;//小数位数
    private final int walletType;//所属钱包类型
    private final int themeColor;//主题颜色

    TokenType(int id,
              String name,
              String symbol,
              String address,
              int logoUrl,
              Integer decimals,
              int walletType
    ,int themeColor) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.address = address;
        this.logoUrl = logoUrl;
        this.decimals = decimals;
        this.walletType = walletType;
        this.themeColor = themeColor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAddress() {
        return address;
    }

    public int getLogoUrl() {
        return logoUrl;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public int getWalletType() {
        return walletType;
    }

    public int getThemeColor() {
        return themeColor;
    }


    public static TokenType of(int type) {
        switch (type) {
            case 1:
                return BTC;
            case 1027:
                return ETH;
            case 825:
                return USDT;
            case 827:
                return TCT;
        }
        throw new IllegalArgumentException("not support coin type : " + type);
    }

    public static List<TokenType> getTokenList() {
        TokenType[] values = TokenType.values();
        List<TokenType> walletTypes = Arrays.asList(values);
        return walletTypes;
    }

    /**
     * 默认币种
     *
     * @param walletTypeId
     * @return
     */
    public static TokenType getDefaultTokenByWalletType(int walletTypeId) {
        switch (walletTypeId) {
            case 1:
                return TokenType.BTC;
            case 2:
                return TokenType.ETH;
        }
        return TokenType.ETH;
    }

    /**
     * 获取钱包类型中支持的币种集合
     *
     * @param walletType
     * @return
     */
    public static List<TokenType> getTokenListByWalletType(int walletType) {
        List<TokenType> tokenTypeList = new ArrayList<>();
        TokenType[] values = TokenType.values();
        for (TokenType tokenType : values) {
            if (tokenType.walletType == walletType)
                tokenTypeList.add(tokenType);
        }
        return tokenTypeList;
    }

}
