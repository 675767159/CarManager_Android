package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/9/30.
 *
 * @email:675767159@qq.com
 */

public enum OBDConnectType{

    Unknown(0),
    WIFI(1),
    BlueTooth(2);

    private final int value;
    OBDConnectType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static OBDConnectType fromInteger(int value){
        switch (value){
            case 1:
                return WIFI;
            case 2:
                return BlueTooth;
            default:
                return Unknown;
        }

    }

}
