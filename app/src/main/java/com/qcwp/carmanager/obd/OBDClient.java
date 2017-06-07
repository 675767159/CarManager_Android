package com.qcwp.carmanager.obd;

import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.model.UserData;

/**
 * Created by qyh on 2017/6/6.
 */

public class OBDClient {

    private   int connectStatus;//连接状态
    private   LoadDataTypeEnum loadDataType;//车辆处于何种状态
    private   String vinCode;//车辆的vincode码
    private   String  spValue;//协议
    private   double vehicleSpeed;//当前速度
    private   String isGetVehicleSpeed;//是否初始化成功
    private   double engineRpm;//转速
    private   double fuelPressure;//气压
    private   double totalTime;//行驶时间
    private   double avgVehicleSpeed;//平均速度
    private   double avgOilConsume;//平均油耗
    private   double currentOilConsume;//当前油耗
    private   double dist;//当前里程
    private   double controlModuleVoltage;//控制模块电压
    private   int ambientAirTemperature;//环境空气温度(-40~214)
    private   double engineCoolant;//发动机冷却液温度(-40~214)
    private   double totalMileage;//总里程
    private   String carCheckUpPidDescriptionList;
    private   String carCheckUpUnitsList;
    private   String carCheckUpPidList;
    private   int DtcCount;
    private   int DrivingDataUnusualCount;
    private   int carCheckUpScore;
    private   int readyToExamination;//连接状态
    private   double bfImpactVehicleSpeed; //碰撞前
    private   double afImapactVehicleSpeed; //碰撞后

    private static class UserDataHolder{

        static final OBDClient INSTANCE = new OBDClient();
    }

    /**
     * * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private OBDClient(){

    }
    public static OBDClient getInstance() {

        return OBDClient.UserDataHolder.INSTANCE;
    }

    public static void readVinCode(int tempOnlyFlag,int tempSensorsService)   {

    }

    static {
        System.loadLibrary("SensorsService");
    }
}
