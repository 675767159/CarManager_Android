package com.qcwp.carmanager.mvp.present;

import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import com.qcwp.carmanager.greendao.gen.CarSeriesModelDao;
import com.qcwp.carmanager.greendao.gen.CarTypeModelDao;
import com.qcwp.carmanager.greendao.gen.CommonBrandModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
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
                getAllCarBrand();
                getAllCommonCarBrand();
                getAllCarSeries();
                getAllCarType();

            }
        }).start();



    }


    private void getAllCarBrand() {
        long ID = 0;
        CarBrandModel carBrandModel = mDaoSession.queryBuilder(CarBrandModel.class).orderDesc(CarBrandModelDao.Properties.Id).limit(1).unique();
        if (carBrandModel != null) {
            ID = carBrandModel.getId();
        }

        mEngine.getAllCarBrand(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel = response.body();
                if (allCarModel.getStatus() == 1) {
                    List<CarBrandModel> carBrandModels = allCarModel.getBrands();
                    mDaoSession.getCarBrandModelDao().insertInTx(carBrandModels);

                }

            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {

            }
        });
    }


    private void getAllCommonCarBrand() {

        long ID = 0;
        CommonBrandModel commonBrandModel = mDaoSession.queryBuilder(CommonBrandModel.class).orderDesc(CommonBrandModelDao.Properties.Id).limit(1).unique();
        if (commonBrandModel != null) {
            ID = commonBrandModel.getId();
        }

        mEngine.getAllCommonCarBrand(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel = response.body();
                if (allCarModel.getStatus() == 1) {
                    List<CommonBrandModel> commonBrands = allCarModel.getCommonBrands();
                    mDaoSession.getCommonBrandModelDao().insertInTx(commonBrands);


                }

            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {

            }
        });
    }

    private void getAllCarSeries() {

        long ID = 0;
        CarSeriesModel carSeriesModel = mDaoSession.queryBuilder(CarSeriesModel.class).orderDesc(CarSeriesModelDao.Properties.Id).limit(1).unique();
        if (carSeriesModel != null) {
            ID = carSeriesModel.getId();
        }


        mEngine.getAllCarSeries(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel = response.body();
                if (allCarModel.getStatus() == 1) {
                    List<CarSeriesModel> carSerieses = allCarModel.getCarSerieses();
                    mDaoSession.getCarSeriesModelDao().insertInTx(carSerieses);

                }

            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {

            }
        });
    }

    private void getAllCarType() {

        long ID = 0;
        CarTypeModel carTypeModel = mDaoSession.queryBuilder(CarTypeModel.class).orderDesc(CarTypeModelDao.Properties.Id).limit(1).unique();
        if (carTypeModel != null) {
            ID = carTypeModel.getId();
        }

        mEngine.getAllCarType(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel = response.body();
                if (allCarModel.getStatus() == 1) {
                    List<CarTypeModel> carTypes = allCarModel.getCarTypes();
                    mDaoSession.getCarTypeModelDao().insertInTx(carTypes);

                }

            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {

            }
        });
    }


}
