package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDetailActivity extends BaseActivity {


    @BindView(R.id.carSeries)
    TitleContentView carSeries;
    @BindView(R.id.carNumber)
    TitleContentView carNumber;
    @BindView(R.id.carVinCode)
    TitleContentView carVinCode;
    @BindView(R.id.carTotalMileage)
    TitleContentView carTotalMileage;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        CarInfoModel carInfoModel = CarInfoModel.getLatestCarInfo();
        if (carInfoModel != null) {
            carSeries.setContentTextViewText(carInfoModel.getCarSeries());
            carNumber.setContentTextViewText(carInfoModel.getCarNumber());
            carVinCode.setContentTextViewText(carInfoModel.getVinCode());
            carTotalMileage.setContentTextViewText(String.format(Locale.getDefault(),"%.1fkm",carInfoModel.getTotalMileage()));
        }
    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.physicalExamination:
                readyGo(CarExaminationActivity.class);
                break;
        }
    }
}
