package com.qcwp.carmanager.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.CarEditSelectAdapter;
import com.qcwp.carmanager.control.EditCarInputView;
import com.qcwp.carmanager.engine.RequestModel;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.greendao.gen.CarSeriesModelDao;
import com.qcwp.carmanager.greendao.gen.CarTypeModelDao;
import com.qcwp.carmanager.greendao.gen.CommonBrandModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;
import com.qcwp.carmanager.utils.Print;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/6/19.
 */

public class CarEditActivity extends BaseActivity {

    @BindView(R.id.vincode)
    EditCarInputView vincode;
    @BindView(R.id.carNumber)
    EditCarInputView carNumber;
    @BindView(R.id.carBrand)
    EditCarInputView carBrand;
    @BindView(R.id.carSeries)
    EditCarInputView carSeries;
    @BindView(R.id.carType)
    EditCarInputView carType;
    @BindView(R.id.buyDate)
    EditCarInputView buyDate;
    @BindView(R.id.initMileage)
    EditCarInputView initMileage;
    @BindView(R.id.ownerName)
    EditCarInputView ownerName;
    @BindView(R.id.carColor)
    EditCarInputView carColor;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;


    private long carBrandId,carCommonBrandId,carSerisId,carTypeId;
    private String buyDateStr,initMileageStr,ownerNameStr,carColorStr,carNumberStr;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_edit_car;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        String vinCode=getIntent().getStringExtra(KeyEnum.vinCode);
        if (EmptyUtils.isNotEmpty(vinCode)){
            vincode.setText(vinCode);
        }
        Type type=(Type) getIntent().getSerializableExtra(KeyEnum.typeKey);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.carBrand:
            {
                this.chooseCarInfo(CarListType.CarBrand);
            }
                break;
            case R.id.carSeries:
            {
                this.chooseCarInfo(CarListType.CarCommonBrand);
            }
            break;
            case R.id.carType:
            {
                this.chooseCarInfo(CarListType.CarType);
            }
            break;
            case R.id.buyDate:
            {
                this.chooseBuydaye();
            }
            break;
            case R.id.carColor:
            {
                this.chooseCarColor();
            }
            break;
            case R.id.button_confirm:
            {
               this.clickConfirm();
            }
            break;

        }

    }




    private void chooseCarInfo(final CarListType carListType){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Holo_Light_Dialog);
        List list = null;
        switch (carListType){
            case CarBrand:

                list= mApp.getDaoInstant().loadAll(CarBrandModel.class);
                builder.setTitle("请选择车品牌");
                break;
            case CarType:
                list= mApp.getDaoInstant().queryBuilder(CarTypeModel.class).where(CarTypeModelDao.Properties.Sid.eq(carSerisId)).orderDesc(CarTypeModelDao.Properties.Id).list();
                builder.setTitle("请选择车型");
                break;
            case CarCommonBrand:
                list= mApp.getDaoInstant().queryBuilder(CommonBrandModel.class).where(CommonBrandModelDao.Properties.Bid.eq(carBrandId)).orderDesc(CommonBrandModelDao.Properties.Id).list();
                builder.setTitle("请选择常用品牌");
                break;
            case Carseries:
                list= mApp.getDaoInstant().queryBuilder(CarSeriesModel.class).where(CarSeriesModelDao.Properties.Cid.eq(carCommonBrandId)).orderDesc(CarSeriesModelDao.Properties.Id).list();
                builder.setTitle("请选择车系");
                break;
        }


        final List finalList = list;
        builder.setAdapter(new CarEditSelectAdapter(this,carListType,list),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (carListType){
                    case CarType:
                     CarTypeModel carTypeModel=(CarTypeModel) finalList.get(which);
                        carType.setText(carTypeModel.getCarTypeName());
                        carTypeId=carTypeModel.getId();
                        break;
                    case CarBrand:
                        CarBrandModel carBrandModel=(CarBrandModel) finalList.get(which);
                        carBrand.setText(carBrandModel.getBrandName());
                        carBrandId=carBrandModel.getId();

                        break;
                    case Carseries:
                        CarSeriesModel carSeriesModel=(CarSeriesModel) finalList.get(which);
                        carSeries.setText(carSeriesModel.getSeriesName());
                        carSerisId=carSeriesModel.getId();
                        break;
                    case CarCommonBrand:
                        CommonBrandModel commonBrandModel=(CommonBrandModel) finalList.get(which);
                        carCommonBrandId=commonBrandModel.getId();
                        CarEditActivity.this.chooseCarInfo(CarListType.Carseries);
                        break;
                }

            }
        });
        builder.show();
    }


    private void chooseBuydaye(){
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970,1,1);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(TimeUtils.getNowDate());
        boolean[] showList={true, true, true, false, false, false};
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                buyDateStr=TimeUtils.date2String(date, dateFormat);
                buyDate.setText(buyDateStr);

            }
        }).setType(showList).setRangDate(startDate,endDate)//起始终止年月日设定
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }



    private void chooseCarColor(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择颜色");
        //    指定下拉列表的显示数据
        final String[] colors = getResources().getStringArray(R.array.CarColors);
        //    设置一个下拉的列表选择项
        builder.setItems(colors, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                     carColorStr=colors[which];
                    carColor.setText(carColorStr);
            }
        });
        builder.show();
    }


    private void clickConfirm(){
        carNumberStr=carNumber.getText();
        initMileageStr=initMileage.getText();
        ownerNameStr=ownerName.getText();

        if (carBrandId!=0&&carSerisId!=0&&carTypeId!=0&&EmptyUtils.isNotEmpty(carNumberStr)&&EmptyUtils.isNotEmpty(buyDateStr)&&EmptyUtils.isNotEmpty(initMileageStr)&&EmptyUtils.isNotEmpty(ownerNameStr)&&EmptyUtils.isNotEmpty(carColorStr)){

            CarInfoModel carInfoModel=new CarInfoModel();
            carInfoModel.setVinCode(vincode.getText());
            carInfoModel.setCarNumber(carNumberStr);
            carInfoModel.setBrand(carBrand.getText());
            carInfoModel.setBrandId(carBrandId);
            carInfoModel.setCarSeries(carSeries.getText());
            carInfoModel.setCarSeriesId(carSerisId);
            carInfoModel.setCarType(carType.getText());
            carInfoModel.setCarTypeId(carTypeId);
            carInfoModel.setTotalMileage(Double.parseDouble(initMileageStr));
            carInfoModel.setOwnerName(ownerNameStr);
            carInfoModel.setCarColor(carColorStr);
            carInfoModel.setIsoBuyDate(buyDateStr+" 00:00:00");
            carInfoModel.setMemberId(UserData.getInstance().getUserId());

//            carInfoModel.setBuyDate(buyDateStr);
//            mApp.getDaoInstant().insertOrReplace(carInfoModel);
            carInfoModel.setCarType(null);
            showLoadingDialog();
            mEngine.uploadCarInfo(carInfoModel).enqueue(new Callback<RequestModel>() {
                @Override
                public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                    RequestModel requestModel=RequestModel.HandlerData(response);
                    dismissLoadingDialog();
                    if (requestModel!=null) {
                        Print.d(TAG, "----"+requestModel.status+requestModel.msg);
                    }else {
                        Print.d(TAG, "----" + "model为空");
                    }
                }

                @Override
                public void onFailure(Call<RequestModel> call, Throwable throwable) {
                    dismissLoadingDialog();
                }
            });


        }else {

            showToast("请先完善车辆信息");
        }
    }


    public  enum Type{
        Bind,
        Edit
    }
    public  enum CarListType{
        CarBrand,
        CarCommonBrand,
        Carseries,
        CarType
    }

}
