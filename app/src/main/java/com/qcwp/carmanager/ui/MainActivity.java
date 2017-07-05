package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.HomeItemView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;
import com.qiantao.coordinatormenu.CoordinatorMenu;


import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {


    @BindView(R.id.home_carInfo)
    HomeItemView carInfo;
    @BindView(R.id.home_totalTravel)
    HomeItemView totalTravel;
    @BindView(R.id.home_singleTravel)
    HomeItemView singleTravel;
    @BindView(R.id.home_professionalTest)
    HomeItemView professionalTest;
    @BindView(R.id.home_driveHabit)
    HomeItemView driveHabit;
    @BindView(R.id.menu)
    CoordinatorMenu mCoordinatorMenu;
    @BindView(R.id.userName)
    TextView userName;

    private CarInfoModel    carInfoModel;
    private CarVinStatisticModel carVinStatisticModel;
    private SingleCarVinStatisticModel singleCarVinStatisticModel;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        userName.setText(UserData.getInstance().getUserName());
        carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();
        if (carInfoModel!=null) {
            carVinStatisticModel = mApp.getDaoInstant().queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).build().unique();
            singleCarVinStatisticModel = mApp.getDaoInstant().queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).build().unique();
        }
        this.updateUI();
        this.getMyAllCarInfo();
        Print.d(TAG,"UserId="+UserData.getInstance().getUserId());
        Print.d(TAG,"UserName="+UserData.getInstance().getUserName());
    }



    private void getMyAllCarInfo(){
        Print.d(TAG,"=====");
        mEngine.getMyAllCarInfo(UserData.getInstance().getUserId()).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {
                AllCarModel allCarInfoModel=response.body();
                Print.d(TAG,"------------");
                if (allCarInfoModel.getStatus()==1){
                    List<CarInfoModel> allCarInfoModels=allCarInfoModel.getVins();
                    for (CarInfoModel carInfoModel:allCarInfoModels){
                        Print.d(TAG,carInfoModel.toString());

                        CarInfoModel sqCarInfoModel=mDaoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).unique();
                        if (sqCarInfoModel!=null){
                            carInfoModel.setId(sqCarInfoModel.getId());
                            carInfoModel.setTimestamp(sqCarInfoModel.getTimestamp());
                        }else {
                            carInfoModel.setTimestamp(TimeUtils.getNowMills());
                        }
                        carInfoModel.setNeedUpload(UploadStatusEnum.HadUpload);
                        carInfoModel.setCarSeries(carInfoModel.getCarType().getCarSeriesModel().getSeriesName());
                        carInfoModel.setBrand(carInfoModel.getCarType().getCarSeriesModel().getCommonBrandModel().getCarBrand().getBrandName());
                        carInfoModel.setCommonBrandName(carInfoModel.getCarType().getCarSeriesModel().getCommonBrandModel().getCommonBrandName());

                        mDaoSession.insertOrReplace(carInfoModel);
                    }
                    Print.d(TAG,allCarInfoModels.size()+"-----");
                    carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();

                    MainActivity.this.updateUI();

                }
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                Print.d(TAG,"++++++++");
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_menu:
            {
                if (mCoordinatorMenu.isOpened()) {
                    mCoordinatorMenu.closeMenu();
                } else {
                    mCoordinatorMenu.openMenu();
                }
            }
                break;

            case R.id.menu_home:{
                mCoordinatorMenu.closeMenu();
            }
            break;
            case R.id.menu_driving:
                break;
            case R.id.menu_professional_test:
                break;
            case R.id.menu_travel:
                break;
            case R.id.menu_car_info:
            {
                Bundle bundle=new Bundle();
                if (carInfoModel!=null) {
                    bundle.putString(KeyEnum.vinCode, carInfoModel.getVinCode());
                }
                bundle.putSerializable(KeyEnum.typeKey,CarEditActivity.Type.Edit);
                readyGo(CarEditActivity.class,bundle);
            }
                break;
            case R.id.menu_set:
                readyGo(SetActivity.class);
                break;
            case R.id.home_carInfo:
               readyGo(CarListActivity.class);
                break;
        }
    }



    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        switch (messageEvent.getType()){
            case CarDataUpdate:
            case  CarBlindSuccess:
            case  CarSelected:
                carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(messageEvent.getMessage())).build().unique();
                carVinStatisticModel=mApp.getDaoInstant().queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(messageEvent.getMessage())).build().unique();
singleCarVinStatisticModel=mApp.getDaoInstant().queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(messageEvent.getMessage())).build().unique();

                updateUI();
                break;
        }

    }


    private void updateUI(){
        if (carInfoModel!=null){
            carInfo.setValue1(carInfoModel.getCarSeries());
            carInfo.setValue2(carInfoModel.getCarNumber());


        }

        if (carVinStatisticModel!=null){
            totalTravel.setValue1(String.format("%.1fkm",carVinStatisticModel.getDistCount()));
            totalTravel.setValue2(CommonUtils.longTimeToStr(carVinStatisticModel.getTimeCount()));
            totalTravel.setValue3(String.format("%.1f升",carVinStatisticModel.getFuelCount()));


            professionalTest.setValue1(String.format("%.2fs",carVinStatisticModel.getBmtestBestScore()));
            professionalTest.setValue2(String.format("%.2fm",carVinStatisticModel.getZdtestBestScore()));
            professionalTest.setValue3(String.format("%.2fs",carVinStatisticModel.getBltestBestScore()));

            driveHabit.setValue1(String.format("%d次",carVinStatisticModel.getAccelerCount()));
            driveHabit.setValue2(String.format("%d次",carVinStatisticModel.getOverspeedCount()));
            driveHabit.setValue3(String.format("%d次",carVinStatisticModel.getDecelerCount()));
        }

        if (singleCarVinStatisticModel!=null){

            singleTravel.setValue1(String.format("%.1fkm",singleCarVinStatisticModel.getDistCount()));
            singleTravel.setValue2(CommonUtils.longTimeToStr(singleCarVinStatisticModel.getTimeCount()));
            singleTravel.setValue3(String.format("%.1f升",singleCarVinStatisticModel.getFuelCount()));

        }
        if ((OBDClient.getDefaultClien().getConnectStatus()== OBDConnectStateEnum.connectTypeHaveBinded||OBDClient.getDefaultClien().getConnectStatus()== OBDConnectStateEnum.connectTypeConnectSuccess)&&carInfoModel.getVinCode().equals(OBDClient.getDefaultClien().getVinCode())){
            singleTravel.setTitle1("本次行驶里程");
            singleTravel.setTitle2("本次行驶时间");
            singleTravel.setTitle3("本次行驶用油");
            carInfo.setValue3("在线");
            carInfo.setValue3Color(R.color.greenColor);
        }else {

            singleTravel.setTitle1("上次行驶里程");
            singleTravel.setTitle2("上次行驶时间");
            singleTravel.setTitle3("上次行驶用油");
            carInfo.setValue3("离线");
            carInfo.setValue3Color(R.color.blackColor);
        }
    }


}
