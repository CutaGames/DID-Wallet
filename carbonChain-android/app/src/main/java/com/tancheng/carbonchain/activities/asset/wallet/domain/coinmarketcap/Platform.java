package com.tancheng.carbonchain.activities.asset.wallet.domain.coinmarketcap;

/**
 * created by tc_collin on 2020/4/13 11:44
 * Description:
 * version: 1.0
 */
public class Platform {
    private int id;
    private String name;
    private String symbol;
    private String slug;
    private String token_address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getToken_address() {
        return token_address;
    }

    public void setToken_address(String token_address) {
        this.token_address = token_address;
    }
}
