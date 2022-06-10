package com.first.dlam.bean;

import java.io.Serializable;

public class FilterBean implements Serializable {
    private int position;

    public FilterBean(int position){
        this.position=position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
