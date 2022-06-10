package com.first.dlam.bean;

import java.io.Serializable;

public class BaseEvent implements Serializable {

    public final String[] message;

    public static BaseEvent getInstance(String[] message) {
        return new BaseEvent(message);
    }

    private BaseEvent(String[] message) {
        this.message = message;
    }
}
