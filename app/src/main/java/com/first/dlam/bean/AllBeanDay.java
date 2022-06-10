package com.first.dlam.bean;

import java.io.Serializable;
import java.util.List;

public class AllBeanDay implements Serializable {

    private List<List<String>> qfqday;

    public List<List<String>> getQfqday() {
        return qfqday;
    }

    public void setQfqday(List<List<String>> qfqday) {
        this.qfqday = qfqday;
    }

    @Override
    public String toString() {
        return "AllBeanDay{" +
                "qfqday=" + qfqday +
                '}';
    }

    private List<List<String>> day;

    public List<List<String>> getDay() {
        return day;
    }

    public void setDay(List<List<String>> day) {
        this.day = day;
    }
}
