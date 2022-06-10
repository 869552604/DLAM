package com.first.dlam.bean;

import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.manager.LineChartManager;

import java.io.Serializable;
import java.util.List;

public class AdapterBean1 implements Serializable {
    private String code;
    private String[] title;
    private Float max;
    private Float min;
    private List<IncomeBean> incomeBean_5;
    private List<CompositeIndexBean> incomeBean_3_5 ;
    private List<CompositeIndexBean> incomeBean_4_5 ;
    private List<CompositeIndexBean> incomeBean_6_5 ;
    private int string3;
    private int string4;
    private int string6;
    private String time;

    private List<CompositeIndexBean> incomeBean_max ;
    private List<CompositeIndexBean> incomeBean_min ;

    public AdapterBean1(String code,String[] title,Float max,Float min,List<IncomeBean> incomeBean_5,List<CompositeIndexBean> incomeBean_3_5
    ,List<CompositeIndexBean> incomeBean_4_5,List<CompositeIndexBean> incomeBean_6_5,int string3,int string4,int string6){
        this.code=code;
        this.title=title;
        this.max=max;
        this.min=min;
        this.incomeBean_5=incomeBean_5;
        this.incomeBean_3_5=incomeBean_3_5;
        this.incomeBean_4_5=incomeBean_4_5;
        this.incomeBean_6_5=incomeBean_6_5;
        this.string3=string3;
        this.string4=string4;
        this.string6=string6;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public List<IncomeBean> getIncomeBean_5() {
        return incomeBean_5;
    }

    public void setIncomeBean_5(List<IncomeBean> incomeBean_5) {
        this.incomeBean_5 = incomeBean_5;
    }


    public List<CompositeIndexBean> getIncomeBean_3_5() {
        return incomeBean_3_5;
    }

    public void setIncomeBean_3_5(List<CompositeIndexBean> incomeBean_3_5) {
        this.incomeBean_3_5 = incomeBean_3_5;
    }

    public List<CompositeIndexBean> getIncomeBean_4_5() {
        return incomeBean_4_5;
    }

    public void setIncomeBean_4_5(List<CompositeIndexBean> incomeBean_4_5) {
        this.incomeBean_4_5 = incomeBean_4_5;
    }

    public List<CompositeIndexBean> getIncomeBean_6_5() {
        return incomeBean_6_5;
    }

    public void setIncomeBean_6_5(List<CompositeIndexBean> incomeBean_6_5) {
        this.incomeBean_6_5 = incomeBean_6_5;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getString3() {
        return string3;
    }

    public void setString3(int string3) {
        this.string3 = string3;
    }

    public int getString4() {
        return string4;
    }

    public void setString4(int string4) {
        this.string4 = string4;
    }

    public int getString6() {
        return string6;
    }

    public void setString6(int string6) {
        this.string6 = string6;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CompositeIndexBean> getIncomeBean_max() {
        return incomeBean_max;
    }

    public void setIncomeBean_max(List<CompositeIndexBean> incomeBean_max) {
        this.incomeBean_max = incomeBean_max;
    }

    public List<CompositeIndexBean> getIncomeBean_min() {
        return incomeBean_min;
    }

    public void setIncomeBean_min(List<CompositeIndexBean> incomeBean_min) {
        this.incomeBean_min = incomeBean_min;
    }
}
