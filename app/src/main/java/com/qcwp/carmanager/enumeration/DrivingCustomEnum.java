package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/1/3.
 */

public enum DrivingCustomEnum {

    Unknown(0),
    Deceleration(1),
    Acceleration(2),
    Overdrive(3);



    private final int value;
    DrivingCustomEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }



}
