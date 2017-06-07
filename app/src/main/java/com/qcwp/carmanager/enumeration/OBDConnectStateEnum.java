package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/1/3.
 */

public enum OBDConnectStateEnum {

    connectTypeConnectSuccess(1),
    connectTypeDisconnection (2),
    connectTypeDisconnectionWithTimeOut (3),
    connectTypeDisconnectionWithProtocol(4),
    connectTypeGetData (5),
    connectTypeConnectingWithWiFi (6),
    connectTypeConnectingWithOBD (7),
    connectTypeConnectingWithSP (8),
    connectTypeHaveBinded (9),
    connectTypeDisconnectionWithWIFi (10),
    connectTypeDisconnectionWithOBD (11),
    Unknown(-1);

    private final int value;
    OBDConnectStateEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }




}
