package com.qcwp.carmanager.obd;

import java.util.List;

/**
 * Created by qyh on 2017/6/8.
 */

public class SensorsService {

// 改变值得
    // showUsaLiter 直接可以展示
    // 替换：SensorsService.showUsaLiter+SensorsService.idlingFuel +
    // SensorsService.customIdlingFuel

    // ------------------------------------------------------------------------

    public static final String VIN_PIDS = "0902";
    public static String SPValue = "";// 协议代码
    public static String currentMileage = "";// 当前油耗 >>未使用

    // ------------------------------------------------------------------------

    public static native String MAIN_ACTIVITY_PIDS();

    public static native double engineLoadValue();// 计算发动机负荷值

    public static native double engineCoolant();// 发动机冷却液温度

    public static native double lTFuelTrim();// 长时燃料补偿值（气缸组1）

    public static native double sTFuelTrim();// 短时燃料补偿值（气缸组1）

    public static native double fuelPressure();// 燃料压力

    public static native double map();//进气歧管绝对压力

    public static native double engineRpm(double engineRpm);// 发动机转速

    public static native double engineRpm();

    public static native double maxEngineRpm();// 最大发动机转速

    public static native double maxEngineRpm(double maxEngineRpm);

    public static native double engineRpmAve();

    public static native double vehicleSpeed();// 车速

    public static native double vehicleSpeed(double vehicleSpeed);

    public static native double vehicleSpeedAve();// 平均车速

    public static native double vehicleSpeedAve(double vehicleSpeedAve);

    public static native double maxVehicleSpeed();// 最大车速

    public static native double maxVehicleSpeed(double maxVehicleSpeed);

    public static native double vehicleSpeedCount();// 测试时间中总的车速，用来计算平均车速用的

    public static native double vehicleSpeedCount(double vehicleSpeedCount);

    public static native double timingAdvance(); // 1气缸点火时提前角

    public static native double intakeTemp(); // 进气温度

    public static native double airFlowRate();// 空气流量传感器的空气流量

    public static native double acceleratorPedalPosition();// 节气门位置

    public static native double acceleratorPedalPosition(
            double acceleratorPedalPosition);

    public static native double acceleratorPedalPositionAvg();

    public static native double maxAcceleratorPedalPosition(); // 最大节气门位置

    public static native double maxAcceleratorPedalPosition(
            double maxAcceleratorPedalPosition);

    public static native String vinCode();// vin码

    public static native String vinCode(String vinCode);

    public static native String vinCodeSourceData();

    public static native String calibrationId();

    public static native String calibrationIdSourceData();

    public static native double usaLiter();// 美-升每百公里(瞬时)

    // 行车100公里平均油耗，前端显示使用

    public static native double showUsaLiter();// 行车100公里平均油耗，前端显示使用

    // 行车100公里瞬时油耗，前端显示使用

    public static native double instantFuel();// 行车100公里瞬时油耗，前端显示使用

    public static native double dist();// 里程 （计算）

    public static native double dist(double dist);

    public static native double beginMileage();// 开始里程

    public static native double beginMileage(double beginMileage);

    public static native double totalTime();// 总行驶时间

    public static native double totalTime(double totalTime);

    public static native double idlingFuel();// 怠速油耗

    public static native double customIdlingFuel();// 自定义怠速油耗

    public static native double literAvg(double literAvg); // 60时速5-100次油耗平均值

    public static native double literAvg(); // 60时速5-100次油耗平均值

    public static native double f_value2_pre(double f_value2_pre);

    public static native double f_value2(double f_value2);

    public static native double s_value2(double s_value2);

    public static native double idlingRatio1(double idlingRatio1);// 0时速怠速系数

    public static native double idlingRatio2(double idlingRatio2);// 1-10时速怠速系数

    public static native double pl(double pl);// 排量

    public static native double testTotalTime();// 测试时间

    public static native double testTotalTime(double testTotalTime);// 测试时间




    // 体检新加参数
    public static native List<String> powertrainDTCList(); // 动力系统故障码

    public static native List<String> chassisDTCList(); // 底盘系统故障码

    public static native List<String> bodyDTCList(); // 车身系统故障码

    public static native List<String> networkDTCList(); // 网络通讯系统故障码

    public static native int DtcCount();// 故障码个数

    public static native int DrivingDataUnusualCount(); // 行车数据异常个数

    public static native int DrivingDataNormalCount();// 行车数据正常个数

    public static native int DrivingDataVoidCount(); // 行车数据无值个数

    public static native String CarCheckUp_Pid_List();// 行车数据pid

    public static native String CarCheckUp_PidDescription_list(); // 行车数据pid描述

    public static native double[] CarCheckUp_Value_list();// 行车数据具体值

    public static native double[] CarCheckUp_MaxValue_list();// 行车数据最大值

    public static native double[] CarCheckUp_MinValue_list();// 行车数据最小值

    public static native String CarCheckUp_Units_list(); // 单位

    public static  native  double controlModuleVoltage(); //电压

    public static native int ambientAirTemperature();//环境空气温度

    public static native int stopTime();
    public static native int stopTime(int stopTime);

    public static native int travelTime();
    public static native int travelTime(int travelTime);


    // -------------------------------------------------------------------------

    public static native void initData();

    public static native void SensorsDataHandler(String sensorsData,String pid);

    public static native void testing(double intervalTime);

    public static native String GetVinCode(String vinData);

    public static native void setAccelerator(String data, String pid);

    public static native List<String> getDtcData(String data);

    public static native void calculateTraveData();

    public static native List<String>  dataHandler(String data, boolean isSpecialVinValue);



    // 体检
    public static native void Car_CheckUp_init(); // 体检初始化

    public static native int DTC_CheckUp(String data); // 故障码检测

    public static native int[] EngineBehaviour_CheckUp(String data); // 发动机工况

    public static native int DrivingData_CheckUp(String data, String pid); // 行车数据

    public static native int Car_CheckUp_score(); // 体检得分

    //汽车碰撞
    //public static native double beforeVehicleSpeed(double beforeVehicleSpeed);//前一秒的车速，启动判断汽车碰撞线程前，最好初始化下
    public static native int vehicleImpact();//判断是否碰撞  ， 返回值    0:没有发生碰撞 ，1：发生碰撞（符合现有碰撞条件）
    public static native void vehicleImpactInit();//碰撞初始化函数
    public static native double bfImpactVehicleSpeed();//碰撞前时速值
    public static native double afImapactVehicleSpeed();//碰撞后时速值

    //油门踏板解析
    public static native double analysisAcceleratorPedal(String tmpData ,String pid );


//	double SensorsService::val1 = 0.3;//系数，0-10,在calculateFuel中使用0-3
//	double SensorsService::val2 = 1.05;//系数10-20
//	double SensorsService::val3 = 1.05;//系数20-30
//	double SensorsService::val4 = 1.05;//系数30-40
//	double SensorsService::val5 = 1.1;//系数40-50
//	double SensorsService::val6 = 1.1;//系数50-60
//	double SensorsService::val7 = 1.1;//系数60-70
//	double SensorsService::val8 = 1.2;//系数70-80
//	double SensorsService::val9 = 1.2;//系数80-90
//	double SensorsService::val10 = 1.2;//系数90-100
    /**
     * 初始化分段时速没有系数值，
     * @param val1
     * @param val2
     * @param val3
     * @param val4
     * @param val5
     * @param val6
     * @param val7
     * @param val8
     * @param val9
     * @param val10
     */
    public static native void initCoefficient(double val1,double val2,double val3,double val4,double val5,double val6,double val7,double val8,double val9,double val10);

    /**
     * 最大油耗
     * 默认39.99，0为不限制
     * @param val
     */
    public static native void setMaxLiter(double val);

    /**
     * 设置超速预警速度
     * @param overSpeed
     */
    public static native void setOverSpeed(double overSpeed);
    /**
     * 是否超速,1超速，0不超速
     * @param overSpeed
     */
    public static native int isOverSpeed();





    static {
        System.loadLibrary("native-lib");
    }

    // public static void main(String[] args)
    // {
    //
    // String pids = SensorsService.VIN_PIDS;
    // System.out.println(pids);;
    // // Log.i(TAG, pids);
    // // double maxEngineRpm=7.9;
    // // String maxEn = SensorsService.maxEngineRpm(maxEngineRpm);
    // // Log.i(TAG, maxEn);
    // }

}
