package com.qcwp.carmanager.mvp.present;

import android.database.Cursor;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.blankj.utilcode.util.TimeUtils;
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
import com.qcwp.carmanager.mvp.contact.UploadDataContract;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.blankj.utilcode.util.ThreadPoolUtils.CachedThread;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class UploadDataPresenter extends BasePresenter implements UploadDataContract.Presenter {

    private final UploadDataContract.View view;
    private final DaoSession mDaoSession;
    private int count=0;
    private boolean isUploading=false;
    private ThreadPoolUtils threadPoolUtils;
    public UploadDataPresenter(UploadDataContract.View view, DaoSession daoSession){
        this.view=view;
        this.mDaoSession=daoSession;
        threadPoolUtils= new ThreadPoolUtils(CachedThread, 1);
    }


    @Override
    public void uploadDriveData() {

        List<TravelSummaryModel> travelSummaryModels =mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.UploadFlag.eq(UploadStatusEnum.NotUpload.getValue())).list();

        count=0;
        isUploading=false;
        view.showProgress("正在上传行程...");
        while (count<travelSummaryModels.size()) {
            if (!isUploading) {
                isUploading = true;
                TravelSummaryModel travelSummaryModel = travelSummaryModels.get(count);
                final long onlyFlag = travelSummaryModel.getOnlyFlag();
                String vinCode = travelSummaryModel.getVinCode();
                String userName = travelSummaryModel.getUserName();
                CarInfoModel carInfoModel = mDaoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vinCode)).unique();
                String uploadStr = String.format("#u=%s", userName);

                uploadStr = uploadStr + String.format(Locale.getDefault(), "&0=%s&1=%s&2=%f&3=%s&4=%d&5=%s&6=%s&7=%d&8=%s&9=%d&10=%s&11=%s&12=%s&13=%s&14=%s&15=%s&16=%s&17=%d&18=%d&19=%s\n",
                        carInfoModel.getVinCode(), carInfoModel.getCarNumber(), carInfoModel.getTotalMileage(), carInfoModel.getSpValue(),//0-3
                        carInfoModel.getIsTestSteer(), carInfoModel.getOwnerName(), carInfoModel.getIsoBuyDate(), carInfoModel.getCarTypeId(), carInfoModel.getManufacturer(),//4-8
                        carInfoModel.getCarSeriesId(), carInfoModel.getProductiveYear(), null, null, carInfoModel.getMaintenanceInterval(),//9-13
                        carInfoModel.getCarColor(), carInfoModel.getFuelOilType(), null, carInfoModel.getMid(), 0, null);

                uploadStr = uploadStr + travelSummaryModel.getSummryData();


                List<TravelDataModel> travelDataModels = mDaoSession.queryBuilder(TravelDataModel.class).where(TravelDataModelDao.Properties.OnlyFlag.eq(onlyFlag)).list();


                for (TravelDataModel travelDataModel : travelDataModels) {
                    uploadStr = uploadStr + travelDataModel.getTravelData();
                }
                uploadStr = uploadStr + "/*over*/";

                final String fileName = String.format(Locale.getDefault(), "%s_%d.txt", vinCode, TimeUtils.getNowMills());

                final String filePath = mApp.getMyFileFolder(fileName);
                FileIOUtils.writeFileFromString(filePath, uploadStr);

                File file = new File(filePath);

                // 创建 RequestBody，用于封装构建RequestBody
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", fileName, requestFile);


                mEngine.getToken(UserData.getInstance().getUserName(), UserData.getInstance().getPassword(), "doTravelDataUploadFile_appUpload").enqueue(new Callback<TokenModel>() {
                    @Override
                    public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {

                        if (response.isSuccessful()) {
                            TokenModel tokenModel = response.body();


                            mEngine.uploadDriveData(body, tokenModel.getToken()).enqueue(new Callback<AllCarModel>() {
                                @Override
                                public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                                    AllCarModel allCarModel = response.body();

                                    Print.d("uploadDriveData",allCarModel.getMsg());
                                    Print.d("uploadDriveData","----"+allCarModel.getStatus());

                                    if (allCarModel.getStatus() == 1 || allCarModel.getMsg().equals("该小结没有合法的数据!") || allCarModel.getMsg().equals("该文件已经解析过了,本次不予处理!")) {

                                        TravelSummaryModel travelSummaryModel = mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.OnlyFlag.eq(onlyFlag)).unique();

                                        travelSummaryModel.setUploadFlag(UploadStatusEnum.HadUpload);
                                        mDaoSession.update(travelSummaryModel);

                                        List<TravelDataModel> travelDataModels = mDaoSession.queryBuilder(TravelDataModel.class).where(TravelDataModelDao.Properties.OnlyFlag.eq(onlyFlag)).list();
                                        mDaoSession.getTravelDataModelDao().deleteInTx(travelDataModels);


                                    }
                                    count++;
                                    FileUtils.deleteFile(filePath);
                                    isUploading = false;

                                }

                                @Override
                                public void onFailure(Call<AllCarModel> call, Throwable throwable) {

                                    count++;
                                    FileUtils.deleteFile(filePath);
                                    isUploading = false;
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<TokenModel> call, Throwable throwable) {

                        count++;
                        isUploading = false;
                    }
                });

                while (true){
                    if (!isUploading){
                        break;
                    }
                }
            }

        }

        while (true) {
            if (count == travelSummaryModels.size()) {
                view.uploadDriveDataComplete();
                view.dismissProgress();
                view.showTip("行程上传完毕!");
                completeOnceRequest();
                break;
            }
        }


    }

    @Override
    public void uploadMapPointOfDriveData() {
        count=0;
        isUploading = false;
        String SQL_DISTINCT_ENAME = "SELECT DISTINCT "+LocationModelDao.Properties.CreateTime.columnName+" FROM "+LocationModelDao.TABLENAME+" WHERE "+LocationModelDao.Properties.UploadFlag.columnName+" = "+UploadStatusEnum.NotUpload.getValue();
        List<String> locationModels= CommonUtils.listEName(mDaoSession,SQL_DISTINCT_ENAME);

        view.showProgress("正在上传轨迹...");
        while (count<locationModels.size()) {
            if (!isUploading) {
                isUploading = true;
                final   String createDate=locationModels.get(count);
                final List<LocationModel>locationModelList=mDaoSession.queryBuilder(LocationModel.class).where(LocationModelDao.Properties.CreateTime.eq(createDate)).orderAsc(LocationModelDao.Properties.CreateTime).limit(1000).list();
                        int i=0;
                        Locale locale=Locale.getDefault();
                        String  vinCode=null;
                        Map<String,Object> map=new HashMap<>();
                        for (LocationModel locationMode :locationModelList){

                            map.put(String.format(locale,"mapPoints[%d].pointx",i),locationMode.getLongitude());
                            map.put(String.format(locale,"mapPoints[%d].pointy",i),locationMode.getLatitude());
                            map.put(String.format(locale,"mapPoints[%d].createDate",i),TimeUtils.string2Millis(locationMode.getCurrentTime()));
                            vinCode=locationMode.getVinCode();
                            i++;

                        }

                        map.put("vinCode",vinCode);
                        map.put("summarizeStartDate",createDate);

                        Print.d("uploadMapPointOfDrive","--"+map.toString());
                  Print.d("uploadMapPointOfDriveuploadMapPointOfDrive","111111111");


                        mEngine.uploadMapPointOfDrive(map).enqueue(new Callback<AllCarModel>() {
                            @Override
                            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {
                                AllCarModel model=response.body();

                                Print.d("uploadMapPointOfDriveuploadMapPointOfDrive","2222222");

                                Print.d("uploadMapPointOfDrive","--"+model.getMsg());
                                if (model!=null&&model.getStatus()==1)
                                {
                                    Print.d("uploadMapPointOfDrive","--"+model.getStatus());
                                    mDaoSession.getLocationModelDao().deleteInTx(locationModelList);



                                    int tempCount=mDaoSession.queryBuilder(LocationModel.class).where(LocationModelDao.Properties.CreateTime.eq(createDate)).orderAsc(LocationModelDao.Properties.CreateTime).limit(1000).list().size();
                                    if (tempCount==0) {
                                        count++;
                                        isUploading = false;

                                    }

                                }else{

                                    count++;
                                    isUploading = false;
                                }
                            }

                            @Override
                            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                                Print.d("uploadMapPointOfDriveuploadMapPointOfDrive","2222222");
                                count++;
                                isUploading = false;
                            }
                        });

                while (true){
                    if (!isUploading){
                        break;
                    }
                }
                Print.d("uploadMapPointOfDriveuploadMapPointOfDrive","33333333333");

            }



        }



        while (true) {
            if (count == locationModels.size()) {
                view.uploadMapPointOfDriveDataComplete();
                completeOnceRequest();
                view.dismissProgress();
                view.showTip("轨迹上传完毕!");
                break;
            }
        }





    }

    @Override
    public void uploadDrivingCustom() {
        isUploading=false;
        count=0;
        List<DrivingCustomModel> drivingCustomModels= this.listDriveCustom();

        view.showProgress("正在上传驾驶习惯...");
        while (count<drivingCustomModels.size()) {
            if (!isUploading) {
                isUploading = true;
                DrivingCustomModel drivingCustomModel=drivingCustomModels.get(count);
                final String startDate=drivingCustomModel.getStartDate();
                int accelerate=UploadDataPresenter.this.getDrivingCustomCount(startDate,DrivingCustomEnum.Acceleration);
                int deceleration=UploadDataPresenter.this.getDrivingCustomCount(startDate,DrivingCustomEnum.Deceleration);
                int overdrive=UploadDataPresenter.this.getDrivingCustomCount(startDate,DrivingCustomEnum.Overdrive);

                mEngine.uploadDriveCustom(drivingCustomModel.getVinCode(),startDate,accelerate,deceleration,overdrive).enqueue(new Callback<AllCarModel>() {
                    @Override
                    public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {
                        AllCarModel model=response.body();

                        Print.d("uploadDrivingCustom",model.getMsg()+"-----");
                        if (model!=null&&model.getStatus()==1) {
                            Print.d("uploadDrivingCustom",model.getStatus()+"-----");
                            List<DrivingCustomModel>list=mDaoSession.queryBuilder(DrivingCustomModel.class).where(DrivingCustomModelDao.Properties.StartDate.eq(startDate)).list();
                            mDaoSession.getDrivingCustomModelDao().deleteInTx(list);
                        }


                        count++;
                        isUploading=false;
                    }

                    @Override
                    public void onFailure(Call<AllCarModel> call, Throwable throwable) {

                        count++;
                        isUploading=false;
                    }
                });

                while (true){
                    if (!isUploading){
                        break;
                    }
                }


            }
        }





        while (true) {
            if (count == drivingCustomModels.size()) {
                view.uploadDrivingCustomComplete();
                completeOnceRequest();
                view.dismissProgress();
                view.showTip("驾驶习惯上传完毕!");
                Print.d("uploadDrivingCustom", "完成");
                break;

            }
        }




    }






    @Override
    public void uploadPhysicalExamination() {

        count=0;
        isUploading=false;

        List<CarCheckModel> carCheckModels= mDaoSession.queryBuilder(CarCheckModel.class).where(CarCheckModelDao.Properties.UploadFlag.eq(UploadStatusEnum.NotUpload.getValue())).list();

        view.showProgress("正在上传体检...");
        while (count<carCheckModels.size()) {
            if (!isUploading) {
                isUploading = true;
                final CarCheckModel carCheckModel=carCheckModels.get(count);

                Map<String,String> faultCodeMap=new HashMap<>();
                String faultCodeStr=carCheckModel.getCarBodyDTCS()+carCheckModel.getChassisDTCS()+carCheckModel.getNetworkDTCS()+carCheckModel.getPowertrainDTCS();
                String[] faultCodes=faultCodeStr.split(",");
                int i=0;
                for (String faultCode:faultCodes) {
                        faultCodeMap.put(String.format(Locale.getDefault(), "faultCodes[%d].code", i), faultCode);
                        i++;
                }

                String engineConditionStr=carCheckModel.getEngineConditions();
                String[] engineConditions=engineConditionStr.split(",");



                Print.d("uploadPhysicalExamination",carCheckModel.getVinCode());

                mEngine.uploadPhysicalExamination(carCheckModel.getVinCode(),carCheckModel.getCreateDate(),carCheckModel.getScore(),engineConditions[0],engineConditions[1],engineConditions[2],engineConditions[3],engineConditions[4],engineConditions[5],faultCodeMap).enqueue(new Callback<AllCarModel>() {
                    @Override
                    public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                        AllCarModel carModel=response.body();
                        Print.d("uploadPhysicalExamination","msg==="+carModel.getMsg());
                        Print.d("uploadPhysicalExamination","status==="+carModel.getStatus());
                        if (carModel.getStatus()==1){

                            carCheckModel.setUploadFlag(UploadStatusEnum.HadUpload);
                            mDaoSession.update(carCheckModel);
                        }
                        count++;
                        isUploading = false;
                    }

                    @Override
                    public void onFailure(Call<AllCarModel> call, Throwable throwable) {

                        Print.d("uploadPhysicalExamination","throwable==="+throwable.getLocalizedMessage());
                        count++;
                        isUploading = false;

                    }
                });

                while (true){
                    if (!isUploading){
                        break;
                    }
                }

            }
        }


        if (count==carCheckModels.size()){
            view.uploadPhysicalExaminationComplete();
            view.dismissProgress();
            view.showTip("体检上传完毕!");
            completeOnceRequest();
            Print.d("uploadDrivingCustom","完成");
        }

    }



    @Override
    int getRequestCount() {
        return 1;
    }

    @Override
    void completeAllRequest() {

    }


    private  List<DrivingCustomModel> listDriveCustom() {

        String SQL_DISTINCT_ENAME = "SELECT * FROM "+DrivingCustomModelDao.TABLENAME+" group by "+DrivingCustomModelDao.Properties.StartDate.columnName;

        List<DrivingCustomModel> result = new ArrayList<>();
        Cursor c = mDaoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try{
            if (c.moveToFirst()) {
                do {

                    DrivingCustomModel drivingCustomModel=new DrivingCustomModel();

                    drivingCustomModel.setVinCode(c.getString(c.getColumnIndex(DrivingCustomModelDao.Properties.VinCode.columnName)));
                    drivingCustomModel.setStartDate(c.getString(c.getColumnIndex(DrivingCustomModelDao.Properties.StartDate.columnName)));
                    drivingCustomModel.setType(DrivingCustomEnum.fromInteger(c.getInt(c.getColumnIndex(DrivingCustomModelDao.Properties.Type.columnName))));
                    drivingCustomModel.setCreateDate(c.getString(c.getColumnIndex(DrivingCustomModelDao.Properties.CreateDate.columnName)));
                    drivingCustomModel.setPointx(c.getDouble(c.getColumnIndex(DrivingCustomModelDao.Properties.Pointx.columnName)));
                    drivingCustomModel.setPointy(c.getDouble(c.getColumnIndex(DrivingCustomModelDao.Properties.Pointy.columnName)));
                    result.add(drivingCustomModel);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }


    private  int getDrivingCustomCount(String startDate,DrivingCustomEnum type) {

        String SQL_DISTINCT_ENAME = "select count(*) from "+DrivingCustomModelDao.TABLENAME+" where "+ DrivingCustomModelDao.Properties.StartDate.columnName+" = ' "+startDate+" ' and type= "+ type.getValue();

        int count=0;
        Cursor c = mDaoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try{
            if (c.moveToFirst()) {
                do {

                    count=c.getInt(0);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return count;
    }
}
