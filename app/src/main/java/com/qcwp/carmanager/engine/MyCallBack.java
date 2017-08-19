package com.qcwp.carmanager.engine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/12.
 *
 * @email:675767159@qq.com
 */

public abstract  class MyCallBack<T> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onCompleted();
        onSuccess(call,response);

    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onCompleted();
        onFailed(call,throwable);

    }

    public abstract void onCompleted();

    public abstract void onSuccess(Call<T> call, Response<T> response);

    public abstract void onFailed(Call<T> call, Throwable throwable);
}
