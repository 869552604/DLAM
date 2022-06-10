package com.first.dlam.bean;

import java.io.Serializable;

public class HistoryBean implements Serializable {
    private String time;
    private String week;
    private String info;

    public HistoryBean(String time,String week,String info){
        this.time=time;
        this.week=week;
        this.info=info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
