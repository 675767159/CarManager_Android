package com.qcwp.carmanager.mvp.contact;


/**
 * Created by qyh on 2017/9/11.
 *
 * @email:675767159@qq.com
 */

public class LocationContract {

    public interface Presenter  {
        void didUpdateBMKUserLocation(double latitude, double longitude);
    }
}
