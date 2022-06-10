package com.first.dlam.bean;

import java.io.Serializable;

public class FirstBean implements Serializable {
    public String message;
    public int type=0;
    public static FirstBean getInstance(int type,String message) {
        return new FirstBean(type,message);
    }

    private FirstBean(int type,String message) {
        this.message = message;
        this.type=type;
    }
}
