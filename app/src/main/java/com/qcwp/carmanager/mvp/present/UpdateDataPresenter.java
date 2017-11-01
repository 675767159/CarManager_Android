package com.qcwp.carmanager.mvp.present;

import android.database.Cursor;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.enumeration.DrivingCustomEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarCheckModelDao;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.DrivingCustomModelDao;
import com.qcwp.carmanager.greendao.gen.LocationModelDao;
import com.qcwp.carmanager.greendao.gen.TravelDataModelDao;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.retrofitModel.TokenModel;
import com.qcwp.carmanager.model.sqLiteModel.CarCheckModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.DrivingCustomModel;
import com.qcwp.carmanager.model.sqLiteModel.LocationModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class UpdateDataPresenter {

    private final DaoSession mDaoSession;
    private final  Engine mEngine;
    private final APP mApp;
    public UpdateDataPresenter(){
        mApp=APP.getInstance();
        this.mDaoSession=mApp.getDaoInstant();
        mEngine=mApp.getEngine();
    }


    public void startUpdateData(){


        new Thread(new Runnable() {
            @Override
            public void run() {


            }
        }).start();



    }


    private void updateCarBrand(){

    }


}
