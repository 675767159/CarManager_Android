package com.qcwp.carmanager.mvp.contact;

/**
 * Created by qyh on 2016/12/20.
 */

public interface BaseView <T>{

    /**
     * show loading message
     *
     * @param text
     */
    void showProgress(String text);

    /**
     * hide loading
     */
    void dismissProgress();



    void showTip(String message);

    Boolean isActive();



}
