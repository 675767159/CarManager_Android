package com.qcwp.carmanager.mvp.present;


import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.engine.Engine;


/**
 * Created by qyh on 2016/12/20.
 */

public  abstract class BasePresenter  {
    protected APP mApp=APP.getInstance();
    protected  Engine mEngine=mApp.getEngine();


    private int count=0;
    abstract int getRequestCount();
    protected void completeOnceRequest(){
        count++;
        if (count==getRequestCount()){
            count=0;
            completeAllRequest();
        }
    }
    abstract void  completeAllRequest();
}
