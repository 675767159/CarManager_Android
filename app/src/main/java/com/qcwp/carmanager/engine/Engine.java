package com.qcwp.carmanager.engine;



import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.PhoneAuthModel;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.retrofitModel.TokenModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by qyh on 2016/11/17.
 */

public  interface Engine {

      String QiCheWangPing = "http://www.qcwp.com/";



    @FormUrlEncoded
    @POST("doMember_appLogin")
    Call<LoginModel> login(@Field("loginName") String loginName, @Field("password") String password);

    @FormUrlEncoded
    @POST("doMember_isRegisterTelCanUseForApp")
    Call<PhoneAuthModel> mobilephoneAuth(@Field("tel") String mobilePhone);


    @FormUrlEncoded
    @POST("doMember_appRegister")
    Call<LoginModel> registerUser(@Field("userName") String userName, @Field("tel") String mobilePhone, @Field("password") String password, @Field("nickName") String nickName);


    @POST("doVin_bindVin")
    Call<RequestModel> uploadCarInfo(@Body CarInfoModel carInfoModel);

    @FormUrlEncoded
    @POST("doMember_getVinsByMemberId")
    Call<AllCarModel> getMyAllCarInfo(@Field("id") int userId);

    @FormUrlEncoded
    @POST("doBrand_getBrandByIdgt")
    Call<AllCarModel> getAllCarBrand(@Field("id") long ID);


    @FormUrlEncoded
    @POST("doCommonBrand_getCommonBrandByIdgt")
    Call<AllCarModel> getAllCommonCarBrand(@Field("id") long ID);


    @FormUrlEncoded
    @POST("doCarSeries_getCarSeriesByIdgt")
    Call<AllCarModel> getAllCarSeries(@Field("id") long ID);

    @FormUrlEncoded
    @POST("doCarType_getCarTypeByIdgt")
    Call<AllCarModel> getAllCarType(@Field("id") long ID);




    @Multipart
    @POST("doTravelDataUploadFile_appUpload")
    Call<AllCarModel> uploadDriveData( @Part MultipartBody.Part file ,@Query("token") String token);


    @FormUrlEncoded
    @POST("doAppToken_getToken")
    Call<TokenModel> getToken(@Field("username") String username,@Field("password") String password,@Field("url") String url);



    @FormUrlEncoded
    @POST("doMapPoint_addPoints")
    Call<AllCarModel> uploadMapPointOfDrive(@FieldMap() Map<String,Object> map);


    @FormUrlEncoded
    @POST("doSummarize_driveCustom")
    Call<AllCarModel> uploadDriveCustom(@Field("vinCode") String vinCode, @Field("summarizeStartDate") String summarizeStartDate,@Field("jijiasuTotal") int accelerate,@Field("jijiansuTotal") int deceleration,@Field("chaosuTotal") int overSpeed);

    @FormUrlEncoded
    @POST("doCarCheck_addCarCheck")
    Call<AllCarModel> uploadPhysicalExamination(@Query("vincode") String vinCode, @Field("theCreateTime") String createDate, @Field("score") int score, @Field("fireSystem") String fireSystem, @Field("fuelSystem") String fuelSystem, @Field("letSystem") String letSystem, @Field("acSystem") String acSystem, @Field("oxygenSystem") String oxygenSystem, @Field("egrSystem") String egrSystem, @FieldMap() Map<String,String> faultCodes);

}
