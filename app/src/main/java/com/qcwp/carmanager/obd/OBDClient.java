package com.qcwp.carmanager.obd;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.DrivingCustomEnum;
import com.qcwp.carmanager.enumeration.ExamnationStatusEnum;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.OBDConnectType;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.DrivingCustomModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

import static com.blankj.utilcode.util.ThreadPoolUtils.FixedThread;
import static com.qcwp.carmanager.broadcast.MessageEvent.MessageEventType.CarCheck_end;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypeClearErr;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypeTest;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypeTijian;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypedrive;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectSuccess;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectingWithSP;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeDisconnection;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeHaveBinded;
import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by qyh on 2017/6/6.
 */

public class OBDClient {

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public double getEngineRpm() {
        return engineRpm;
    }

    public double getFuelPressure() {
        return fuelPressure;
    }


    public double getAvgVehicleSpeed() {
        return avgVehicleSpeed;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public double getAvgOilConsume() {
        return avgOilConsume;
    }

    public double getCurrentOilConsume() {
        return currentOilConsume;
    }

    public double getDist() {
        return dist;
    }

    public double getIntakeTemp() {
        return intakeTemp;
    }

    public double getTotalMileage() {

        return totalMileage;
    }

    public void startExamnation() {
        this.loadDataType = dataTypeTijian;
    }


    public void clearFaultCode() {
        this.loadDataType = dataTypeClearErr;
    }



    public void startTest() {
        this.loadDataType = dataTypeTest;
//        SensorsService.testing(0.01);
    }

    public void stopTest() {
        this.loadDataType = dataTypedrive;
    }

    public double getEngineCoolant() {
        return engineCoolant;
    }

    private OBDConnectStateEnum connectStatus;//连接状态
    private LoadDataTypeEnum loadDataType;//车辆处于何种状态
    private String vinCode;//车辆的vincode码
    private String spValue;//协议
    private double vehicleSpeed;//当前速度
    private String isGetVehicleSpeed;//是否初始化成功

    public LoadDataTypeEnum getLoadDataType() {
        return loadDataType;
    }

    private double engineRpm;//转速
    private double fuelPressure;//气压
    private double totalTime;//行驶时间
    private double avgVehicleSpeed;//平均速度
    private double avgOilConsume;//平均油耗
    private double currentOilConsume;//当前油耗
    private double dist;//当前里程
    private double controlModuleVoltage;//控制模块电压
    private double intakeTemp;//环境空气温度(-40~214)
    private double engineCoolant;//发动机冷却液温度(-40~214)
    private double totalMileage;//总里程

    public double getTotalTime() {
        return totalTime;
    }



    private int readyToExamination;//连接状态
    private double bfImpactVehicleSpeed; //碰撞前
    private double afImapactVehicleSpeed; //碰撞后


    private int accelerCount, decelerCount, overspeedCount, hypervelocityReminder;
    private long onlyFlag;


    private CarVinStatisticModel carVinStatisticModel;
    private SingleCarVinStatisticModel singleCarVinStatisticModel;
    private  TravelSummaryModel travelSummaryModel;

    private CarInfoModel carInfoModel;
    private List<Double> engineRpmArray, vehicleSpeedArray;
    private List<String>  travelArray;

    private int countOfOBDData, originalTimeCount;
    private double originalDistCount, originalFuelCount, originalCarMileage;
    private DaoSession daoSession;
    private String phoneModel;
    private ThreadPoolUtils threadPool;
    private  ScheduledFuture scheduledFuture;

    public OBDConnectStateEnum getConnectStatus() {
        return connectStatus;
    }

    private String startTime;

    public String getStartTime() {
        return startTime;
    }

    public String getVinCode() {
        return vinCode;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public CarVinStatisticModel getCarVinStatisticModel() {
        return carVinStatisticModel;
    }

    public SingleCarVinStatisticModel getSingleCarVinStatisticModel() {
        return singleCarVinStatisticModel;
    }

    public TravelSummaryModel getTravelSummaryModel() {
        return travelSummaryModel;
    }

    public void setOnlyFlag(long onlyFlag) {

        this.onlyFlag = onlyFlag;
    }


    /**
     * * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private OBDClient() {

        daoSession = APP.getInstance().getDaoInstant();
        EventBus.getDefault().register(OBDClient.this);

    }


    private static OBDClient INSTANCE;

    // 提供一个全局的静态方法
    public static OBDClient getDefaultClien() {
        if (INSTANCE == null) {
            synchronized (OBDClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OBDClient();
                }
            }
        }
        return INSTANCE;
    }


    private void stopOBDClient(){
        connectStatus=connectTypeDisconnection;
        loadDataType=LoadDataTypeEnum.dataTypeDisConnected;
        scheduledFuture.cancel(true);
        scheduledFuture=null;
        threadPool.shutDown();
        threadPool=null;


    }

    public void startOBDClient() {


        threadPool = new ThreadPoolUtils(FixedThread, 2);
        threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                OBDConnectService obdConnectService = OBDConnectService.getInstance();
                obdConnectService.setConectOBDListener(new OBDConnectService.ConectOBDListener() {
                    @Override
                    public void completeConect(Boolean success, String message) {
                        connectStatus = connectTypeConnectingWithSP;
                        Boolean blockSuccess = success;
                        String blockMessage = message;
                        if (success) {
                            if (EmptyUtils.isNotEmpty(OBDClient.this.detectProtocol())) {

                                String data = OBDConnectService.getData(SensorsService.VIN_PIDS);
                                Print.d("OBDConnectService", data + "----");

                                SensorsService.SensorsDataHandler(data, SensorsService.VIN_PIDS);
                                vinCode = SensorsService.GetVinCode(data);

                                connectStatus = connectTypeConnectSuccess;

                                MessageEvent messageEvent = new MessageEvent(MessageEvent.MessageEventType.OBDConnectSuccess, message);
                                EventBus.getDefault().post(messageEvent);

                                blockMessage = vinCode;
                                blockSuccess = true;


                            } else {
                                blockMessage = "不是通用协议";
                                blockSuccess = false;

                            }
                        }

                        final Boolean finalBlockSuccess = blockSuccess;
                        final String finalBlockMessage = blockMessage;
                        MyActivityManager.getInstance().getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (readVinCodeCompleteListener != null) {
                                    readVinCodeCompleteListener.connectComplete(finalBlockSuccess, finalBlockMessage);
                                }
                            }
                        });

                        if (!blockSuccess) {
                            threadPool.shutDownNow();
                        }

                    }
                });
                MySharedPreferences mySharedPreferences=new MySharedPreferences(APP.getInstance());
                int value=mySharedPreferences.getInt(KeyEnum.typeKey,OBDConnectType.WIFI.getValue());
                OBDConnectType type=OBDConnectType.fromInteger(value);
                obdConnectService.setConnectType(type);
                obdConnectService.startConnectService();


                return null;
            }
        });


    }

    private String detectProtocol() {

        String SP = new MySharedPreferences(APP.getInstance()).getString(KeyEnum.currentOBDrotocol, "");

        if (EmptyUtils.isNotEmpty(SP)) {
            String data = OBDConnectService.getData(SP);
            if (this.checkProtocolData(data)) {
                return SP;
            }
        }

        //1.用协议去判断连接状态，成功返回：OK（ATDPN 例外，返回 0），失败返回的数据目前未知
        //2.用0100去拿数据，成功返回：BUS INIT: OK和(4100或41 00)  失败返回 BUS INIT: ... ERROR、NO DATA、UNABLE TO CONNECT
        //3.必须同时满足1和2才能正常连接
        String data;
        //    NSLog(@"SP = %@",SP);
        String str1 = OBDConnectService.getData("ATZ");//这两个必须有，不然会出现连上了却读不到数据的情况，（实测过的）
        String str2 = OBDConnectService.getData("ATE0");
        String str3 = OBDConnectService.getData("ATS0");


        if (this.checkProtocolData("OK")) {
            return "ATZ";
        }


        String sp_Value;
        String[] spArr = APP.getInstance().getResources().getStringArray(R.array.OBDProtocol);

        for (int i = 0; i < spArr.length; i++) {
            if (connectStatus == connectTypeConnectingWithSP) {
                sp_Value = spArr[i];
                if (this.checkProtocolData(OBDConnectService.getData(sp_Value))) {
                    MySharedPreferences mySharedPreferences = new MySharedPreferences(APP.getInstance());
                    mySharedPreferences.setString(KeyEnum.currentOBDrotocol, sp_Value);
                    return sp_Value;
                }
            }
        }
        return null;
    }

    private Boolean checkProtocolData(String data) {

        if (data.toUpperCase().contains("OK")) {
            data = OBDConnectService.getData("0100");

            if (data.contains("4100") || data.contains("41 00")) {
                //当车辆还未初始化完成时，再初始化一次
                while (data.contains("ERROR")) {
                    data = OBDConnectService.getData("0100");
                }


                return true;
            }

        }

        return false;
    }

    private ReadVinCodeCompleteListener readVinCodeCompleteListener;

    public interface ReadVinCodeCompleteListener {
        void connectComplete(Boolean success, String message);
    }

    public void setReadVinCodeCompleteListener(ReadVinCodeCompleteListener readVinCodeCompleteListener) {
        this.readVinCodeCompleteListener = readVinCodeCompleteListener;
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()) {
            case CarBlindSuccess:
                OBDClient.this.initData();
                break;
            case OBDLostDisconnection:
                stopOBDClient();
                break;
        }

    }


    //初始化
    private void initData() {

        accelerCount = 0;
        decelerCount = 0;
        overspeedCount = 0;
        hypervelocityReminder = 0;


        engineRpmArray = new ArrayList();
        vehicleSpeedArray = new ArrayList();
        travelArray = new ArrayList();


        carVinStatisticModel = daoSession.queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();
        if (carVinStatisticModel == null) {
            carVinStatisticModel = new CarVinStatisticModel();
            carVinStatisticModel.setId(onlyFlag);
        }
        carVinStatisticModel.setVinCode(vinCode);
        daoSession.insertOrReplace(carVinStatisticModel);

        singleCarVinStatisticModel = daoSession.queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();
        if (singleCarVinStatisticModel == null) {
            singleCarVinStatisticModel = new SingleCarVinStatisticModel();
            singleCarVinStatisticModel.setOnlyFlag(onlyFlag);
        }
        singleCarVinStatisticModel.setVinCode(vinCode);
        singleCarVinStatisticModel.setTimeCount(0);
        singleCarVinStatisticModel.setDistCount(0);
        singleCarVinStatisticModel.setFuelCount(0);
        daoSession.insertOrReplace(singleCarVinStatisticModel);


        carInfoModel = daoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(vinCode)).unique();

        countOfOBDData = 0;
        originalDistCount = carVinStatisticModel.getDistCount();
        originalTimeCount = carVinStatisticModel.getTimeCount();
        originalFuelCount = carVinStatisticModel.getFuelCount();


        originalCarMileage = carInfoModel.getTotalMileage();

        connectStatus = connectTypeHaveBinded;

        phoneModel=android.os.Build.MODEL==null?"未知":android.os.Build.MODEL;
        this.getCarData();

    }

    private String getCurrentSummryData() {


        double showUsaLiter = SensorsService.showUsaLiter();
        double maxVehicleSpeed = SensorsService.maxVehicleSpeed();
        double avgVehicleSpeed = SensorsService.vehicleSpeedAve();
        double maxEngineRpm = SensorsService.maxEngineRpm();
        double avgEngineRpm = SensorsService.engineRpmAve();
        double maxAcceleratorPedalPosition = SensorsService.maxAcceleratorPedalPosition();
        double avgAcceleratorPedalPosition = SensorsService.acceleratorPedalPositionAvg();
        double cureentDist = SensorsService.dist();
        double carDist = originalCarMileage + cureentDist;
        int travelTime = SensorsService.travelTime();
        int stopTime = SensorsService.stopTime();
        String startDate = startTime;
        String endDate = TimeUtils.getNowString();


        return "@OnlyFlag=" + onlyFlag + "&VinId=" + 0 + "&UserId=" + UserData.getInstance().getUserId() + "&LiterAvg=" + showUsaLiter + "&MaxVehicleSpeed=" + maxVehicleSpeed + "&VehicleSpeedAve=" + avgVehicleSpeed + "&MaxEngineRpm=" + maxEngineRpm + "&EngineRpmAve=" + avgEngineRpm + "&MaxAcceleratorPedalPosition=" + maxAcceleratorPedalPosition + "&AcceleratorPedalPositionAve=" + avgAcceleratorPedalPosition + "&StartDate=" + startDate + "&EndDate=" + endDate + "&CarDist=" + carDist + "&Dist=" + cureentDist + "&CountSecond=" + travelTime + "&StopSecond=" + stopTime +"&phoneBrand"+phoneModel+"\n";

    }

    //计算和保存

    private void calculateTraveData() {
        Print.d("calculateTraveData","----------");
        if (connectStatus == connectTypeHaveBinded) {
            SensorsService.calculateTraveData();
            if (((int) SensorsService.totalTime()) % 2 != 0) {
                //是否急加速
                OBDClient.this.isHardAcceleration();
            }

            //是否急刹车
            OBDClient.this.isBrakes();


            if (loadDataType == dataTypedrive) {
                if (SensorsService.vehicleImpact() == 1) {
                    bfImpactVehicleSpeed = SensorsService.bfImpactVehicleSpeed();
                    afImapactVehicleSpeed = SensorsService.afImapactVehicleSpeed();
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarCrash, null));
                }


                OBDClient.this.saveOBDData();

            }
        }


    }

    private void carCheckSome(JSONObject jsonObject) {

        String info = jsonObject.optString(KeyEnum.pidInfoKey);
        String pid = jsonObject.optString(KeyEnum.pidKey);
        OBDConnectService.getData(pid);//没有什么用
        EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarCheck_some, info));
    }

    //体检
    private void healthExamination() {

        JSONArray jsonArray = CommonUtils.getJSONArrayFromText("AllPids.json");
        Integer index = 0;

        for (; index < 3; index++) {

            JSONObject jsonObject = jsonArray.optJSONObject(index);
            this.carCheckSome(jsonObject);
        }


        //获取故障码-体检
        SensorsService.Car_CheckUp_init();
        String data = OBDConnectService.getData("03");
        //    NSLog(@"故障码   %@",data);
        SensorsService.DTC_CheckUp(data);
        Resources mResource=APP.getInstance().getResources();
        String[] titleArray =mResource.getStringArray(R.array.FaultCodeType);
        for (int i = 0; i < titleArray.length; i++) {
            if (loadDataType != dataTypeTijian) return;
            List<String> valueList = new ArrayList<>();
            if (i == 0) valueList = SensorsService.powertrainDTCList();
            else if (i == 1) valueList = SensorsService.chassisDTCList();
            else if (i == 2) valueList = SensorsService.bodyDTCList();
            else if (i == 3) valueList = SensorsService.networkDTCList();


            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setType(MessageEvent.MessageEventType.CarCheck_faultCode);
            Map map = new HashMap();
            map.put(KeyEnum.nameKey, titleArray[i]);
            map.put(KeyEnum.statusKey, valueList);
            messageEvent.setData(map);
            EventBus.getDefault().post(messageEvent);



            for (int j = 0; j < 4; j++) {
                JSONObject jsonObject = jsonArray.optJSONObject(index);
                this.carCheckSome(jsonObject);
                index++;
            }

        }




//    //获取汽车工况-体检
        data = OBDConnectService.getData("0141");
        int[] engineConditions = SensorsService.EngineBehaviour_CheckUp(data);
        titleArray = mResource.getStringArray(R.array.EngineConditions);
        for (int j = 0; j < 6; j++) {
            if (loadDataType != dataTypeTijian) return;
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setType(MessageEvent.MessageEventType.CarCheck_engineCondition);
            Map map = new HashMap();
            map.put(KeyEnum.nameKey, titleArray[j]);
            map.put(KeyEnum.statusKey, ExamnationStatusEnum.fromInteger(engineConditions[j]));
            messageEvent.setData(map);
            EventBus.getDefault().post(messageEvent);


            for (int i = 0; i < 4; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(index);
                this.carCheckSome(jsonObject);
                index++;
            }
        }


        //获取行车数据-体检
//        String examnation_pidStr = SensorsService.CarCheckUp_Pid_List();
//        String[] examnationPidArray = examnation_pidStr.split(",");

        JSONArray driveDataPidArray = CommonUtils.getJSONArrayFromText("CarCheckDriveDataPids.json");


        for (int i=0;i<driveDataPidArray.length();i++) {
            if (loadDataType != dataTypeTijian) return;
            for (int j = 0; j < 2; j++) {
                JSONObject jsonObject = jsonArray.optJSONObject(index);
                this.carCheckSome(jsonObject);
                index++;
            }


            JSONObject jsonObject=driveDataPidArray.optJSONObject(i);

            String pid=jsonObject.optString(KeyEnum.pidKey);
            data = OBDConnectService.getData(pid);

            int drivingDataStatus = SensorsService.DrivingData_CheckUp(data, pid);


            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setType(MessageEvent.MessageEventType.CarCheck_driveData);
            Map map = new HashMap();
            map.put(KeyEnum.nameKey, jsonObject.optString(KeyEnum.pidInfoKey));
            map.put(KeyEnum.statusKey, ExamnationStatusEnum.fromInteger(drivingDataStatus));
            messageEvent.setData(map);
            EventBus.getDefault().post(messageEvent);
        }


        Integer  carCheckUpScore = SensorsService.Car_CheckUp_score();
        Integer DtcCount = SensorsService.DtcCount();
        Integer  DrivingDataUnusualCount = SensorsService.DrivingDataUnusualCount();

        MessageEvent messageEvent=new MessageEvent();
        messageEvent.setType(CarCheck_end);
        Map map=new HashMap();
        map.put("carCheckUpScore",carCheckUpScore);
        map.put("DtcCount",DtcCount);
        map.put("DrivingDataUnusualCount",DrivingDataUnusualCount);
        messageEvent.setData(map);

        EventBus.getDefault().post(messageEvent);


    }

    @SuppressLint("DefaultLocale")
    private String readTravelDataWith(String pid, String data) {

        data = data.replace("\r", "");
        data = data.replace("\n", "");
        double temp = originalCarMileage;
        long summryOnlyFlag = onlyFlag;

        vehicleSpeed = SensorsService.vehicleSpeed();
        engineRpm = SensorsService.engineRpm();
        fuelPressure = SensorsService.fuelPressure();
        totalTime = SensorsService.totalTime();
        avgVehicleSpeed = SensorsService.vehicleSpeedAve();
        avgOilConsume = SensorsService.showUsaLiter();
        currentOilConsume = SensorsService.instantFuel();
        dist = SensorsService.dist();
        controlModuleVoltage = SensorsService.controlModuleVoltage();
        intakeTemp = SensorsService.intakeTemp();
        engineCoolant = SensorsService.engineCoolant();
        totalMileage = originalCarMileage + SensorsService.dist();

        EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.Driving, null));

        return String.format("?%.0f&%.0f&%s&%.4f&%.4f&%.0f&%.0f&%.0f&%.0f&%.0f&%.4f&%.0f&%.0f&%.0f&%.0f&%.2f&%.4f&%d&%f&%f&%s&%s\n", SensorsService.acceleratorPedalPosition(),//1
                SensorsService.airFlowRate(),//2
                TimeUtils.getNowString(),//3
                SensorsService.dist(),//4
                temp + SensorsService.dist(),//5
                SensorsService.engineCoolant(),//6
                SensorsService.engineLoadValue(),//7
                SensorsService.engineRpm(),//8
                SensorsService.fuelPressure(),//9
                SensorsService.intakeTemp(),//10
                SensorsService.showUsaLiter(),//11
                SensorsService.lTFuelTrim(),//12
                SensorsService.map(),//13
                SensorsService.sTFuelTrim(),//14
                SensorsService.timingAdvance(),//15
                SensorsService.vehicleSpeed(),//16
                SensorsService.usaLiter(),//17
                summryOnlyFlag,//18
                SensorsService.idlingFuel(),//19
                SensorsService.customIdlingFuel(),//20
                pid,//21
                data);


    }

    //是否超车
    private void postHypervelocity() {

        // TODO: 2017/6/23
    }

    private List<String> getDrivePidWithOther(List otherList) {
        String str = SensorsService.MAIN_ACTIVITY_PIDS();
        List<String> list = new ArrayList<>(Arrays.asList(str.split(",")));
        list.addAll(otherList);
        return list;
    }

    //发指令
    private void getCarData() {


        threadPool.submit(new Runnable() {
            @Override
            public void run() {

                Print.d("Runnable", "getCarData=========");
                String[] acceleratorPids = APP.getInstance().getResources().getStringArray(R.array.AcceleratorPids);
                for (String pid : acceleratorPids) {
                    String tmpData = OBDConnectService.getData(pid);
                    SensorsService.setAccelerator(tmpData, pid);
                }

                String str = SensorsService.MAIN_ACTIVITY_PIDS();

                String[] mainPIDS = str.split(",");

                List<String> mainPIDList = new ArrayList<>(Arrays.asList(mainPIDS));


                for (String pid : mainPIDList) {
                    String tmpData = OBDConnectService.getData(pid);
                    SensorsService.SensorsDataHandler(tmpData, pid);
                }


                //添加行驶记录的起始状态
                travelSummaryModel = new TravelSummaryModel();
                travelSummaryModel.setSummryData(OBDClient.this.getCurrentSummryData());
                travelSummaryModel.setCarNumber(carInfoModel.getCarNumber());
                travelSummaryModel.setOnlyFlag(onlyFlag);
                travelSummaryModel.setUserName(UserData.getInstance().getUserName());
                travelSummaryModel.setVinCode(vinCode);
                travelSummaryModel.setUploadFlag(UploadStatusEnum.NotUpload);
                travelSummaryModel.setStartDate(startTime);
                travelSummaryModel.setEndDate(TimeUtils.getNowString());
                daoSession.insert(travelSummaryModel);




                scheduledFuture=threadPool.scheduleWithFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        OBDClient.this.calculateTraveData();

                    }
                }, 1,1, SECONDS);





                String[] ALL_DRIVE_PIDs = APP.getInstance().getResources().getStringArray(R.array.ALL_PID_DRIVE);
                List<String> allDrivePidList = new ArrayList<>(Arrays.asList(ALL_DRIVE_PIDs));


                allDrivePidList.removeAll(mainPIDList);



                int location = 0;

                loadDataType=dataTypedrive;
                while (true) {

                    if (connectStatus == connectTypeHaveBinded) {
                        switch (loadDataType) {
                            case dataTypeTest:

                                String tmpData = OBDConnectService.getData("010D");
                                SensorsService.SensorsDataHandler(tmpData, "010D");
                                vehicleSpeed=SensorsService.vehicleSpeed();
                                EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarTest,String.valueOf(vehicleSpeed)));
                                break;
                            case dataTypeTijian:
                                OBDClient.this.healthExamination();
                                loadDataType = dataTypedrive;
                                break;
                            case dataTypedrive: {

                                String minorPid="";
                                if (allDrivePidList.size() > 0) {
                                    minorPid=allDrivePidList.get(location);
                                    location++;
                                    if (location == allDrivePidList.size()) {
                                        location = 0;
                                    }
                                }

                                List<String> pids = new ArrayList<>(mainPIDList);
                                pids.add(minorPid);


//                                OBDClient.this.getDrivePidWithOther(allDrivePidList.subList(location, location + length));
                                for (int i = 0; i < pids.size(); i++) {
                                    if (connectStatus == connectTypeHaveBinded) {
                                        String pid = pids.get(i);
                                        String data = OBDConnectService.getData(pid);
                                        SensorsService.SensorsDataHandler(data, pid);
                                        Print.d("SensorsDataHandler",pid+"=="+data);
                                        if (data.length() > 0) {
                                            if (i >= mainPIDList.size()) {
                                                travelArray.add(OBDClient.this.readTravelDataWith(pid, data));
                                            }


                                            if (pid.equals("010D")) {
                                                OBDClient.this.postHypervelocity();
                                            }
                                        }
                                    }
                                }
                            }


                            break;
                            case dataTypeClearErr: {

                                OBDConnectService.getData("04");
                                loadDataType = dataTypeTijian;
                            }
                            break;
                            default:
                                break;
                        }
                    } else {
                        try {
                            sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }


            }
        });


    }

    private double lastEngineRpm=0;

    //是否急加速
    private boolean isHardAcceleration() {


        double engineGap =  engineRpm-lastEngineRpm;
        lastEngineRpm = engineRpm;
        if (engineGap > 1800 && vehicleSpeed > 0) {


            addDriveCustomRecord(DrivingCustomEnum.Acceleration);



            return true;
        }

        return false;

    }

    private double lastVehicleSpeed=0;
    //是否急减速
    private boolean isBrakes() {


        double speedGap= lastVehicleSpeed-vehicleSpeed;
        lastVehicleSpeed=vehicleSpeed;

        if (speedGap > 10) {
            addDriveCustomRecord(DrivingCustomEnum.Deceleration);
            return true;
        }

        return false;

    }


    private void addDriveCustomRecord(DrivingCustomEnum type){


        switch (type){
            case Acceleration:
                accelerCount++;
                carVinStatisticModel.setAccelerCount(carVinStatisticModel.getAccelerCount()+1);
                singleCarVinStatisticModel.setAccelerCount(accelerCount);
                travelSummaryModel.setAccelerCount(accelerCount);
                break;
            case Deceleration:
                decelerCount++;
                carVinStatisticModel.setDecelerCount(carVinStatisticModel.getDecelerCount()+1);
                singleCarVinStatisticModel.setDecelerCount(decelerCount);
                travelSummaryModel.setDecelerCount(decelerCount);

                break;
            case Overdrive:
                break;
        }


        daoSession.update(carVinStatisticModel);
        daoSession.update(singleCarVinStatisticModel);
        daoSession.update(travelSummaryModel);

        DrivingCustomModel drivingCustomModel=new DrivingCustomModel();
        drivingCustomModel.setVinCode(vinCode);
        drivingCustomModel.setStartDate(startTime);
        drivingCustomModel.setType(type);
        drivingCustomModel.setCreateDate(TimeUtils.getNowString());
        daoSession.insert(drivingCustomModel);

    }


    /**
     * 保存数据
     */
    private void saveOBDData() {

        if (travelArray.size() > 60) {


            List<String> tempTravelArray = new ArrayList();
            tempTravelArray.addAll(travelArray);
            travelArray.clear();
            //勋章

            //距离和时间的值是设备自己累积的，所以这两个数值逐渐增大
            double driveDist = SensorsService.dist();//走了多远距离
            int driveTime = (int) SensorsService.totalTime();//走了多久时间


            //车辆统计数据

            double showfuelCount = SensorsService.showUsaLiter();//行车平均油耗 L/100公里

            double fuelCount = showfuelCount * driveDist / 100;



            if (carVinStatisticModel.getMaxTime() < driveTime) {
                carVinStatisticModel.setMaxTime(driveTime);
            }

            if (carVinStatisticModel.getMaxDist() < driveDist) {
                carVinStatisticModel.setMaxDist(driveDist);
            }


            carVinStatisticModel.setDistCount(originalDistCount + driveDist);
            carVinStatisticModel.setTimeCount(originalTimeCount + driveTime);

            carVinStatisticModel.setFuelCount(originalFuelCount + fuelCount);

            double totalAvgFuel=(originalFuelCount + fuelCount)/(originalDistCount + driveDist)*100;

            carVinStatisticModel.setAvgFuel(totalAvgFuel);

            daoSession.update(carVinStatisticModel);


            //车辆原始数据
            carInfoModel.setTotalMileage(originalCarMileage + driveDist);

            daoSession.update(carInfoModel);
            //保存勋章数据



            singleCarVinStatisticModel.setDistCount(driveDist);
            singleCarVinStatisticModel.setTimeCount(driveTime);
            singleCarVinStatisticModel.setFuelCount(fuelCount);
            singleCarVinStatisticModel.setVinCode(vinCode);
            singleCarVinStatisticModel.setAvgFuel(showfuelCount);
            singleCarVinStatisticModel.setAvgVehicleSpeed(SensorsService.vehicleSpeedAve());
            daoSession.update(singleCarVinStatisticModel);



            travelSummaryModel.setMileage(driveDist);
            travelSummaryModel.setDriveTime(driveTime);
            travelSummaryModel.setAverageSpeed(SensorsService.vehicleSpeedAve());
            travelSummaryModel.setAverageOilConsume(showfuelCount);
            travelSummaryModel.setSummryData(OBDClient.this.getCurrentSummryData());
            travelSummaryModel.setEndDate(TimeUtils.getNowString());
            daoSession.update(travelSummaryModel);

            for (String data : tempTravelArray) {
                TravelDataModel travelDataModel = new TravelDataModel();
                travelDataModel.setOnlyFlag(onlyFlag);
                travelDataModel.setVinCode(vinCode);
                travelDataModel.setTravelData(data);
                travelDataModel.setUserName(UserData.getInstance().getUserName());
                travelDataModel.setUploadFlag(UploadStatusEnum.NotUpload);
                daoSession.insert(travelDataModel);

            }


            EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarDataUpdate, vinCode));

        }


    }


}
