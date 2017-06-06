package com.qcwp.carmanager.model;


import com.google.auto.value.AutoValue;

/**
 * Created by qyh on 2017/6/5.
 */
@AutoValue
public abstract class User {
    abstract String name();
    abstract String addr();
    abstract int age();
    abstract String gender();
    abstract String hobby();
    abstract String sign();

    //创建User，内部调用的是AutoValue_User
    public static User create(String name,String addr,int age,String gender,String hobby,String sign){
        return new AutoValue_User(name,addr,age,gender,hobby,sign);
    }


}
