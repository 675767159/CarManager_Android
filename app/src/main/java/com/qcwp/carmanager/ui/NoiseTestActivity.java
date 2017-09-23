package com.qcwp.carmanager.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.NoiseTestItemView;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.implement.MediaRecorderClass;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.NoiseTestModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import java.util.Locale;

import butterknife.BindView;

public class NoiseTestActivity extends BaseActivity implements MediaRecorderClass.MediaRecorderListener{


    @BindView(R.id.button_start_test)
    Button buttonStartTest;
    @BindView(R.id.currentSpeed)
    NoiseTestItemView currentSpeed;
    @BindView(R.id.averageSpeed)
    NoiseTestItemView averageSpeed;
    @BindView(R.id.averageNoise)
    NoiseTestItemView averageNoise;
    @BindView(R.id.currentNoise)
    NoiseTestItemView currentNoise;
    @BindView(R.id.maxNoise)
    NoiseTestItemView maxNoise;
    @BindView(R.id.minMoise)
    NoiseTestItemView minMoise;

    private MediaRecorderClass mediaRecorder;
    private OBDClient obdClient;
    private CarInfoModel carInfoModel;
    private Locale locale;
    private float totalVehicleSpeed;
    private int count;
    private double totalNoise,max=0,min=100;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_noise_test;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        CommonUtils.setViewCorner(buttonStartTest, 20, 20, R.color.red);
        mediaRecorder=new MediaRecorderClass(mApp.getMyFileFolder(TimeUtils.getNowString()));
        mediaRecorder.setMediaRecorderListener(this);
        obdClient=OBDClient.getDefaultClien();
        locale=Locale.getDefault();
        carInfoModel=CarInfoModel.getCarInfoByVinCode(UserData.getInstance().getVinCode());
    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.button_start_test:
                if (obdClient.getConnectStatus()!= OBDConnectStateEnum.connectTypeHaveBinded){
                 showToast("请先连接OBD设备");
                    return;
                }


                if (Build.VERSION.SDK_INT >= 6.0) {
                    PackageManager pm =getPackageManager();

                    boolean permission = (PackageManager.PERMISSION_GRANTED ==
                            pm.checkPermission("android.permission.RECORD_AUDIO", "com.qcwp.carmanager"));
                    if (!permission)  {
                        ActivityCompat.requestPermissions(MyActivityManager.getInstance().getCurrentActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                                1);
                        showToast("请打开录音权限!");
                        return;
                    }

                }


                if (v.isEnabled()){
                   startTest();
                }
                break;
        }
    }



    private  void  startTest(){


        mediaRecorder.startRecord();
        buttonStartTest.setEnabled(false);
        obdClient.startTest();
        totalNoise=0;
        totalNoise=0;
        count=0;
        max=0;
        min=100;

    }

    private void endTest(){

        buttonStartTest.setEnabled(true);
        mediaRecorder.stopRecord();
        buttonStartTest.setText("开始测试");
        obdClient.stopTest();

        NoiseTestModel noiseTestModel=new NoiseTestModel();
        noiseTestModel.setVinCode(carInfoModel.getVinCode());
        noiseTestModel.setCarNumber(carInfoModel.getCarNumber());
        noiseTestModel.setUserName(UserData.getInstance().getUserName());
        noiseTestModel.setUploadFlag(UploadStatusEnum.NotUpload);
        noiseTestModel.setAvgNoise(totalNoise/count);
        noiseTestModel.setCreateDate(TimeUtils.getNowString());
        noiseTestModel.setAvgSpeed(totalVehicleSpeed/count);
        noiseTestModel.setMaxNoise(max);
        noiseTestModel.setMinNoise(min);
        mDaoSession.insert(noiseTestModel);

    }

    private void updateDisplay(double decibel){

        Print.d(TAG,decibel+"----");
        totalVehicleSpeed+=obdClient.getVehicleSpeed();
        totalNoise+=decibel;
        count++;

        if (decibel>max){
            max=decibel;
        }

        if (decibel<min){
            min=decibel;
        }

        currentSpeed.setValue(String.format(locale,"%.1f",obdClient.getVehicleSpeed()));
        averageSpeed.setValue(String.format(locale,"%.1f",totalVehicleSpeed/count));
        currentNoise.setValue(String.format(locale,"%.1f",decibel));
        averageNoise.setValue(String.format(locale,"%.1f",totalNoise/count));
        maxNoise.setValue(String.format(locale,"%.1f",max));
        minMoise.setValue(String.format(locale,"%.1f",min));


    }

    @Override
    public void getCurrentVoiceDecibel(double decibel, long remainTime) {

        if (remainTime>=20){
          endTest();
        }else {
            buttonStartTest.setText(String.format(Locale.getDefault(),"测试倒计时：%d s",20-remainTime));
            updateDisplay(decibel);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
          endTest();
    }
}
