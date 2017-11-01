package com.qcwp.carmanager.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.HomeItemView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.mvp.contact.MainContract;
import com.qcwp.carmanager.mvp.present.MainPresenter;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;
import com.qiantao.coordinatormenu.CoordinatorMenu;

import java.util.Locale;

import butterknife.BindView;

import static com.qcwp.carmanager.obd.OBDConnectService.REQUEST_WIFILocationRequestResult_Code;


public class MainActivity extends BaseActivity implements MainContract.View{


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
    private MainContract.Presenter presenter;
    private AlertDialog overSpeedReminderDialog;
    private OBDClient obdClient;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter=new MainPresenter(this,mDaoSession);
        userName.setText(UserData.getInstance().getUserName());
        obdClient = OBDClient.getDefaultClien();
        carInfoModel=presenter.getLatestCarInfo();


        if (carInfoModel!=null) {
            UserData.getInstance().setVinCode(carInfoModel.getVinCode());
            carVinStatisticModel = presenter.getCarVinStatistic(carInfoModel.getVinCode());
            singleCarVinStatisticModel = presenter.getSingleCarVinStatistic(carInfoModel.getVinCode());
        }


        this.updateUI();

        presenter.getMyAllCarInfo();


        AlertDialog.Builder overSpeedReminder =
                new AlertDialog.Builder(this);
        overSpeedReminder.setTitle("警告");
        overSpeedReminder.setMessage("您已超出您所设置的最大速度！");
        overSpeedReminderDialog = overSpeedReminder.create();
        Print.d(TAG,"UserId="+UserData.getInstance().getUserId());
        Print.d(TAG,"UserName="+UserData.getInstance().getUserName());


        if (Build.VERSION.SDK_INT >= 6.0 && !CommonUtils.isGpsOPen(this)) {
            CommonUtils.openGPS(this,REQUEST_WIFILocationRequestResult_Code);
        }

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
                readyGo(DrivingActivity.class);
                break;
            case R.id.menu_professional_test:
                readyGo(ProfessionTestActivity.class);
                break;
            case R.id.menu_travel:
                readyGo(TravelActivity.class);
                break;
            case R.id.menu_car_info:
            {
                readyGo(CarDetailActivity.class);
            }
                break;
            case R.id.menu_set:
                readyGo(SetActivity.class);
                break;
            case R.id.home_carInfo:
               readyGo(CarListActivity.class);
                break;
            case R.id.home_totalTravel:
                readyGo(CarDetailActivity.class);
                break;
        }
    }



    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        switch (messageEvent.getType()){
            case CarDataUpdate:
            case  CarBlindSuccess:
            case  CarSelected:
                carInfoModel=presenter.getCarInfoByVinCode(messageEvent.getMessage());

                carVinStatisticModel=presenter.getCarVinStatistic(messageEvent.getMessage());

                singleCarVinStatisticModel=presenter.getSingleCarVinStatistic(messageEvent.getMessage());

                updateUI();
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

            case CarCrash: {

                MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
                if (mySharedPreferences.getBoolean(KeyEnum.isOpenedCrashRemind, false)) {
                    String contacPhone = mySharedPreferences.getString(KeyEnum.contactPhone, "");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + contacPhone));
                    String crashMessage = getString(R.string.crashMessage);
                    crashMessage = crashMessage.replace("(车牌)", carInfoModel.getCarNumber());
                    crashMessage = crashMessage.replace("(碰撞前)", String.format(Locale.getDefault(), "%.1f", obdClient.getBfImpactVehicleSpeed()));
                    crashMessage = crashMessage.replace("(碰撞后)", String.format(Locale.getDefault(), "%.1f", obdClient.getAfImapactVehicleSpeed()));
                    intent.putExtra("sms_body", crashMessage);
                    startActivity(intent);
                }

            }
                break;

        }

    }


    private void updateUI(){
        if (carInfoModel!=null){
            carInfo.setValue1(carInfoModel.getCarSeries());
            carInfo.setValue2(carInfoModel.getCarNumber());

        }
        Locale locale=Locale.getDefault();
        if (carVinStatisticModel!=null){

            totalTravel.setValue1(String.format(locale,"%.1fkm",carVinStatisticModel.getDistCount()));
            totalTravel.setValue2(CommonUtils.longTimeToStr(carVinStatisticModel.getTimeCount()));
            totalTravel.setValue3(String.format(locale,"%.1f升",carVinStatisticModel.getFuelCount()));


            professionalTest.setValue1(String.format(locale,"%.2fs",carVinStatisticModel.getBmtestBestScore()));
            professionalTest.setValue2(String.format(locale,"%.2fm",carVinStatisticModel.getZdtestBestScore()));
            professionalTest.setValue3(String.format(locale,"%.2fs",carVinStatisticModel.getBltestBestScore()));

            driveHabit.setValue1(String.format(locale,"%d次",carVinStatisticModel.getAccelerCount()));
            driveHabit.setValue2(String.format(locale,"%d次",carVinStatisticModel.getOverspeedCount()));
            driveHabit.setValue3(String.format(locale,"%d次",carVinStatisticModel.getDecelerCount()));
        }

        if (singleCarVinStatisticModel!=null){

            singleTravel.setValue1(String.format(locale,"%.1fkm",singleCarVinStatisticModel.getDistCount()));
            singleTravel.setValue2(CommonUtils.longTimeToStr(singleCarVinStatisticModel.getTimeCount()));
            singleTravel.setValue3(String.format(locale,"%.1f升",singleCarVinStatisticModel.getFuelCount()));

        }
        if ((obdClient.getConnectStatus() == OBDConnectStateEnum.connectTypeHaveBinded) && carInfoModel.getVinCode().equals(obdClient.getVinCode())) {
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


    @Override
    public void showProgress(String text) {
        showLoadingDialog(text);
    }

    @Override
    public void dismissProgress() {
        dismissLoadingDialog();

    }

    @Override
    public void showTip(String message) {

        showToast(message);
    }

    @Override
    public Boolean isActive() {
        return isActive;
    }


    @Override
    public void onSuccessGetMyAllCarInfo(CarInfoModel carInfoModel) {

       this.carInfoModel=carInfoModel;
        if (carInfoModel!=null){
            UserData.getInstance().setVinCode(carInfoModel.getVinCode());
        }
       this.updateUI();
    }
}
