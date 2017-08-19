package com.qcwp.carmanager.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by qyh on 2016/11/14.
 */

public class UserData implements Serializable {




    public String getPassword() {
        return password;
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


    private int userId;
    private String password;
    private String userName;
    private double userLocaLatitude;
    private double userLocaLongitude;

    /**
     * * private的构造函数用于避免外界直接使用new来实例化对象
       */
    private UserData(){



    }

    public static UserData setInstance(LoginModel model) {

        UserData userData =UserData.getInstance();
        userData.userId=model.getMemberId();
        userData.userName=model.getUsername();
        userData.password=model.getPassword();
        return userData;
    }

    public static void dropInstance() {

        UserData userData =UserData.getInstance();
        userData.userId=0;
        userData.userName=null;
        userData.password=null;

    }


    private static UserData INSTANCE;

    // 提供一个全局的静态方法
    public static UserData getInstance() {
        if (INSTANCE == null) {
            synchronized (UserData.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserData();
                }
            }
        }
        return INSTANCE;
    }


}
