/**
  * Copyright 2020 bejson.com 
  */
package com.tancheng.carbonchain.activities.asset.wallet.domain.coinmarketcap;
import java.util.List;

/**
 * Auto-generated: 2020-04-13 11:39:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private List<Data> data;
    private Status status;
    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public void setStatus(Status status) {
         this.status = status;
     }
     public Status getStatus() {
         return status;
     }

}