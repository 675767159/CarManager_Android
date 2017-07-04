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
public class MyIntentService extends IntentService {

    private  final String ACTION_UploadDriveData="com.qyh.quyonghengapplication.service.action.UploadDriveData";
    private final String TAG="MyIntentService";
    private Engine mEngine;
    private APP mApp;
    private Context context;
    private DaoSession mDaoSession;
    private int count=0;
    private  List<TravelSummaryModel> travelSummaryModels;
    private long onlyFlag;
    private String filePath;
    public MyIntentService() {
        super("MyIntentService");
        mApp=APP.getInstance();
        mEngine= mApp.getEngine();
        mDaoSession=mApp.getDaoInstant();

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
                travelSummaryModels =mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.UploadFlag.eq(UploadStatusEnum.NotUpload.getValue())).list();
                handleActionUploadDriveData();
            }
        }
    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUploadDriveData() {



        Print.d("MyIntentServicee","travelSummaryModels="+travelSummaryModels.size());

        if (travelSummaryModels!=null&&count<travelSummaryModels.size()){
            TravelSummaryModel travelSummaryModel=travelSummaryModels.get(count);
            onlyFlag=travelSummaryModel.getOnlyFlag();
            String vinCode=travelSummaryModel.getVinCode();
            String userName=travelSummaryModel.getUserName();
            CarInfoModel carInfoModel =mDaoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vinCode)).unique();
            String  uploadStr =String.format("#u=%s",userName);
            Print.d(TAG,uploadStr);

            uploadStr=uploadStr+String.format(Locale.getDefault(),"&0=%s&1=%s&2=%f&3=%s&4=%d&5=%s&6=%s&7=%d&8=%s&9=%d&10=%s&11=%s&12=%s&13=%s&14=%s&15=%s&16=%s&17=%d&18=%d&19=%s\n",
                    carInfoModel.getVinCode(),carInfoModel.getCarNumber(),carInfoModel.getTotalMileage(),carInfoModel.getSpValue(),//0-3
                    carInfoModel.getIsTestSteer(),carInfoModel.getOwnerName(),carInfoModel.getIsoBuyDate(),carInfoModel.getCarTypeId(),carInfoModel.getManufacturer(),//4-8
                    carInfoModel.getCarSeriesId(),carInfoModel.getProductiveYear(),null,null,carInfoModel.getMaintenanceInterval(),//9-13
                    carInfoModel.getCarColor(),carInfoModel.getFuelOilType(),null,carInfoModel.getMid(),0,null);

            uploadStr=uploadStr+travelSummaryModel.getSummryData();


            List<TravelDataModel>travelDataModels = mDaoSession.queryBuilder(TravelDataModel.class).where(TravelDataModelDao.Properties.OnlyFlag.eq(onlyFlag)).list();



            for (TravelDataModel travelDataModel:travelDataModels){
                uploadStr=uploadStr+travelDataModel.getTravelData();
            }
            uploadStr=uploadStr+"/*over*/";

            final String fileName =String.format(Locale.getDefault(),"%s_%d.txt",vinCode,TimeUtils.getNowMills());
            Print.d(TAG,fileName);

            filePath=mApp.getMyFileFolder(fileName);
            FileIOUtils.writeFileFromString(filePath,uploadStr);

            File file=new File(filePath);

            Print.d(TAG,file.getName()+"----");
            // 创建 RequestBody，用于封装构建RequestBody
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

             MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file",fileName, requestFile);


            this.getToken(body);




        }



    }

    private void getToken(final MultipartBody.Part file){
Print.d(TAG,"getUserName=="+UserData.getInstance().getUserName());
Print.d(TAG,"getPassword=="+UserData.getInstance().getPassword());

        mEngine.getToken(UserData.getInstance().getUserName(),UserData.getInstance().getPassword(),"doTravelDataUploadFile_appUpload").enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {

                if (response.isSuccessful()){
                    TokenModel tokenModel=response.body();
                    Print.d(TAG,"token=="+tokenModel.getStatus());
                    Print.d(TAG,"token=="+tokenModel.getMsg());
                    Print.d(TAG,"token=="+tokenModel.getToken());
                    uploadDriveData(file,tokenModel.getToken());
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {

            }
        });
    }

    private void uploadDriveData(MultipartBody.Part file,String token){


        mEngine.uploadDriveData(file,token).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel=response.body();
                Print.d(TAG,allCarModel.getMsg());
                Print.d(TAG,allCarModel.getStatus()+"====");
                if (allCarModel.getStatus()==1||allCarModel.getMsg().equals("该小结没有合法的数据!")||allCarModel.getMsg().equals("该文件已经解析过了,本次不予处理!")){

                    TravelSummaryModel travelSummaryModel=mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.OnlyFlag.eq(onlyFlag)).unique();
                    travelSummaryModel.setUploadFlag(UploadStatusEnum.HadUpload);
                    mDaoSession.update(travelSummaryModel);

                   List<TravelDataModel>  travelDataModels=mDaoSession.queryBuilder(TravelDataModel.class).where(TravelDataModelDao.Properties.OnlyFlag.eq(onlyFlag)).list();
                    for (TravelDataModel travelDataModel:travelDataModels) {
                        mDaoSession.delete(travelDataModel);
                    }


                }
                count++;
                FileUtils.deleteFile(filePath);
                MyIntentService.this.handleActionUploadDriveData();

            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {

                count++;
                FileUtils.deleteFile(filePath);
                MyIntentService.this.handleActionUploadDriveData();
            }
        });
    }

}
