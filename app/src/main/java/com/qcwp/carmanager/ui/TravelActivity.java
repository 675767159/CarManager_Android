package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.TravelItemView;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.utils.CommonUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelActivity extends BaseActivity {


    @BindView(R.id.carType)
    TextView carType;
    @BindView(R.id.totalMileage)
    TravelItemView totalMileage;
    @BindView(R.id.totalTime)
    TravelItemView totalTime;
    @BindView(R.id.singleLongestMileage)
    TravelItemView singleLongestMileage;
    @BindView(R.id.singleLongestTime)
    TravelItemView singleLongestTime;
    @BindView(R.id.totalAccelerate)
    TravelItemView totalAccelerate;
    @BindView(R.id.totalBrake)
    TravelItemView totalBrake;
    @BindView(R.id.totalOverSpeed)
    TravelItemView totalOverSpeed;
    @BindView(R.id.totalOilResume)
    TravelItemView totalOilResume;
    @BindView(R.id.totalAverageOilResume)
    TravelItemView totalAverageOilResume;
    @BindView(R.id.singleMileage)
    TravelItemView singleMileage;
    @BindView(R.id.singleTime)
    TravelItemView singleTime;
    @BindView(R.id.singleAverageSpeed)
    TravelItemView singleAverageSpeed;
    @BindView(R.id.singleAverageOilResume)
    TravelItemView singleAverageOilResume;
    @BindView(R.id.singleAccelerate)
    TravelItemView singleAccelerate;
    @BindView(R.id.singleBrake)
    TravelItemView singleBrake;
    @BindView(R.id.singleOverSpeed)
    TravelItemView singleOverSpeed;
    @BindView(R.id.carCheckScore)
    TravelItemView carCheckScore;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_travel;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        CarInfoModel carInfoModel = CarInfoModel.getCarInfoByVinCode(UserData.getInstance().getVinCode());
        if (carInfoModel != null) {
            carType.setText(carInfoModel.getCarType().getCarTypeName());


            Locale locale=Locale.getDefault();
            CarVinStatisticModel carVinStatisticModel=mDaoSession.queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).unique();
            if (carVinStatisticModel!=null) {

                totalMileage.setContentTextViewText(String.format(locale, "%.1f km", carVinStatisticModel.getDistCount()));
                totalTime.setContentTextViewText(CommonUtils.longTimeToStr(carVinStatisticModel.getTimeCount()));
                singleLongestMileage.setContentTextViewText(String.format(locale, "%.1f km", carVinStatisticModel.getMaxDist()));
                singleLongestTime.setContentTextViewText(CommonUtils.longTimeToStr(carVinStatisticModel.getMaxTime()));
                totalAccelerate.setContentTextViewText(String.format(locale, "%d 次", carVinStatisticModel.getAccelerCount()));
                totalBrake.setContentTextViewText(String.format(locale, "%d 次", carVinStatisticModel.getDecelerCount()));
                totalOverSpeed.setContentTextViewText(String.format(locale, "%d 次", carVinStatisticModel.getOverspeedCount()));
                totalOilResume.setContentTextViewText(String.format(locale, "%.1f L", carVinStatisticModel.getFuelCount()));
                totalAverageOilResume.setContentTextViewText(String.format(locale, "%.1f L/100km", carVinStatisticModel.getAvgFuel()));
            }



            SingleCarVinStatisticModel singleCarVinStatisticModel=mDaoSession.queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).unique();
            if (singleCarVinStatisticModel!=null){

                singleMileage.setContentTextViewText(String.format(locale, "%.1f km", singleCarVinStatisticModel.getDistCount()));
                singleTime.setContentTextViewText(CommonUtils.longTimeToStr(singleCarVinStatisticModel.getTimeCount()));

                singleAverageSpeed.setContentTextViewText(String.format(locale,"%.1f km/h",singleCarVinStatisticModel.getAvgVehicleSpeed()));
                singleAverageOilResume.setContentTextViewText(String.format(locale,"%.1f L/100km",singleCarVinStatisticModel.getAvgFuel()));
                singleAccelerate.setContentTextViewText(String.format(locale,"%d 次",singleCarVinStatisticModel.getAccelerCount()));
                singleBrake.setContentTextViewText(String.format(locale,"%d 次",singleCarVinStatisticModel.getDecelerCount()));
                singleOverSpeed.setContentTextViewText(String.format(locale,"%d 次",singleCarVinStatisticModel.getOverspeedCount()));
                carCheckScore.setContentTextViewText(String.format(locale,"%.0f 分",singleCarVinStatisticModel.getCarCheckAvg()));

            }

        }


    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.travelRecord:
                readyGo(TravelRecordActivity.class);
                break;

        }
    }
}
