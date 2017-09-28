package com.qcwp.carmanager.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.PinyinUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.CarEditSelectAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.EditCarInputView;
import com.qcwp.carmanager.control.NavBarView;
import com.qcwp.carmanager.engine.RequestModel;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.TimeEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarSeriesModelDao;
import com.qcwp.carmanager.greendao.gen.CarTypeModelDao;
import com.qcwp.carmanager.greendao.gen.CommonBrandModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.NavbarView)
    NavBarView navbarView;

    private CarInfoModel  carInfoModel;
    private long carBrandId,carCommonBrandId,carSerisId,carTypeId;
    private String initMileageStr,ownerNameStr,carNumberStr,
            buyDateStr,carColorStr;
    private Type type;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_edit_car;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        String vinCode=getIntent().getStringExtra(KeyEnum.vinCode);
        if (EmptyUtils.isNotEmpty(vinCode)){
            vincode.setText(vinCode);
            carInfoModel=mApp.getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vincode.getText())).build().unique();
        }else{
            vincode.setEnabled(true);

        }

        type=(Type) getIntent().getSerializableExtra(KeyEnum.typeKey);
        switch (type){
            case Bind:
                navbarView.setBackButtonHidden(true);
                navbarView.setTitle("车辆绑定");
                break;
        }
        updateUI();

    }

    private void updateUI(){
        Print.d(TAG,"-------------");
        if (carInfoModel!=null){

            carNumber.setText(carInfoModel.getCarNumber());
            carBrand.setText(carInfoModel.getBrand());
            carBrandId=carInfoModel.getBrandId();
            carSeries.setText(carInfoModel.getCarSeries());
            carSerisId=carInfoModel.getCarSeriesId();
            carType.setText(carInfoModel.getCarType().getCarTypeName());
            carTypeId=carInfoModel.getCarTypeId();
            if (EmptyUtils.isNotEmpty(carInfoModel.getIsoBuyDate())) {
                buyDateStr = carInfoModel.getIsoBuyDate().replace(" 00:00:00", "");
            }
            buyDate.setText(buyDateStr);
            carColorStr=carInfoModel.getCarColor();
            carColor.setText(carColorStr);
            initMileage.setText(String.valueOf(carInfoModel.getTotalMileage()));
            ownerName.setText(carInfoModel.getOwnerName());


        }

    }


    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.carBrand:
            {
                this.getAllCarBrand();
            }
                break;
            case R.id.carSeries:
            {
                this.getAllCommonCarBrand();
            }
            break;
            case R.id.carType:
            {
                this.getAllCarType();
            }
            break;
            case R.id.buyDate:
            {
                this.chooseBuydate();
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
                List<CarBrandModel> carBrandModels=mApp.getDaoInstant().loadAll(CarBrandModel.class);

                for (CarBrandModel carBrandModel:carBrandModels){
                    if (carBrandModel.getBrandNamePinYin()==null) {
                        String firstLetter=PinyinUtils.getPinyinFirstLetter(carBrandModel.getBrandName());
                        carBrandModel.setBrandNamePinYin(firstLetter);
                        mDaoSession.update(carBrandModel);

                    }

                }

                list=mDaoSession.queryBuilder(CarBrandModel.class).orderAsc(CarBrandModelDao.Properties.BrandNamePinYin).list();
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

                        carCommonBrandId=0;
                        carSerisId=0;
                        carSeries.setText("");
                        carTypeId=0;
                        carType.setText("");

                       CarEditActivity.this.getAllCommonCarBrand();
                        break;
                    case Carseries:
                        CarSeriesModel carSeriesModel=(CarSeriesModel) finalList.get(which);
                        carSeries.setText(carSeriesModel.getSeriesName());
                        carSerisId=carSeriesModel.getId();


                        carTypeId=0;
                        carType.setText("");

                        CarEditActivity.this.getAllCarType();

                        break;
                    case CarCommonBrand:
                        CommonBrandModel commonBrandModel=(CommonBrandModel) finalList.get(which);
                        carCommonBrandId=commonBrandModel.getId();
                        CarEditActivity.this.getAllCarSeries();

                        carSerisId=0;
                        carSeries.setText("");
                        carTypeId=0;
                        carType.setText("");

                        break;
                }

            }
        });
        builder.show();
    }


    private void chooseBuydate(){
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



            if (carInfoModel==null) {
                 carInfoModel = new CarInfoModel();
            }
            carInfoModel.setVinCode(vincode.getText());
            carInfoModel.setCarNumber(carNumberStr);
            carInfoModel.setBrand(carBrand.getText());
            carInfoModel.setBrandId(carBrandId);
            carInfoModel.setCarSeries(carSeries.getText());
            carInfoModel.setCarSeriesId(carSerisId);
            carInfoModel.setCarTypeId(carTypeId);
            carInfoModel.setTotalMileage(Double.parseDouble(initMileageStr));
            carInfoModel.setOwnerName(ownerNameStr);
            carInfoModel.setCarColor(carColorStr);
            carInfoModel.setIsoBuyDate(buyDateStr+" 00:00:00");
            carInfoModel.setMemberId(UserData.getInstance().getUserId());
            carInfoModel.setNeedUpload(UploadStatusEnum.NotUpload);
            carInfoModel.setCarType(null);
            Print.d(TAG,carInfoModel.toString());
            showLoadingDialog();

            mEngine.uploadCarInfo(carInfoModel).enqueue(new Callback<RequestModel>() {
                @Override
                public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                    RequestModel requestModel=RequestModel.HandlerData(response);
                    dismissLoadingDialog();
                    Print.d(TAG,requestModel.status+"-----"+requestModel.msg);
                    if (requestModel.isSuccess) {
                        carInfoModel.setNeedUpload(UploadStatusEnum.HadUpload);
                        CarEditActivity.this.updateSuccess();
                    }else {
                        showToast(requestModel.msg);
                    }



                }

                @Override
                public void onFailure(Call<RequestModel> call, Throwable throwable) {

                    dismissLoadingDialog();
                    showToast(throwable.getLocalizedMessage());

                }
            });


        }else {

            showToast("请先完善车辆信息");
        }
    }


    private void updateSuccess(){

        carInfoModel.setTimestamp(TimeUtils.getNowMills());
        CarTypeModel carTypeModel=new CarTypeModel();
        carTypeModel.setCarTypeName(carType.getText());
        carInfoModel.setCarType(carTypeModel);//后台有一个carType对象，不是字符串，所以传字符串会出错
        mApp.getDaoInstant().insertOrReplace(carInfoModel);
        showLoadingDialog("绑定成功，请在返回...");

        MessageEvent messageEvent=new MessageEvent();
        if (type==Type.Bind) {
            OBDClient.getDefaultClien().setVinCode(vincode.getText());
            UserData.getInstance().setVinCode(vincode.getText());
            messageEvent.setType(MessageEvent.MessageEventType.CarBlindSuccess);
        }else {
            messageEvent.setType(MessageEvent.MessageEventType.CarSelected);
        }
        messageEvent.setMessage(vincode.getText());
        EventBus.getDefault().post(messageEvent);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                dismissLoadingDialog();
                finish();
            }
        }, TimeEnum.Launch_Time);


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

    private void getAllCarBrand(){
        long ID=0;
        CarBrandModel carBrandModel=mDaoSession.queryBuilder(CarBrandModel.class).orderDesc(CarBrandModelDao.Properties.Id).limit(1).unique();
        if (carBrandModel!=null){
            ID=carBrandModel.getId();
        }
        showLoadingDialog();
        mEngine.getAllCarBrand(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel=response.body();
                if (allCarModel.getStatus()==1){
                   List<CarBrandModel>carBrandModels=allCarModel.getBrands();
                    for (CarBrandModel carBrandModel:carBrandModels){
                        mDaoSession.insertOrReplace(carBrandModel);
                        Print.d(TAG,carBrandModel.getBrandName());
                    }

                }
                dismissLoadingDialog();
               CarEditActivity.this.chooseCarInfo(CarListType.CarBrand);
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.CarBrand);
            }
        });
    }

    private void getAllCommonCarBrand(){

        long ID=0;
        CommonBrandModel commonBrandModel=mDaoSession.queryBuilder(CommonBrandModel.class).orderDesc(CommonBrandModelDao.Properties.Id).limit(1).unique();
        if (commonBrandModel!=null){
            ID=commonBrandModel.getId();
        }
        showLoadingDialog();
        mEngine.getAllCommonCarBrand(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel=response.body();
                if (allCarModel.getStatus()==1){
                    List<CommonBrandModel>commonBrands=allCarModel.getCommonBrands();
                    for (CommonBrandModel  commonBrandModel:commonBrands){
                        mDaoSession.insertOrReplace(commonBrandModel);
                        Print.d(TAG,commonBrandModel.getCommonBrandName());
                    }

                }
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.CarCommonBrand);
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.CarCommonBrand);
            }
        });
    }

    private void getAllCarSeries(){

        long ID=0;
        CarSeriesModel carSeriesModel=mDaoSession.queryBuilder(CarSeriesModel.class).orderDesc(CarSeriesModelDao.Properties.Id).limit(1).unique();
        if (carSeriesModel!=null){
            ID=carSeriesModel.getId();
        }

        showLoadingDialog();
        mEngine.getAllCarSeries(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel=response.body();
                if (allCarModel.getStatus()==1){
                    List<CarSeriesModel>carSerieses=allCarModel.getCarSerieses();
                    for (CarSeriesModel  carSeriesModel:carSerieses){
                        mDaoSession.insertOrReplace(carSeriesModel);
                        Print.d(TAG,carSeriesModel.getSeriesName());
                    }

                }
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.Carseries);
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.Carseries);
            }
        });
    }

    private void getAllCarType(){

        long ID=0;
        CarTypeModel carTypeModel=mDaoSession.queryBuilder(CarTypeModel.class).orderDesc(CarTypeModelDao.Properties.Id).limit(1).unique();
        if (carTypeModel!=null){
            ID=carTypeModel.getId();
        }
        showLoadingDialog();
        mEngine.getAllCarType(ID).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {

                AllCarModel allCarModel=response.body();
                if (allCarModel.getStatus()==1){
                    List<CarTypeModel>carTypes=allCarModel.getCarTypes();
                    for (CarTypeModel  carTypeModel:carTypes){
                        mDaoSession.insertOrReplace(carTypeModel);
                        Print.d(TAG,carTypeModel.getCarTypeName());
                    }

                }
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.CarType);
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                dismissLoadingDialog();
                CarEditActivity.this.chooseCarInfo(CarListType.CarType);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(this.type==Type.Bind&&keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);
    }
}
