package com.qcwp.carmanager.model;


import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by qyh on 2016/11/14.
 */

public class UserInfoModel implements Serializable {

    private  String mobilePhone;
    private  String userName;
    private  int age;
    private  String  email;
    private  int sex;
    private  String  createDate;
    private  String  qq;
    private  int integral;
    private  String  realName;
    private  String  portraitUrl;





    private static class UserInfoHolder{

        static final UserInfoModel INSTANCE = new UserInfoModel();
    }
    /**
     * * private的构造函数用于避免外界直接使用new来实例化对象
       */
    private UserInfoModel(){


    }

    public static UserInfoModel setInstance(JSONObject jsonObject) {

        UserInfoModel userInfoModel = UserInfoHolder.INSTANCE;



        return userInfoModel;
    }

    public static UserInfoModel getInstance() {

        return UserInfoHolder.INSTANCE;
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
      private Object readResolve() {
             return getInstance();
      }
}
