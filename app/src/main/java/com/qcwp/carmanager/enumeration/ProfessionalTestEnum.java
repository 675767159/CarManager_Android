package com.qcwp.carmanager.enumeration;

import com.qcwp.carmanager.R;

/**
 * Created by qyh on 2017/1/3.
 */

public enum ProfessionalTestEnum {

    HectometerAccelerate(2),
    KilometersAccelerate(5),
    KilometersBrake(6),
    Noise(7),
    Unknown(0);

    private final int value;
    ProfessionalTestEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }



}
