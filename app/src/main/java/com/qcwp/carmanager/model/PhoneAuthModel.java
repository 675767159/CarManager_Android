package com.qcwp.carmanager.model;

/**
 * Created by qyh on 2017/5/31.
 */

public class PhoneAuthModel {
    /**
     * msg : 请输入手机号码!
     * canUse : 0
     */

    private String msg;
    private int canUse;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCanUse() {
        return canUse;
    }

    public void setCanUse(int canUse) {
        this.canUse = canUse;
    }
}
