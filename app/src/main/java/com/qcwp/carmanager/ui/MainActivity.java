package com.qcwp.carmanager.ui;

import android.os.Bundle;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.HomeItemView;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }


}
