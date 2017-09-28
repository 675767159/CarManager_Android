package com.qcwp.carmanager.mvp.present;

import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.LocationModelDao;
import com.qcwp.carmanager.greendao.gen.TravelDataModelDao;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.retrofitModel.TokenModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.LocationModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.mvp.contact.UploadDataContract;
import com.qcwp.carmanager.service.MyIntentService;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import java.io.File;
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
    public UploadDataPresenter(UploadDataContract.View view, DaoSession daoSession){
        this.view=view;
        this.mDaoSession=daoSession;
    }


    @Override
    public void uploadDriveData() {

        List<TravelSummaryModel> travelSummaryModels =mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.UploadFlag.eq(UploadStatusEnum.NotUpload.getValue())).list();

        count=0;
        isUploading=false;
        while (count<travelSummaryModels.size()) {
            if (!isUploading&&count<travelSummaryModels.size()) {
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
                                        for (TravelDataModel travelDataModel : travelDataModels) {
                                            mDaoSession.delete(travelDataModel);
                                        }


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
            }

        }

        if (count==travelSummaryModels.size()){
            view.uploadDriveDataComplete();
            completeOnceRequest();
        }


    }

    @Override
    public void uploadMapPointOfDriveData() {
        count=0;
        isUploading=false;
        String SQL_DISTINCT_ENAME = "SELECT DISTINCT "+LocationModelDao.Properties.CreateTime.columnName+" FROM "+LocationModelDao.TABLENAME+" WHERE "+LocationModelDao.Properties.UploadFlag.columnName+" = "+UploadStatusEnum.NotUpload.getValue();
        List<String> locationModels= CommonUtils.listEName(mDaoSession,SQL_DISTINCT_ENAME);


        while (count<locationModels.size()) {
            if (!isUploading && count < locationModels.size()) {
                isUploading = true;
               final   String createDate=locationModels.get(count);

                final List<LocationModel>locationModelList=mDaoSession.queryBuilder(LocationModel.class).where(LocationModelDao.Properties.CreateTime.eq(createDate)).orderAsc(LocationModelDao.Properties.CreateTime).limit(1000).list();




                int i=0;
                Locale locale=Locale.getDefault();
                String  vinCode=null;
                Map<String,Object> map=new HashMap<String, Object>();
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

                mEngine.uploadMapPointOfDrive(map).enqueue(new Callback<AllCarModel>() {
                    @Override
                    public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {
                        AllCarModel model=response.body();
                        Print.d("uploadMapPointOfDrive","--"+response.code());
                        Print.d("uploadMapPointOfDrive","--"+model.getMsg());
                        if (model!=null&&model.getStatus()==1)
                        {
                            Print.d("uploadMapPointOfDrive","--"+model.getStatus());
                            for (LocationModel locationModel:locationModelList) {
                                mDaoSession.delete(locationModel);
                            }



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
                        count++;
                        isUploading = false;
                    }
                });



            }
        }

        if (count==locationModels.size()){
            view.uploadMapPointOfDriveDataComplete();
        }





    }

    @Override
    int getRequestCount() {
        return 1;
    }

    @Override
    void completeAllRequest() {

    }
}
