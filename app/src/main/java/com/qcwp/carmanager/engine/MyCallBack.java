package com.qcwp.carmanager.engine;

import com.blankj.utilcode.util.NetworkUtils;
import com.qcwp.carmanager.utils.Print;

import java.io.IOException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/12.
 *
 * @email:675767159@qq.com
 */

public abstract  class MyCallBack<T> implements Callback<T> {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //出错提示
    private final String networkMsg="网络已断开，请检查网络连接！";
    private final String parseMsg="解析出错！";
    private final String unknownMsg="未知错误！";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onCompleted();
       if (response.isSuccessful()) {
            onSuccess(call, response.body());
        }else {
           ApiException apiException=new ApiException();
           apiException.setCode(response.code());
           apiException.setDisplayMessage(response.message());
           onFailed(call,apiException);
       }

    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {

        onCompleted();
        ApiException apiException=new ApiException(throwable);
        if (throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            int code=httpException.code();
            switch(code){
                case UNAUTHORIZED:
                case FORBIDDEN:
                   apiException.setDisplayMessage("已被禁止联网！");         //权限错误，需要实现
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    apiException.setDisplayMessage(networkMsg);  //均视为网络错误
                    break;
            }

            apiException.setCode(code);

        }else if (throwable instanceof UnknownHostException){
            if (NetworkUtils.isAvailableByPing()){
                apiException.setDisplayMessage("服务器有误！");  //均视为网络错误
            }else {
                apiException.setDisplayMessage(networkMsg);  //均视为网络错误
            }
        }
        else {
            apiException.setDisplayMessage(apiException.getLocalizedMessage());
        }

        onFailed(call,apiException);



    }

    public abstract void onCompleted();

    public abstract void onSuccess(Call<T> call, T model);

    public abstract void onFailed(Call<T> call, ApiException apiException);
}
