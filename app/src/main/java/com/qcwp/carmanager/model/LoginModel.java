package com.qcwp.carmanager.model;

/**
 * Created by qyh on 2017/5/31.
 */

public class LoginModel {

    /**
     * username : quyongheng
     * status : 1
     * memberId : 53696
     */

    private String username;
    private int status;
    private int memberId;
    private String msg;
    private String password;

    @Override
    public String toString() {
        return "LoginModel{" +
                "username='" + username + '\'' +
                ", status=" + status +
                ", memberId=" + memberId +
                ", msg='" + msg + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
