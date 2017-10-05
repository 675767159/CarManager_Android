package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.CarCheckExpandableListAdapter;
import com.qcwp.carmanager.adapter.CarCheckRecordAdapter;
import com.qcwp.carmanager.adapter.SetExpandableListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.control.TouchHighlightImageView;
import com.qcwp.carmanager.enumeration.ExamnationStatusEnum;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarCheckModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarCheckModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CarExaminationActivity extends BaseActivity {


    @BindView(R.id.btn_start)
    TouchHighlightImageView btnStart;
    @BindView(R.id.carSeries)
    TitleContentView carSeries;
    @BindView(R.id.carState)
    TitleContentView carState;
    @BindView(R.id.score)
    TextView carCheckScore;
    @BindView(R.id.examinationProgress)
    ProgressBar examinationProgress;
    @BindView(R.id.faultCode)
    TitleContentView faultCode;
    @BindView(R.id.unusualValue)
    TitleContentView unusualValue;
    @BindView(R.id.examinationTime)
    TitleContentView examinationTime;
    @BindView(R.id.selectModel)
    RadioGroup selectModel;
    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;
    @BindView(R.id.examnationItem)
    TextView examnationItem;

    private Animation rotateAnimation;
    private OBDClient obdClient;
    private int progress;
    private final int allItemCount=103;
    private List dataList;
    private  List<CarCheckModel> carCheckModels;
    private String carCheckTime;
    private CarInfoModel carInfo;
    private String engineConditions;
    private String driveDatas;
    private String powertrainDTCS;
    private String chassisDTCS;
    private String carBodyDTCS;
    private String networkDTCS;
    private CarCheckExpandableListAdapter carExamnationExpandableListAdapter;


    private Boolean hadClear=false;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_car_examination;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.car_examination_animation);
        obdClient=OBDClient.getDefaultClien();
        carInfo = CarInfoModel.getLatestCarInfo();
        if (carInfo != null) {
            carSeries.setContentTextViewText(carInfo.getCarSeries());

        }




        carExamnationExpandableListAdapter=new CarCheckExpandableListAdapter(this);
        expandablelistview.setAdapter(carExamnationExpandableListAdapter);





        examinationProgress.setMax(allItemCount);
        examinationProgress.setVisibility(View.GONE);


       CarCheckModel carCheckModel = mDaoSession.queryBuilder(CarCheckModel.class).where(CarCheckModelDao.Properties.VinCode.eq(carInfo.getVinCode())).orderDesc(CarCheckModelDao.Properties.CreateDate).limit(1).unique();

        if (carCheckModel!=null) {
            carCheckTime = carCheckModel.getCreateDate();
            this.loadCarCheckRecord(carCheckTime);
        }


        selectModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int index=group.indexOfChild(getViewById(checkedId));
                switch (index){
                    case 0:
                        if (EmptyUtils.isNotEmpty(carCheckTime)) {
                            CarExaminationActivity.this.loadCarCheckRecord(carCheckTime);
                        }
                        break;
                    case 1:

                        if (carInfo!=null) {
                            carCheckModels = mDaoSession.queryBuilder(CarCheckModel.class).where(CarCheckModelDao.Properties.VinCode.eq(carInfo.getVinCode())).orderDesc(CarCheckModelDao.Properties.CreateDate).list();
                        }
                        expandablelistview.setAdapter(new CarCheckRecordAdapter(CarExaminationActivity.this,carCheckModels));
                        CarExaminationActivity.this.expandGroup(1);
                        CarExaminationActivity.this.autoScrollTop();
                        break;

                }

            }
        });

        expandablelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                ExpandableListAdapter adapter=parent.getExpandableListAdapter();
                if (adapter instanceof CarCheckRecordAdapter) {

                    CarCheckModel carCheckModel = carCheckModels.get(childPosition);
                    carCheckTime = carCheckModel.getCreateDate();
                    CarExaminationActivity.this.loadCarCheckRecord(carCheckTime);
                    RadioButton radioButtonAll = (RadioButton) selectModel.findViewById(R.id.radioButton1);
                    radioButtonAll.setChecked(true);
                }else {
                    CarExaminationActivity.this.startExamination(LoadDataTypeEnum.dataTypeClearErr);
                }

                return false;
            }
        });



    }


    private void loadCarCheckRecord(String createDate){
        CarCheckModel carCheckModel=mDaoSession.queryBuilder(CarCheckModel.class).where(CarCheckModelDao.Properties.CreateDate.eq(createDate)).limit(1).unique();
        if (carCheckModel!=null){
            int score=carCheckModel.getScore();
            carState.setContentTextViewText(CommonUtils.carCheckUpStatus(score));
            carCheckScore.setText(carCheckModel.getScore()+"分");
            unusualValue.setContentTextViewText(carCheckModel.getUnusualCodeCount()+"个");
            faultCode.setContentTextViewText(carCheckModel.getFaultCodeCount()+"个");
            examinationTime.setContentTextViewText(carCheckModel.getCreateDate().substring(0,16));

            this.initDataList();

            String[] powertrainDTCList =carCheckModel.getPowertrainDTCS().split(",");

            this.handleRecordFaultCodes(powertrainDTCList,this.getString(R.string.powertrainDTC));


            String[] chassisDTCList  =carCheckModel.getChassisDTCS().split(",");

            this.handleRecordFaultCodes(chassisDTCList,this.getString(R.string.chassisDTC));


            String[] carBodyDTCList  =carCheckModel.getCarBodyDTCS().split(",");

            this.handleRecordFaultCodes(carBodyDTCList,this.getString(R.string.carBodyDTC));


            String[] networkDTCList  =carCheckModel.getNetworkDTCS().split(",");

            this.handleRecordFaultCodes(networkDTCList,this.getString(R.string.networkDTC));


            String[] engineConditionList=carCheckModel.getEngineConditions().split(",");
            String[] engineConditionItems=this.getResources().getStringArray(R.array.EngineConditions);
            this.handleRecordOthers(engineConditionList,this.getString(R.string.engineCondition),engineConditionItems);

            String[] driveDataList=carCheckModel.getDriveDatas().split(",");
            JSONArray driveDataPidArray = CommonUtils.getJSONArrayFromText("CarCheckDriveDataPids.json");
            this.handleRecordOthers(driveDataList,this.getString(R.string.driveData),driveDataPidArray);

            carExamnationExpandableListAdapter=new CarCheckExpandableListAdapter(this);
            expandablelistview.setAdapter(carExamnationExpandableListAdapter);
            carExamnationExpandableListAdapter.updateData(dataList);
            this.expandGroup(7);


        }

    }


    private void handleRecordFaultCodes(String[] faultCodes,String title){
        Map faultMap=new HashMap();

        Map headMap=new HashMap();
        headMap.put(KeyEnum.nameKey,title);
        if (EmptyUtils.isNotEmpty(faultCodes[0])) {
            headMap.put(KeyEnum.statusKey, ExamnationStatusEnum.UNUSUAL);
        }else {
            headMap.put(KeyEnum.statusKey,ExamnationStatusEnum.NORMAL);
        }
        faultMap.put(KeyEnum.groupKey,headMap);

        List list=new ArrayList();
        for (String code:faultCodes){
            if (EmptyUtils.isNotEmpty(code)) {
                Map map = new HashMap();
                map.put(KeyEnum.nameKey, code);
                map.put(KeyEnum.statusKey, ExamnationStatusEnum.Unknown);
                list.add(map);
            }

        }
        faultMap.put(KeyEnum.childKey,list);
        dataList.add(faultMap);

    }

    private void handleRecordOthers(String[] others,String title,JSONArray othersName){
        Map faultMap=new HashMap();

        Map headMap=new HashMap();
        headMap.put(KeyEnum.nameKey,title);
        headMap.put(KeyEnum.statusKey, ExamnationStatusEnum.Unknown);
        faultMap.put(KeyEnum.groupKey,headMap);

        List list=new ArrayList();
        int i=0;
        for (String data:others){
            if (EmptyUtils.isNotEmpty(data)) {
                Map map = new HashMap();
                map.put(KeyEnum.nameKey, othersName.optJSONObject(i).optString(KeyEnum.pidInfoKey));
                map.put(KeyEnum.statusKey, ExamnationStatusEnum.fromInteger(Integer.parseInt(data)));
                list.add(map);
                i++;
            }

        }
        faultMap.put(KeyEnum.childKey,list);
        dataList.add(faultMap);
    }

    private void handleRecordOthers(String[] others,String title,String[] othersName){

        Map faultMap=new HashMap();

        Map headMap=new HashMap();
        headMap.put(KeyEnum.nameKey,title);
        headMap.put(KeyEnum.statusKey, ExamnationStatusEnum.Unknown);
        faultMap.put(KeyEnum.groupKey,headMap);

        List list=new ArrayList();
        int i=0;
        for (String data:others){
            if (EmptyUtils.isNotEmpty(data)) {
                Map map = new HashMap();
                map.put(KeyEnum.nameKey, othersName[i]);
                map.put(KeyEnum.statusKey, ExamnationStatusEnum.fromInteger(Integer.parseInt(data)));
                list.add(map);
                i++;
            }

        }
        faultMap.put(KeyEnum.childKey,list);
        dataList.add(faultMap);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        Map map=messageEvent.getData();
        switch (messageEvent.getType()){

            case CarCheck_some: {
                examnationItem.setText(messageEvent.getMessage());
                progress++;
                examinationProgress.setProgress(progress);
                this.autoScrollBottom();
            }
                break;
            case CarCheck_faultCode:
                this.handleFaultCode(map);
                break;
            case CarCheck_engineCondition:
              this.handleEngineCondition(map);
                break;
            case CarCheck_driveData:
               this.handleDriveData(map);
                break;
            case CarCheck_end:
                endExamination(map);
                break;

        }


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_start:
                if (obdClient.getLoadDataType()==LoadDataTypeEnum.dataTypedrive) {
                    CarExaminationActivity.this.startExamination(LoadDataTypeEnum.dataTypeTijian);
                }else {
                    showToast("请先连接上OBD,等待初始化完成！");
                }
                break;
        }
    }

    private void startExamination(LoadDataTypeEnum type) {
        btnStart.setImageResource(R.mipmap.car_examination_needle);
        btnStart.startAnimation(rotateAnimation);
        btnStart.setEnabled(false);
        examnationItem.setVisibility(View.VISIBLE);
        progress=0;

        engineConditions="";
        driveDatas="";
        powertrainDTCS="";
        chassisDTCS="";
        carBodyDTCS="";
        networkDTCS="";

        this.initDataList();
        carExamnationExpandableListAdapter.invalidData();
        switch (type){
            case dataTypeTijian:
                obdClient.startExamnation();
                break;
            case dataTypeClearErr:
                obdClient.clearFaultCode();
                break;
        }

        examinationProgress.setVisibility(View.VISIBLE);

        carCheckScore.setVisibility(View.GONE);
        unusualValue.setVisibility(View.GONE);
        faultCode.setVisibility(View.GONE);
        examinationTime.setVisibility(View.GONE);
        carState.setContentTextViewText("正在体检...");



    }


    private void endExamination(Map map) {
        btnStart.setImageResource(R.mipmap.car_examination_restart);
        btnStart.clearAnimation();
        btnStart.setEnabled(true);
        examnationItem.setVisibility(View.GONE);
        examinationProgress.setVisibility(View.GONE);
        Integer score=(Integer)map.get("carCheckUpScore");

        carCheckScore.setVisibility(View.VISIBLE);
        carCheckScore.setText(score+"分");


        carState.setContentTextViewText(CommonUtils.carCheckUpStatus(score));

        Integer unusualCodeCount=(Integer)map.get("DrivingDataUnusualCount");
        unusualValue.setVisibility(View.VISIBLE);
        unusualValue.setContentTextViewText(unusualCodeCount+"个");

        faultCode.setVisibility(View.VISIBLE);
        Integer faultCodeCount=(Integer)map.get("DtcCount");
        faultCode.setContentTextViewText(faultCodeCount+"个");

        examinationTime.setVisibility(View.VISIBLE);
        String currentTime=TimeUtils.getNowString();
        examinationTime.setContentTextViewText(currentTime.substring(0,16));


        if (faultCodeCount>0&&!hadClear){
            showToast("发现故障码，现进行自动清除！");
            this.startExamination(LoadDataTypeEnum.dataTypeClearErr);
            hadClear=true;
        }else {

            if (faultCodeCount>0){
                showToast("有无法清除的故障码！");
            }
            hadClear=false;
            CarCheckModel carCheckModel = new CarCheckModel();
            carCheckModel.setScore(score);
            carCheckModel.setCreateDate(currentTime);
            carCheckModel.setUnusualCodeCount(unusualCodeCount);
            carCheckModel.setFaultCodeCount(faultCodeCount);
            carCheckModel.setVinCode(carInfo.getVinCode());
            carCheckModel.setPowertrainDTCS(powertrainDTCS);
            carCheckModel.setChassisDTCS(chassisDTCS);
            carCheckModel.setCarBodyDTCS(carBodyDTCS);
            carCheckModel.setNetworkDTCS(networkDTCS);
            carCheckModel.setDriveDatas(driveDatas);
            carCheckModel.setEngineConditions(engineConditions);
            carCheckModel.setCarSeries(carInfo.getCarSeries());
            carCheckModel.setUploadFlag(UploadStatusEnum.NotUpload);
            mDaoSession.insertOrReplace(carCheckModel);


            CarVinStatisticModel carVinStatisticModel = obdClient.getCarVinStatisticModel();
            carVinStatisticModel.setFaultCodeCount(carVinStatisticModel.getFaultCodeCount() + faultCodeCount);

            double averageScore = (carVinStatisticModel.getCarCheckCount() * carVinStatisticModel.getCarCheckAvg() + score) * 1f / (carVinStatisticModel.getCarCheckCount() + 1);

            carVinStatisticModel.setCarCheckAvg(averageScore);
            carVinStatisticModel.setLastCarCheck(score);
            carVinStatisticModel.setCarCheckCount(carVinStatisticModel.getCarCheckCount() + 1);
            mDaoSession.update(carVinStatisticModel);

            TravelSummaryModel travelSummaryModel = obdClient.getTravelSummaryModel();
            travelSummaryModel.setCarCheckUpScore(score);
            mDaoSession.update(carVinStatisticModel);


        }




    }


    private void   handleEngineCondition(Map map){

        Map engineCondition;
        if (dataList.size()<6){
            engineCondition=new HashMap();
            Map head=new HashMap();
            head.put(KeyEnum.nameKey,"发动机工况");
            head.put(KeyEnum.statusKey,ExamnationStatusEnum.Unknown);
            engineCondition.put(KeyEnum.groupKey,head);
            List list=new ArrayList<>();
            list.add(map);
            engineCondition.put(KeyEnum.childKey,list);
            dataList.add(engineCondition);
            carExamnationExpandableListAdapter.updateData(dataList);
            this.expandGroup(dataList.size());


        }else {
            engineCondition=(Map)dataList.get(5);
            List list=(List)engineCondition.get(KeyEnum.childKey);
            list.add(map);
            carExamnationExpandableListAdapter.updateData(dataList);

        }


        ExamnationStatusEnum status=(ExamnationStatusEnum)map.get(KeyEnum.statusKey);
        engineConditions=engineConditions+status.getValue()+",";


    }


    private void   handleFaultCode(Map map){

        String name=(String) map.get(KeyEnum.nameKey);
        List<String> faultCodes=(List<String>) map.get(KeyEnum.statusKey);

        Map faultMap=new HashMap();

        Map head=new HashMap();
        head.put(KeyEnum.nameKey,name);

        if (faultCodes!=null&&faultCodes.size()>0){
            head.put(KeyEnum.statusKey,ExamnationStatusEnum.UNUSUAL);
        }else {
            head.put(KeyEnum.statusKey,ExamnationStatusEnum.NORMAL);
        }
        faultMap.put(KeyEnum.groupKey,head);

        List list=new ArrayList();

        String faultCodeStr="";
        for (String code:faultCodes){

            Map child=new HashMap();
            child.put(KeyEnum.nameKey,code);
            child.put(KeyEnum.statusKey,ExamnationStatusEnum.Unknown);
            list.add(child);

            faultCodeStr=faultCodeStr+code+",";
        }
        faultMap.put(KeyEnum.childKey,list);

        dataList.add(faultMap);
        carExamnationExpandableListAdapter.updateData(dataList);
        this.expandGroup(dataList.size());


        switch (name){
            case "动力系统":
                powertrainDTCS=faultCodeStr;
                break;
            case "底盘系统":
                chassisDTCS=faultCodeStr;
                break;
            case "车身系统":
                carBodyDTCS=faultCodeStr;
                break;
            case "网络通讯系统":
                networkDTCS=faultCodeStr;
                break;
        }

    }




    private void   handleDriveData(Map map){

        Map engineCondition;
        if (dataList.size()<7){
            engineCondition=new HashMap();
            Map head=new HashMap();
            head.put(KeyEnum.nameKey,"行车数据");
            head.put(KeyEnum.statusKey,ExamnationStatusEnum.Unknown);
            engineCondition.put(KeyEnum.groupKey,head);
            List list=new ArrayList<>();
            list.add(map);
            engineCondition.put(KeyEnum.childKey,list);
            dataList.add(engineCondition);
            carExamnationExpandableListAdapter.updateData(dataList);
            this.expandGroup(dataList.size());


        }else {
            engineCondition=(Map)dataList.get(6);
            List list=(List)engineCondition.get(KeyEnum.childKey);
            list.add(map);
            carExamnationExpandableListAdapter.updateData(dataList);

        }

        ExamnationStatusEnum status=(ExamnationStatusEnum)map.get(KeyEnum.statusKey);
        driveDatas=driveDatas+status.getValue()+",";


    }

    private void expandGroup(int groupCount){
        for (int i=0; i<groupCount; i++) {
            expandablelistview.expandGroup(i);
        }


    }

    private void initDataList(){

        dataList=new ArrayList();
        Map map=new HashMap();
        Map map1=new HashMap();
        map1.put(KeyEnum.nameKey,"故障扫描");
        map1.put(KeyEnum.statusKey,ExamnationStatusEnum.Unknown);
        map.put(KeyEnum.groupKey,map1);
        dataList.add(map);

    }


    private void autoScrollBottom(){
        if (expandablelistview.isStackFromBottom()) {
            expandablelistview.setStackFromBottom(false);
        }
        expandablelistview.setStackFromBottom(true);
    }

    private void autoScrollTop(){
        if (!expandablelistview.isStackFromBottom()) {
            expandablelistview.setStackFromBottom(true);
        }
        expandablelistview.setStackFromBottom(false);
    }

}
