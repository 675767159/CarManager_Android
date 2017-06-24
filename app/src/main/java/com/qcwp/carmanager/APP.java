package com.qcwp.carmanager;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.engine.TokenInterceptor;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.DaoMaster;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.implement.GreenDaoContext;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private DaoSession daoSession;
    public static byte[] DESKey={27,19,23,67,90,56,78,55};
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;

        List neddTokeApis= Arrays.asList(getResources().getStringArray(R.array.NeedTokenAPI));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).addInterceptor(new TokenInterceptor(neddTokeApis))
                .build();

        mEngine = new Retrofit.Builder()
                .baseUrl(Engine.QiCheWangPing)
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

        this.setupDatabase();




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

         double mScreenSize;
         int mScreenWidth = 0;
         int mScreenHeight = 0;
         float mScreenDensity = 0.0f;

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

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"

        GreenDaoContext greenDaoContext=new GreenDaoContext(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(greenDaoContext,"CarManager.db", null);

        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();


        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoInstant() {
        return daoSession;
    }
}
