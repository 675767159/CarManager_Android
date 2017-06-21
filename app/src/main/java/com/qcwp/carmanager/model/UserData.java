package com.qcwp.carmanager.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by qyh on 2016/11/14.
 */

public class UserData implements Serializable {


    private int userId;
    private String password;
    private String userName;
    private double userLocaLatitude;
    private double userLocaLongitude;

    public String getPassword() {
        return password;
    }

    private static class UserDataHolder{

        static final UserData INSTANCE = new UserData();
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getUserLocaLatitude() {
        return userLocaLatitude;
    }

    public double getUserLocaLongitude() {
        return userLocaLongitude;
    }

    /**
     * * private的构造函数用于避免外界直接使用new来实例化对象
       */
    private UserData(){



    }

    public static UserData setInstance(LoginModel model) {

        UserData userData = UserDataHolder.INSTANCE;
        userData.userId=model.getMemberId();
        userData.userName=model.getUsername();
        userData.password=model.getPassword();
        return userData;
    }

    public static UserData getInstance() {

        return UserDataHolder.INSTANCE;
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
      private Object readResolve() {
             return getInstance();
      }
}
