package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.greendao.gen.CarCheckModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.TestSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarCheckModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.obd.OBDClient;

import java.util.List;
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
    @BindView(R.id.totalCarCheckCount)
    TitleContentView totalCarCheckCount;
    @BindView(R.id.averageCarCheckScore)
    TitleContentView averageCarCheckScore;
    @BindView(R.id.latestCarCheckScore)
    TitleContentView latestCarCheckScore;
    @BindView(R.id.totalFaultCodeCount)
    TitleContentView totalFaultCodeCount;
    @BindView(R.id.carEdit)
    Button carEditButton;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        CarInfoModel carInfoModel = CarInfoModel.getCarInfoByVinCode(UserData.getInstance().getVinCode());
        if (carInfoModel != null) {
            Locale locale=Locale.getDefault();
            carSeries.setContentTextViewText(carInfoModel.getCarSeries());
            carNumber.setContentTextViewText(carInfoModel.getCarNumber());
            carVinCode.setContentTextViewText(carInfoModel.getVinCode());
            carTotalMileage.setContentTextViewText(String.format(locale, "%.1fkm", carInfoModel.getTotalMileage()));

            CarVinStatisticModel carVinStatisticModel=mDaoSession.queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(carInfoModel.getVinCode())).unique();


            if (carVinStatisticModel!=null){

                totalCarCheckCount.setContentTextViewText(String.format(locale,"%d 次",carVinStatisticModel.getCarCheckCount()));

                averageCarCheckScore.setContentTextViewText(String.format(locale,"%.0f 分",carVinStatisticModel.getCarCheckAvg()));

                latestCarCheckScore.setContentTextViewText(String.format(locale,"%d 分",carVinStatisticModel.getLastCarCheck()));

                totalFaultCodeCount.setContentTextViewText(String.format(locale,"%d 个",carVinStatisticModel.getFaultCodeCount()));

            }

        }else {
            carEditButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.physicalExamination:
                readyGo(CarExaminationActivity.class);
                break;
            case R.id.carEdit:
                Bundle bundle=new Bundle();
                bundle.putString(KeyEnum.vinCode,UserData.getInstance().getVinCode());
                bundle.putSerializable(KeyEnum.typeKey,CarEditActivity.Type.Edit);
                readyGo(CarEditActivity.class,bundle);
                break;
        }
    }


}
