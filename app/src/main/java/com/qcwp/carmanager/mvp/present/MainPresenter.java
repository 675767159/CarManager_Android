package com.qcwp.carmanager.mvp.present;

import android.app.Activity;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.engine.ApiException;
import com.qcwp.carmanager.engine.MyCallBack;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.PhoneAuthModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.mvp.contact.MainContract;
import com.qcwp.carmanager.mvp.contact.RegisterContract;
import com.qcwp.carmanager.ui.MainActivity;
import com.qcwp.carmanager.utils.Print;

import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private  final MainContract.View view;
    private  final String TAG="MainPresenter";
    private final DaoSession mDaoSession;
    public MainPresenter(MainContract.View view, DaoSession mDaoSession){
        this.view=view;
        this.mDaoSession=mDaoSession;
    }

    @Override
    public void getMyAllCarInfo() {

        Print.d(TAG,"------------"+UserData.getInstance().getUserId());
        mEngine.getMyAllCarInfo(UserData.getInstance().getUserId()).enqueue(new MyCallBack<AllCarModel>() {
            @Override
            public void onCompleted() {
                if (!view.isActive()) {
                    return;
                }
                completeOnceRequest();
            }

            @Override
            public void onSuccess(Call<AllCarModel> call, AllCarModel allCarInfoModel) {

                Print.d(TAG,"------------");
                if (allCarInfoModel.getStatus()==1) {
                    List<CarInfoModel> allCarInfoModels = allCarInfoModel.getVins();
                    for (CarInfoModel carInfoModel : allCarInfoModels) {
                        Print.d(TAG, carInfoModel.toString());

                        CarInfoModel sqCarInfoModel = mDaoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).unique();
                        if (sqCarInfoModel != null) {
                            carInfoModel.setId(sqCarInfoModel.getId());
                            carInfoModel.setTimestamp(sqCarInfoModel.getTimestamp());
                        } else {
                            carInfoModel.setTimestamp(TimeUtils.getNowMills());
                        }
                        carInfoModel.setNeedUpload(UploadStatusEnum.HadUpload);
                        carInfoModel.setCarSeries(carInfoModel.getCarType().getCarSeriesModel().getSeriesName());
                        carInfoModel.setBrand(carInfoModel.getCarType().getCarSeriesModel().getCommonBrandModel().getCarBrand().getBrandName());
                        carInfoModel.setCommonBrandName(carInfoModel.getCarType().getCarSeriesModel().getCommonBrandModel().getCommonBrandName());

                        mDaoSession.insertOrReplace(carInfoModel);

                    }


                }

               CarInfoModel  carInfoModel =MainPresenter.this.getLatestCarInfo();

                if (carInfoModel==null){
                    view.showTip("您还未绑定任何车辆");
                    return;
                }

                view.onSuccessGetMyAllCarInfo(carInfoModel);

            }

            @Override
            public void onFailed(Call<AllCarModel> call,  ApiException throwable) {
                view.showTip(throwable.getDisplayMessage());
            }
        });


    }

    @Override
    public CarInfoModel getLatestCarInfo() {
       CarInfoModel carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();
        return carInfoModel;
    }

    @Override
    public CarInfoModel getCarInfoByVinCode(String vinCode) {

        CarInfoModel carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vinCode)).build().unique();
        return carInfoModel;
    }

    @Override
    public CarVinStatisticModel getCarVinStatistic(String vinCode) {

        CarVinStatisticModel  carVinStatisticModel = mApp.getDaoInstant().queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).build().unique();

        if (carVinStatisticModel==null){
            return new CarVinStatisticModel();
        }

        return carVinStatisticModel;
    }

    @Override
    public SingleCarVinStatisticModel getSingleCarVinStatistic(String vinCode) {

        SingleCarVinStatisticModel singleCarVinStatisticModel=mApp.getDaoInstant().queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).build().unique();

        if (singleCarVinStatisticModel==null){
            return new SingleCarVinStatisticModel();
        }
        return singleCarVinStatisticModel;
    }

    @Override
    int getRequestCount() {
        return 0;
    }

    @Override
    void completeAllRequest() {

    }
}
