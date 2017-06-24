package com.qcwp.carmanager.enumeration;

/**
 * Created by qyh on 2017/1/3.
 */

public enum UploadStatusEnum {

    HadUpload(0),
    NotUpload(1);

    private final int value;
    UploadStatusEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }



}
