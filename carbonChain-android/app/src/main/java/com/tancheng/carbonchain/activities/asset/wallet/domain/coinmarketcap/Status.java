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
public class Status {

    private Date timestamp;
    private int error_code;
    private String error_message;
    private int elapsed;
    private int credit_count;
    public void setTimestamp(Date timestamp) {
         this.timestamp = timestamp;
     }
     public Date getTimestamp() {
         return timestamp;
     }

    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setError_message(String error_message) {
         this.error_message = error_message;
     }
     public String getError_message() {
         return error_message;
     }

    public void setElapsed(int elapsed) {
         this.elapsed = elapsed;
     }
     public int getElapsed() {
         return elapsed;
     }

    public void setCredit_count(int credit_count) {
         this.credit_count = credit_count;
     }
     public int getCredit_count() {
         return credit_count;
     }

}