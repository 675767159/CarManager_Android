package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;

import com.minstone.testsqldelight.HockeyPlayerModel;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.HomeItemView;
import com.qcwp.carmanager.model.HockeyPlayer;
import com.qcwp.carmanager.utils.Print;
import com.qiantao.coordinatormenu.CoordinatorMenu;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        Print.d(TAG,stringFromJNI());

        List<HockeyPlayer> result = new ArrayList<>();
        HockeyPlayerModel.Creator<HockeyPlayer> creator=HockeyPlayer.FACTORY.creator;


    }

    public native String stringFromJNI();
    static {
        System.loadLibrary("native-lib");
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
                break;
            case R.id.button_set:
                break;
        }
    }



}
