/**
 * Copyright 2020 bejson.com
 */
package com.tancheng.carbonchain.activities.asset.wallet.domain.coinmarketcap;

import java.util.Date;

/**
 * Auto-generated: 2020-04-13 11:39:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private int id;
    private String name;
    private String symbol;
    private String slug;
    private int is_active;
    private Date first_historical_data;
    private Date last_historical_data;
    private Platform platform;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setFirst_historical_data(Date first_historical_data) {
        this.first_historical_data = first_historical_data;
    }

    public Date getFirst_historical_data() {
        return first_historical_data;
    }

    public void setLast_historical_data(Date last_historical_data) {
        this.last_historical_data = last_historical_data;
    }

    public Date getLast_historical_data() {
        return last_historical_data;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

}