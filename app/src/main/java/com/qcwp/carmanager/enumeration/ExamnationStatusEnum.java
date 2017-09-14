package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/1/3.
 */

public enum ExamnationStatusEnum {

    NORMAL(1),
    UNUSUAL (0),
    NOVALUE (-1),
    Unknown(-2);

    private final int value;
    ExamnationStatusEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }


    public static ExamnationStatusEnum fromInteger(int value){
        switch (value){
            case 1:
                return NORMAL;
            case 0:
                return UNUSUAL;
            case -1:
                return NOVALUE;
            default:
                return Unknown;
        }

    }


}
