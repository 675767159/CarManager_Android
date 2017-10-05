package com.qcwp.carmanager.engine;

/**
 * Created by qyh on 2017/9/30.
 *
 * @email:675767159@qq.com
 */

public class ApiException extends Exception {

    private int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(Throwable throwable) {
        super(throwable);

    }
    public ApiException() {
        super();

    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public String getDisplayMessage() {
        return displayMessage;
    }
    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }
}
