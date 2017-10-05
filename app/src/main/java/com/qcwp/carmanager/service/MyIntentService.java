package com.qcwp.carmanager.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.engine.RequestModel;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.TravelDataModelDao;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.retrofitModel.TokenModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.mvp.contact.UploadDataContract;
import com.qcwp.carmanager.mvp.present.UploadDataPresenter;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 * 连续两次启动IntentService，会发现应用程序不会阻塞，而且最重的是第二次的请求会再第一个请求结束之后运行(这个证实了IntentService采用单独的线程每次只从队列中拿出一个请求进行处理)
 */
public class MyIntentService extends IntentService implements UploadDataContract.View{

    private  final String ACTION_UploadDriveData="com.qyh.quyonghengapplication.service.action.UploadDriveData";
    private final String TAG="MyIntentService";
    private Engine mEngine;
    private APP mApp;
    private Context context;
    private DaoSession mDaoSession;
    private UploadDataPresenter uploadDataPresenter;
    public MyIntentService() {
        super("MyIntentService");
        mApp=APP.getInstance();
        mEngine= mApp.getEngine();
        mDaoSession=mApp.getDaoInstant();
        uploadDataPresenter=new UploadDataPresenter(this,mDaoSession);

    }


    public static void startService(Context context) {

        MyIntentService myIntentService=new MyIntentService();
        myIntentService.startActionUploadDriveData(context);

    }



    private  void startActionUploadDriveData(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_UploadDriveData);
        context.startService(intent);
    }




    @Override
    protected void onHandleIntent(Intent intent) {

        Print.d(TAG,intent.toString());
        if (intent != null) {
            final String action = intent.getAction();
            Print.d("onHandleIntent",action);
            if (ACTION_UploadDriveData.equals(action)) {

                uploadDataPresenter.uploadDriveData();
            }
        }
    }



    @Override
    public void uploadDriveDataComplete() {

        uploadDataPresenter.uploadMapPointOfDriveData();
        Print.d("uploadDriveData","uploadDriveDataComplete");
    }

    @Override
    public void uploadMapPointOfDriveDataComplete() {

        uploadDataPresenter.uploadDrivingCustom();
    }

    @Override
    public void uploadDrivingCustomComplete() {

        uploadDataPresenter.uploadPhysicalExamination();
    }

    @Override
    public void uploadPhysicalExaminationComplete() {

    }
}
