package com.qcwp.carmanager.mvp.present;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.engine.MyCallBack;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.mvp.contact.LoginContract;
import com.qcwp.carmanager.utils.Print;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class Calculator  {

    public double sum(double a, double b){
        return a + b;
    }

    public double substract(double a, double b){
        return a-b;
    }

    public double divide(double a, double b){
        return a/b;
    }

    public double multiply(double a, double b){
        return a*b;
    }


}
