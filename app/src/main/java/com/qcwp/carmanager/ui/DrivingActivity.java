package com.qcwp.carmanager.ui;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.InstrumentView;
import com.qcwp.carmanager.control.Thermometer;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import java.util.Locale;

import butterknife.BindView;

public class DrivingActivity extends BaseActivity {


    @BindView(R.id.engineRpm)
    InstrumentView engineRpm;
    @BindView(R.id.vehicleSpeed)
    InstrumentView vehicleSpeed;
    @BindView(R.id.fuelPressure)
    InstrumentView fuelPressure;
    @BindView(R.id.averageSpeed)
    TitleContentView averageSpeed;
    @BindView(R.id.instantOilConsume)
    TitleContentView instantOilConsume;
    @BindView(R.id.averageOilConsume)
    TitleContentView averageOilConsume;
    @BindView(R.id.currentMileage)
    TitleContentView currentMileage;
    @BindView(R.id.totalMileage)
    TitleContentView totalMileage;
    @BindView(R.id.totalTime)
    TitleContentView totalTime;
    @BindView(R.id.engineCoolant)
    Thermometer engineCoolant;
    @BindView(R.id.intakeTemp)
    Thermometer intakeTemp;
    @BindView(R.id.engineCoolantTextView)
    TextView engineCoolantTextView;
    @BindView(R.id.intakeTempTextView)
    TextView intakeTempTextView;
    @BindView(R.id.annunciator)
    TextView annunciator;

    private OBDClient obdClient;
    private Locale locale;
    private AlertDialog overSpeedReminderDialog;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_driving;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        engineRpm.initView(8, 9, 10, "r/min");

        vehicleSpeed.initView(240, 13, 2, "km/h");

        fuelPressure.initView(8, 9, 10, "kpa");

        engineCoolant.initTemperatureRange(40, 215);

        intakeTemp.initTemperatureRange(40, 215);

        obdClient = OBDClient.getDefaultClien();
        locale = Locale.getDefault();

        if (EmptyUtils.isNotEmpty(obdClient.getVinCode())) {
            CarInfoModel carInfoModel = CarInfoModel.getCarInfoByVinCode(obdClient.getVinCode());
            if (carInfoModel != null) {
                annunciator.setText(carInfoModel.getCarSeries() + " " + carInfoModel.getCarNumber());
            }
        }

        AlertDialog.Builder overSpeedReminder =
                new AlertDialog.Builder(this);
        overSpeedReminder.setTitle("警告");
        overSpeedReminder.setMessage("您已超出您所设置的最大速度！");
        overSpeedReminderDialog = overSpeedReminder.create();
    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {


        switch (messageEvent.getType()) {
            case Driving: {
                Print.d(TAG, "---------");
                vehicleSpeed.setValue((int) obdClient.getVehicleSpeed());
                engineRpm.setValue2((int) obdClient.getEngineRpm());
                fuelPressure.setValue2((int) obdClient.getFuelPressure());


                averageSpeed.setContentTextViewText(String.format(locale, "%.0f KM/H", obdClient.getAvgVehicleSpeed()));
                instantOilConsume.setContentTextViewText(String.format(locale, "%.2f L/100KM", obdClient.getCurrentOilConsume()));
                averageOilConsume.setContentTextViewText(String.format(locale, "%.2f L/100KM", obdClient.getAvgOilConsume()));
                currentMileage.setContentTextViewText(String.format(locale, "%.1f KM", obdClient.getDist()));
                totalMileage.setContentTextViewText(String.format(locale, "%.1f KM", obdClient.getTotalMileage()));
                totalTime.setContentTextViewText(CommonUtils.longTimeToStr((int) obdClient.getTotalTime()));

                engineCoolant.setTemperatureC((int) obdClient.getEngineCoolant());
                intakeTemp.setTemperatureC((int) obdClient.getIntakeTemp());

                engineCoolantTextView.setText(String.format(locale, "%.0f", obdClient.getEngineCoolant()));
                intakeTempTextView.setText(String.format(locale, "%.0f", obdClient.getIntakeTemp()));

            }
            break;
            case OverSpeed:
                Print.d(TAG, "showPopupWindow");
                if (!overSpeedReminderDialog.isShowing())
                    overSpeedReminderDialog.show();
                break;
            case NormalSpeed:
                Print.d(TAG, "hidePopupWindow");
                if (overSpeedReminderDialog.isShowing())
                    overSpeedReminderDialog.dismiss();
                break;
        }


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.map:
               readyGo(DriveTrackActivity.class);
                break;

        }
    }



}
