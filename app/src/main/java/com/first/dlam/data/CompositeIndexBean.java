package com.first.dlam.data;

import java.io.Serializable;

/**
 * Created by xhu_ww on 2018/6/1.
 * description:
 */
public class CompositeIndexBean implements Serializable {
    private double rate;
    private String tradeDate;

    public CompositeIndexBean(String tradeDate,double rate){
        this.tradeDate=tradeDate;
        this.rate=rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Override
    public String toString() {
        return "CompositeIndexBean{" +
                "rate=" + rate +
                ", tradeDate='" + tradeDate + '\'' +
                '}';
    }
}
