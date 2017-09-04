package com.qcwp.carmanager.obd;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.CarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.SingleCarVinStatisticModelDao;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelDataModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static com.blankj.utilcode.util.ThreadPoolUtils.CachedThread;
import static com.blankj.utilcode.util.ThreadPoolUtils.FixedThread;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypeTijian;
import static com.qcwp.carmanager.enumeration.LoadDataTypeEnum.dataTypedrive;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectSuccess;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectingWithSP;
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
    public double getEngineCoolant() {
        return engineCoolant;
    }

    private OBDConnectStateEnum connectStatus;//连接状态
    private LoadDataTypeEnum loadDataType;//车辆处于何种状态
    private String vinCode;//车辆的vincode码
    private String spValue;//协议
    private double vehicleSpeed;//当前速度
    private String isGetVehicleSpeed;//是否初始化成功
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

    private String carCheckUpPidDescriptionList;
    private String carCheckUpUnitsList;



    private String carCheckUpPidList;
    private int DtcCount;
    private int DrivingDataUnusualCount;
    private int carCheckUpScore;
    private int readyToExamination;//连接状态
    private double bfImpactVehicleSpeed; //碰撞前
    private double afImapactVehicleSpeed; //碰撞后


    private int accelerCount, decelerCount, overspeedCount, hypervelocityReminder;
    private long onlyFlag;


    private CarInfoModel carInfoModel;
    private List<String> engineRpmArray, vehicleSpeedArray, travelArray;

    private int countOfOBDData, originalTimeCount;
    private double originalDistCount, originalFuelCount, originalCarMileage;
    private DaoSession daoSession;
    private ThreadPoolUtils threadPool;

    public  OBDConnectStateEnum getConnectStatus() {
        return connectStatus;
    }

    public String startTime;


    public String getVinCode() {
        return vinCode;
    }



    public void setStartTime(String startTime) {
        this.startTime = startTime;
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


    public void startOBDClient() {


        threadPool = new ThreadPoolUtils(FixedThread, 2);
        threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                BlueteethService blueteethService =  BlueteethService.getDefaultBluetoothService();
                blueteethService.setConectOBDListener(new BlueteethService.ConectOBDListener() {
                    @Override
                    public void completeConect(Boolean success, String message) {
                        connectStatus = connectTypeConnectingWithSP;
                        Boolean blockSuccess = success;
                        String blockMessage = message;
                        if (success) {
                            if (EmptyUtils.isNotEmpty(OBDClient.this.detectProtocol())) {

                                String data = BlueteethService.getData(SensorsService.VIN_PIDS);
                                Print.d("BlueteethService", data + "----");

                                SensorsService.SensorsDataHandler(data, SensorsService.VIN_PIDS);
                                vinCode = SensorsService.GetVinCode(data);

                                connectStatus = connectTypeConnectSuccess;

                                MessageEvent messageEvent=new MessageEvent(MessageEvent.MessageEventType.OBDConnectSuccess,message);
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

                        if (!blockSuccess){
                            threadPool.shutDownNow();
                        }

                    }
                });
                blueteethService.startBluetoothService();



                return null;
            }
        });


    }

    private String detectProtocol() {

        String SP = new MySharedPreferences(APP.getInstance()).getString(KeyEnum.currentOBDrotocol, "");

        if (EmptyUtils.isNotEmpty(SP)) {
            String data = BlueteethService.getData(SP);
            if (this.checkProtocolData(data)) {
                return SP;
            }
        }

        //1.用协议去判断连接状态，成功返回：OK（ATDPN 例外，返回 0），失败返回的数据目前未知
        //2.用0100去拿数据，成功返回：BUS INIT: OK和(4100或41 00)  失败返回 BUS INIT: ... ERROR、NO DATA、UNABLE TO CONNECT
        //3.必须同时满足1和2才能正常连接
        String data;
        //    NSLog(@"SP = %@",SP);
        String str1 = BlueteethService.getData("ATZ");//这两个必须有，不然会出现连上了却读不到数据的情况，（实测过的）
        String str2 = BlueteethService.getData("ATE0");
        String str3 = BlueteethService.getData("ATS0");


        if (this.checkProtocolData("OK")) {
            return "ATZ";
        }


        String sp_Value;
        String[] spArr = APP.getInstance().getResources().getStringArray(R.array.OBDProtocol);

        for (int i = 0; i < spArr.length; i++) {
            if (connectStatus == connectTypeConnectingWithSP) {
                sp_Value = spArr[i];
                if (this.checkProtocolData(BlueteethService.getData(sp_Value))) {
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
            data = BlueteethService.getData("0100");

            if (data.contains("4100") || data.contains("41 00")) {
                //当车辆还未初始化完成时，再初始化一次
                while (data.contains("ERROR")) {
                    data = BlueteethService.getData("0100");
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
    public void setReadVinCodeCompleteListener(ReadVinCodeCompleteListener readVinCodeCompleteListener){
        this.readVinCodeCompleteListener=readVinCodeCompleteListener;
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()){
            case CarBlindSuccess:
                OBDClient.this.initData();
                break;
            case OBDLostDisconnection:
                //重连
                connectStatus=OBDConnectStateEnum.connectTypeDisconnectionWithOBD;
                threadPool.shutDownNow();
                vinCode=null;
                OBDClient.this.startOBDClient();
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


       CarVinStatisticModel carVinStatisticModel = daoSession.queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();
        if (carVinStatisticModel == null) {
            carVinStatisticModel = new CarVinStatisticModel();
        }
        carVinStatisticModel.setVinCode(vinCode);
        daoSession.insertOrReplace(carVinStatisticModel);

        SingleCarVinStatisticModel singleCarVinStatisticModel =daoSession.queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();
        if (singleCarVinStatisticModel == null) {
            singleCarVinStatisticModel = new SingleCarVinStatisticModel();
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

        this.getCarData();

    }

    private String getCurrentSummryData() {


        double showUsaLiter=SensorsService.showUsaLiter();
        double maxVehicleSpeed=SensorsService.maxVehicleSpeed();
        double avgVehicleSpeed=SensorsService.vehicleSpeedAve();
        double maxEngineRpm=SensorsService.maxEngineRpm();
        double avgEngineRpm=SensorsService.engineRpmAve();
        double maxAcceleratorPedalPosition=SensorsService.maxAcceleratorPedalPosition();
        double avgAcceleratorPedalPosition=SensorsService.acceleratorPedalPositionAvg();
        double cureentDist=SensorsService.dist();
        double carDist=originalCarMileage+cureentDist;
        int travelTime=SensorsService.travelTime();
        int stopTime=SensorsService.stopTime();
        String startDate=startTime;
        String endDate=TimeUtils.getNowString();


        return  "@OnlyFlag="+onlyFlag+"&VinId="+0+"&UserId="+UserData.getInstance().getUserId()+"&LiterAvg="+showUsaLiter+"&MaxVehicleSpeed="+maxVehicleSpeed+"&VehicleSpeedAve="+avgVehicleSpeed+"&MaxEngineRpm="+maxEngineRpm+"&EngineRpmAve="+avgEngineRpm+"&MaxAcceleratorPedalPosition="+maxAcceleratorPedalPosition+"&AcceleratorPedalPositionAve="+avgAcceleratorPedalPosition+"&StartDate="+startDate+"&EndDate="+endDate+"&CarDist="+carDist+"&Dist="+cureentDist+"&CountSecond="+travelTime+"&StopSecond="+stopTime+"\n";

    }

    //计算和保存

    private void calculateTraveData() {
        if (connectStatus == connectTypeHaveBinded)
        {
            SensorsService.calculateTraveData();
            if (((int)SensorsService.totalTime()) % 2!=0)
            {
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

    //体检
    private void healthExamination() {

        // TODO: 2017/6/23
    }

    @SuppressLint("DefaultLocale")
    private String readTravelDataWith(String pid, String data) {

        data =data.replace("\r","");
        data=data.replace("\n","");
        double temp = originalCarMileage;
        long summryOnlyFlag=onlyFlag;

        vehicleSpeed= SensorsService.vehicleSpeed();
        Print.d("readTravelDataWith","vehicleSpeed===="+vehicleSpeed);
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
    totalMileage =originalCarMileage+SensorsService.dist();
    carCheckUpPidDescriptionList= SensorsService.CarCheckUp_PidDescription_list();
    carCheckUpUnitsList=SensorsService.CarCheckUp_Units_list();
    carCheckUpPidList=SensorsService.CarCheckUp_Pid_List();

     EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.Driving,null));

     return   String.format("?%.0f&%.0f&%s&%.4f&%.4f&%.0f&%.0f&%.0f&%.0f&%.0f&%.4f&%.0f&%.0f&%.0f&%.0f&%.2f&%.4f&%d&%f&%f&%s&%s\n",SensorsService.acceleratorPedalPosition(),//1
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
                    String tmpData = BlueteethService.getData(pid);
                    SensorsService.setAccelerator(tmpData, pid);
                }

                String str = SensorsService.MAIN_ACTIVITY_PIDS();

                String[] mainPIDS = str.split(",");

                List<String> mainPIDList = new ArrayList<>(Arrays.asList(mainPIDS));



                for (String pid : mainPIDList) {
                    String tmpData = BlueteethService.getData(pid);
                    SensorsService.SensorsDataHandler(tmpData, pid);
                }
                loadDataType = dataTypedrive;

                //添加行驶记录的起始状态
                TravelSummaryModel travelSummaryModel = new TravelSummaryModel();
                travelSummaryModel.setSummryData(OBDClient.this.getCurrentSummryData());
                travelSummaryModel.setCarNumber(carInfoModel.getCarNumber());
                travelSummaryModel.setOnlyFlag(onlyFlag);
                travelSummaryModel.setUserName(UserData.getInstance().getUserName());
                travelSummaryModel.setVinCode(vinCode);
                travelSummaryModel.setUploadFlag(UploadStatusEnum.NotUpload);
                travelSummaryModel.setStartDate(startTime);
                travelSummaryModel.setEndDate(TimeUtils.getNowString());
                daoSession.insert(travelSummaryModel);


                threadPool.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        OBDClient.this.calculateTraveData();
                    }
                }, 0, 1, SECONDS);


                String[] ALL_DRIVE_PIDs = APP.getInstance().getResources().getStringArray(R.array.ALL_PID_DRIVE);
                List<String> allDrivePidList =new ArrayList<>( Arrays.asList(ALL_DRIVE_PIDs));

                allDrivePidList.removeAll(mainPIDList);


                List<String> pids = new ArrayList<>();

                int location = 0, length = 1;

                while (true) {
                    if (connectStatus == connectTypeHaveBinded) {
                        switch (loadDataType) {
                            case dataTypeTest:

                                String tmpData = BlueteethService.getData("010D");
                                SensorsService.SensorsDataHandler(tmpData, "010D");

                                break;
                            case dataTypeTijian:
                                OBDClient.this.healthExamination();
                                loadDataType = dataTypedrive;
                                break;
                            case dataTypedrive: {
                                if (pids.size() > 0) {
                                    location = allDrivePidList.indexOf(pids.get(pids.size() - 1));
                                    location++;
                                    if (location == allDrivePidList.size()) {
                                        location = 0;
                                    }
                                }

                                pids = OBDClient.this.getDrivePidWithOther(allDrivePidList.subList(location, location + length));

                                for (int i = 0; i < pids.size(); i++) {
                                    if (connectStatus == connectTypeHaveBinded) {
                                        String pid = pids.get(i);
                                        String data = BlueteethService.getData(pid);

                                        if (data.length() > 0) {
                                            if (i >= mainPIDList.size()) {
                                                travelArray.add(OBDClient.this.readTravelDataWith(pid, data));
                                            }
                                            SensorsService.SensorsDataHandler(data, pid);

                                            if (pid.equals("010D")) {
                                                OBDClient.this.postHypervelocity();
                                            }
                                        }
                                    }
                                }
                            }


                            break;
                            case dataTypeClearErr: {

                                BlueteethService.getData("04");
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
    //是否急加速
    private void isHardAcceleration(){



    }

    private void isBrakes(){

    }


    /**
     * 保存数据
     */
    private void saveOBDData(){

        Print.d("saveOBDData","========"+travelArray.size());
        if (travelArray.size() >60)
        {


            List<String> tempTravelArray=new ArrayList();
            tempTravelArray.addAll(travelArray);
            Print.d("saveOBDData","tempTravelArray------------"+tempTravelArray.size());
            travelArray.clear();
            Print.d("saveOBDData","------------"+onlyFlag);
            Print.d("saveOBDData","tempTravelArray------------"+tempTravelArray.size());
            //勋章

            //距离和时间的值是设备自己累积的，所以这两个数值逐渐增大
            double driveDist = SensorsService.dist();//走了多远距离
            int driveTime =(int)SensorsService.totalTime();//走了多久时间


            //车辆统计数据

            double showfuelCount=SensorsService.showUsaLiter();//行车100公里的油耗

            double fuelCount=showfuelCount*driveDist/100;

            Print.d("saveOBDData","111111");
            CarVinStatisticModel carVinStatisticModel=daoSession.queryBuilder(CarVinStatisticModel.class).where(CarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();

            if (carVinStatisticModel.getMaxTime() <driveTime)
            {
                carVinStatisticModel.setMaxTime(driveTime);
            }

            Print.d("saveOBDData","222222");
            if (carVinStatisticModel.getMaxDist() <driveDist)
            {
                carVinStatisticModel.setMaxDist(driveDist);
            }



            carVinStatisticModel.setDistCount(originalDistCount+driveDist);
            carVinStatisticModel.setTimeCount(originalTimeCount+driveTime);

            carVinStatisticModel.setFuelCount(originalFuelCount+fuelCount);

            carVinStatisticModel.setAvgFuel(showfuelCount);

            daoSession.update(carVinStatisticModel);
            Print.d("saveOBDData","3333333");



            //车辆原始数据
            carInfoModel.setTotalMileage(originalCarMileage+driveDist);

            daoSession.update(carInfoModel);
            //保存勋章数据



            Print.d("saveOBDData","444444");
            SingleCarVinStatisticModel singleCarVinStatisticModel=daoSession.queryBuilder(SingleCarVinStatisticModel.class).where(SingleCarVinStatisticModelDao.Properties.VinCode.eq(vinCode)).unique();
            singleCarVinStatisticModel.setDistCount(driveDist);
            singleCarVinStatisticModel.setTimeCount(driveTime);
            singleCarVinStatisticModel.setFuelCount(fuelCount);
            singleCarVinStatisticModel.setVinCode(vinCode);
            singleCarVinStatisticModel.setAvgFuel(showfuelCount);
            singleCarVinStatisticModel.setAvgVehicleSpeed(SensorsService.vehicleSpeedAve());
            daoSession.update(singleCarVinStatisticModel);



            Print.d("saveOBDData","555555");
            TravelSummaryModel travelSummaryModel = daoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.OnlyFlag.eq(onlyFlag)).unique();
            travelSummaryModel.setMileage(driveDist);
            travelSummaryModel.setDriveTime(driveTime);
            travelSummaryModel.setAverageSpeed(SensorsService.vehicleSpeedAve());
            travelSummaryModel.setAverageOilConsume(showfuelCount);
            travelSummaryModel.setSummryData(OBDClient.this.getCurrentSummryData());
            travelSummaryModel.setEndDate(TimeUtils.getNowString());
            daoSession.update(travelSummaryModel);

            Print.d("saveOBDData","666666");
            for (String data:tempTravelArray) {
                Print.d("saveOBDData","---------"+data);
                TravelDataModel travelDataModel=new TravelDataModel();
                travelDataModel.setOnlyFlag(onlyFlag);
                travelDataModel.setVinCode(vinCode);
                travelDataModel.setTravelData(data);
                travelDataModel.setUserName(UserData.getInstance().getUserName());
                travelDataModel.setUploadFlag(UploadStatusEnum.NotUpload);
                daoSession.insert(travelDataModel);

            }
            Print.d("saveOBDData","777777");


            EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarDataUpdate,vinCode));
            Print.d("saveOBDData","============"+travelArray.size());

        }


    }




}
