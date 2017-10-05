package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/6/6.
 */

public enum LoadDataTypeEnum {
    dataTypedrive (0),
    dataTypeTijian (1),
    dataTypeTest (2),
    dataTypeClearErr (3),
    dataTypeReConnection (100),
    dataTypeDisConnected (-1);

    private final int value;
    LoadDataTypeEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
