package com.qcwp.carmanager.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.ProfessionalTestDisplayView;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.greendao.gen.NoiseTestModelDao;
import com.qcwp.carmanager.greendao.gen.TestSummaryModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.NoiseTestModel;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;
import com.qcwp.carmanager.utils.MyActivityManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfessionTestActivity extends BaseActivity implements ProfessionalTestDisplayView.OnDisplayViewClickListener {


    @BindView(R.id.carSeries)
    TitleContentView carSeries;
    @BindView(R.id.carNumber)
    TitleContentView carNumber;
    @BindView(R.id.carTotalMileage)
    TitleContentView carTotalMileage;
    @BindView(R.id.hectometerAccelerate)
    ProfessionalTestDisplayView hectometerAccelerate;
    @BindView(R.id.kilometersAccelerate)
    ProfessionalTestDisplayView kilometersAccelerate;
    @BindView(R.id.kilometersBrake)
    ProfessionalTestDisplayView kilometersBrake;
    @BindView(R.id.noiseValue)
    TitleContentView noiseValue;
    @BindView(R.id.vehicleSpeed)
    TitleContentView vehicleSpeed;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_profession_test;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        CarInfoModel carInfoModel = CarInfoModel.getCurrentCarInfo();
        Locale locale = Locale.getDefault();
        if (carInfoModel != null) {
            carNumber.setContentTextViewText(carInfoModel.getCarNumber());
            carSeries.setContentTextViewText(carInfoModel.getCarSeries());
            carTotalMileage.setContentTextViewText(String.format(Locale.getDefault(), "%.1f km", carInfoModel.getTotalMileage()));

            TestSummaryModel bestHectometerAccelerate = getBestRecord(carInfoModel.getVinCode(), ProfessionalTestEnum.HectometerAccelerate);
            if (bestHectometerAccelerate != null) {
                hectometerAccelerate.setMyCarRecord(String.format(locale, "%.1f s", bestHectometerAccelerate.getTestTime()));
            }

            TestSummaryModel bestKilometersAccelerate = getBestRecord(carInfoModel.getVinCode(), ProfessionalTestEnum.KilometersAccelerate);
            if (bestKilometersAccelerate != null) {
                kilometersAccelerate.setMyCarRecord(String.format(locale, "%.1f s", bestKilometersAccelerate.getTestTime()));
            }


            TestSummaryModel bestKilometersBrake = getBestRecord(carInfoModel.getVinCode(), ProfessionalTestEnum.KilometersBrake);
            if (bestKilometersBrake != null) {
                kilometersBrake.setMyCarRecord(String.format(locale, "%.1f m", bestKilometersBrake.getTestDist()));
            }


            NoiseTestModel noiseTestModel = mDaoSession.queryBuilder(NoiseTestModel.class).where(NoiseTestModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).orderDesc(NoiseTestModelDao.Properties.CreateDate).limit(1).unique();
            if (noiseTestModel != null) {
                noiseValue.setContentTextViewText(String.format(locale,"%.1f dB",noiseTestModel.getAvgNoise()));
                vehicleSpeed.setContentTextViewText(String.format(locale,"%.1f km/h",noiseTestModel.getAvgSpeed()));
            }


        }


        hectometerAccelerate.setOnDisplayViewClickListener(this);
        kilometersAccelerate.setOnDisplayViewClickListener(this);
        kilometersBrake.setOnDisplayViewClickListener(this);




    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


    @Override
    public void onClickHelp(ProfessionalTestDisplayView v) {

        String helpDescription = null;
        int titleID = 0;
        if (v == hectometerAccelerate) {
            helpDescription = this.getString(R.string.hectometerAccelerateHelp);
            titleID = R.string.hectometerAccelerateName;
        } else if (v == kilometersAccelerate) {
            helpDescription = this.getString(R.string.kilometersAccelerateHelp);
            titleID = R.string.kilometersAccelerateName;
        } else if (v == kilometersBrake) {
            helpDescription = this.getString(R.string.kilometersBrakeHelp);
            titleID = R.string.kilometersBrakeName;
        }

        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle(titleID);
        normalDialog.setMessage(helpDescription);
        // 显示
        normalDialog.show();
    }

    @Override
    public void onClickChallenge(ProfessionalTestDisplayView v) {

        ProfessionalTestEnum professionalTestEnum = null;
        if (v == hectometerAccelerate) {
            professionalTestEnum = ProfessionalTestEnum.HectometerAccelerate;
        } else if (v == kilometersAccelerate) {
            professionalTestEnum = ProfessionalTestEnum.KilometersAccelerate;
        } else if (v == kilometersBrake) {
            professionalTestEnum = ProfessionalTestEnum.KilometersBrake;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyEnum.professionalTestType, professionalTestEnum);
        readyGo(ProfessionalTestDetailActivity.class, bundle);
    }

    @Override
    public void onClickRecord(ProfessionalTestDisplayView v) {
        ProfessionalTestEnum professionalTestEnum = null;
        if (v == hectometerAccelerate) {
            professionalTestEnum = ProfessionalTestEnum.HectometerAccelerate;
        } else if (v == kilometersAccelerate) {
            professionalTestEnum = ProfessionalTestEnum.KilometersAccelerate;
        } else if (v == kilometersBrake) {
            professionalTestEnum = ProfessionalTestEnum.KilometersBrake;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyEnum.professionalTestType, professionalTestEnum);
        readyGo(TestRecordActivity.class, bundle);
    }

    private TestSummaryModel getBestRecord(String vinCode, ProfessionalTestEnum type) {

        TestSummaryModel bestRecord = null;
        if (type != ProfessionalTestEnum.KilometersBrake) {

            bestRecord = mDaoSession.queryBuilder(TestSummaryModel.class).where(TestSummaryModelDao.Properties.VinCode.eq(vinCode), TestSummaryModelDao.Properties.TestType.eq(type.getValue())).orderAsc(TestSummaryModelDao.Properties.TestTime).limit(1).unique();
        } else {
            bestRecord = mDaoSession.queryBuilder(TestSummaryModel.class).where(TestSummaryModelDao.Properties.VinCode.eq(vinCode), TestSummaryModelDao.Properties.TestType.eq(type.getValue())).orderAsc(TestSummaryModelDao.Properties.TestDist).limit(1).unique();
        }

        return bestRecord;

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_noise_test:
                readyGo(NoiseTestActivity.class);
                break;
            case R.id.noiseRecord:
                readyGo(NoiseRecordActivity.class);
                break;
        }
    }


}
