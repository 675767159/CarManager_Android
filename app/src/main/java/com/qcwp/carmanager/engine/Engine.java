package com.qcwp.carmanager.engine;



import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.PhoneAuthModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by qyh on 2016/11/17.
 */

public  interface Engine {

      String HuaYIKangDaoBaseUrl = "http://www.qcwp.com/";



    @FormUrlEncoded
    @POST("doMember_appLogin")
    Call<LoginModel> login(@Field("loginName") String loginName, @Field("password") String password);

    @FormUrlEncoded
    @POST("doMember_isRegisterTelCanUseForApp")
    Call<PhoneAuthModel> mobilephoneAuth(@Field("tel") String mobilePhone);


    @FormUrlEncoded
    @POST("doMember_appRegister")
    Call<LoginModel> registerUser(@Field("userName") String userName, @Field("tel") String mobilePhone, @Field("password") String password, @Field("nickName") String nickName);
}
