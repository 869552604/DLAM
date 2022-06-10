package com.first.dlam.bean;

import java.io.Serializable;
import java.util.List;

public class AllBeanMonth implements Serializable {
    private List<List<String>> qfqmonth;

    public List<List<String>> getQfqmonth() {
        return qfqmonth;
    }

    public void setQfqmonth(List<List<String>> qfqmonth) {
        this.qfqmonth = qfqmonth;
    }

    private List<List<String>> month;

    public List<List<String>> getMonth() {
        return month;
    }

    public void setWeek(List<List<String>> month) {
        this.month = month;
    }
}
