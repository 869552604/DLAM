package com.first.dlam.bean;

import java.io.Serializable;
import java.util.List;

public class AllBeanWeek implements Serializable {
    private List<List<String>> qfqweek;

    public List<List<String>> getQfqweek() {
        return qfqweek;
    }

    public void setQfqweek(List<List<String>> qfqweek) {
        this.qfqweek = qfqweek;
    }

    private List<List<String>> week;

    public List<List<String>> getWeek() {
        return week;
    }

    public void setWeek(List<List<String>> week) {
        this.week = week;
    }
}
