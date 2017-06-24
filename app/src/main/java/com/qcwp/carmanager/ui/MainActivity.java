package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.HomeItemView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.utils.Print;
import com.qiantao.coordinatormenu.CoordinatorMenu;


import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.carInfo)
    HomeItemView carInfo;
    @BindView(R.id.totalTravel)
    HomeItemView totalTravel;
    @BindView(R.id.lastTravel)
    HomeItemView lastTravel;
    @BindView(R.id.professionalTest)
    HomeItemView professionalTest;
    @BindView(R.id.driveHabit)
    HomeItemView driveHabit;
    @BindView(R.id.menu)
    CoordinatorMenu mCoordinatorMenu;

    private CarInfoModel    carInfoModel;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).limit(1).unique();
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
                readyGo(CarEditActivity.class,bundle);
            }
                break;
            case R.id.button_set:
                break;
        }
    }



    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        Print.d(TAG,"onReceiveMessageEvent");
        if (messageEvent.getType()== MessageEvent.MessageEventType.CarBlindSuccess){
            carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(messageEvent.getMessage())).build().unique();
            updateUI();
        }
    }


    private void updateUI(){
        if (carInfoModel!=null){
            carInfo.setValue1(carInfoModel.getCarSeries());
            carInfo.setValue3(carInfoModel.getCarNumber());

            totalTravel.setValue1(String.valueOf(carInfoModel.getTotalMileage()));

        }

    }
}
