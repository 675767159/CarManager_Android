package com.qcwp.carmanager.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.mvp.contact.UploadDataContract;
import com.qcwp.carmanager.mvp.present.UploadDataPresenter;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 * 连续两次启动IntentService，会发现应用程序不会阻塞，而且最重的是第二次的请求会再第一个请求结束之后运行(这个证实了IntentService采用单独的线程每次只从队列中拿出一个请求进行处理)
 */
public class MyIntentService extends IntentService{

    private  final String ACTION_UploadDriveData="com.qyh.quyonghengapplication.service.action.UploadDriveData";
    private final String TAG="MyIntentService";

    public MyIntentService() {
        super("MyIntentService");



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

                new UploadDataPresenter().startUploadData();
            }
        }
    }





}
