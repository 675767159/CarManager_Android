package com.qcwp.carmanager.ui;

import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.HomeItemView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;
import com.qiantao.coordinatormenu.CoordinatorMenu;


import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.carInfo)
    HomeItemView carInfo;
    @BindView(R.id.totalTravel)
    HomeItemView totalTravel;
    @BindView(R.id.singleTravel)
    HomeItemView singleTravel;
    @BindView(R.id.professionalTest)
    HomeItemView professionalTest;
    @BindView(R.id.driveHabit)
    HomeItemView driveHabit;
    @BindView(R.id.menu)
    CoordinatorMenu mCoordinatorMenu;

    private CarInfoModel    carInfoModel;
    private CarVinStatisticModel carVinStatisticModel;
    private SingleCarVinStatisticModel singleCarVinStatisticModel;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();
        if (carInfoModel!=null) {
            carVinStatisticModel = mApp.getDaoInstant().queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).build().unique();
            singleCarVinStatisticModel = mApp.getDaoInstant().queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).build().unique();
        }
        updateUI();

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

            case R.id.button_home:{
                mCoordinatorMenu.closeMenu();
            }
            break;
            case R.id.button_driving:
                break;
            case R.id.button_professional_test:
                break;
            case R.id.button_travel:
                break;
            case R.id.button_car_info:
            {
                Bundle bundle=new Bundle();
                bundle.putString(KeyEnum.vinCode,"12345678901234567");
                bundle.putSerializable(KeyEnum.typeKey,CarEditActivity.Type.Edit);
                readyGo(CarEditActivity.class,bundle);
            }
                break;
            case R.id.button_set:
                break;
        }
    }



    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        switch (messageEvent.getType()){
            case CarDataUpdate:
            case  CarBlindSuccess:
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
            carInfo.setValue3(carInfoModel.getCarNumber());

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
        if (OBDClient.getDefaultClien().getConnectStatus()== OBDConnectStateEnum.connectTypeHaveBinded){
            singleTravel.setTitle1("本次行驶里程");
            singleTravel.setTitle2("本次行驶时间");
            singleTravel.setTitle3("本次行驶用油");
        }else {

            singleTravel.setTitle1("上次行驶里程");
            singleTravel.setTitle2("上次行驶时间");
            singleTravel.setTitle3("上次行驶用油");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Print.d("onRequestPermissionsResult","--------"+requestCode);
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  Print.d("onRequestPermissionsResult","--------");
                }
                return;
            }
        }
    }
}
