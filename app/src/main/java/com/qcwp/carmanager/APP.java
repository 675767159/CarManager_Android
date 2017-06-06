package com.qcwp.carmanager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.engine.TokenInterceptor;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import cn.smssdk.SMSSDK;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.qcwp.carmanager.enumeration.TimeEnum.DEFAULT_TIMEOUT;

/**
 * Created by qyh on 2017/5/31.
 */

public class APP extends Application {
    private Engine mEngine;
    private static APP sInstance;
    // 通过改变isDebug，实现Debug、Release版
    public final static boolean isDebug = BuildConfig.DEBUG;

    private double mScreenSize;
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private float mScreenDensity = 0.0f;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mEngine = new Retrofit.Builder()
                .baseUrl(Engine.HuaYIKangDaoBaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build().create(Engine.class);


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                MyActivityManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                MyActivityManager.getInstance().removeActivity(activity);
            }


        });


        SMSSDK.initSDK(this,this.getString(R.string.ShareSDK_Key),this.getString(R.string.ShareSDK_Secret));

        this.getScreenPhysicalSize();

    }
    public static APP getInstance() {
        return sInstance;
    }
    public Engine getEngine() {
        return mEngine;
    }

    public String getMyFileFolder(String fileName) {
         String mFileFolder;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mFileFolder = this.getExternalCacheDir().getPath() + File.separator+fileName;
        } else {
            mFileFolder =this.getCacheDir().getPath() + File.separator+fileName;
        }

        return mFileFolder;
    }

    private void getScreenPhysicalSize() {

        DisplayMetrics dm=getResources().getDisplayMetrics();
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        mScreenSize=diagonalPixels / (160 * dm.density);
        BigDecimal bd = new BigDecimal(mScreenSize);
        bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        mScreenSize=Double.parseDouble(bd+"");
        mScreenDensity = dm.density;
        mScreenHeight = dm.heightPixels;
        mScreenWidth = dm.widthPixels;
        Print.d("getScreenPhysicalSize",mScreenDensity+"");
        Print.d("getScreenPhysicalSize",mScreenWidth+"");
        Print.d("getScreenPhysicalSize",mScreenHeight+"");
        Print.d("getScreenPhysicalSize", dm.densityDpi+"");
    }
    public float getMyScreenDensity() {
        return mScreenDensity;
    }

    public int getMyScreenWidth() {
        return mScreenWidth;
    }

    public int getMyScreenHeight() {
        return mScreenHeight;
    }

    public Double getMyScreenSize() {
        return mScreenSize ;
    }
}
