#pragma once
#include<string>
#include<vector>
#include <map>
using namespace std;

const int MAXNUM=30; //体检汽车参数个数
static map<string,int> Car_CheckUp_location; //用来表示指令对应数组的位置

class SensorsService
{
public:

	static string  pids ;
	static double engineLoadValue;	//计算发动机负荷值
	static double lTFuelTrim;		//长时燃料补偿值（气缸组1）
	static double lTFuelTrim2;//长时燃料补偿值（气缸组2）
	static double sTFuelTrim;	//短时燃料补偿值（气缸组1）
	static double sTFuelTrim2;	//短时燃料补偿值（气缸组2）
	static double fuelPressure;		//燃料压力
	static double map;  //进气歧管绝对压力
	static double maxVehicleSpeed;	//最大车速
	static double intakeTemp;  //进气温度
	static double engineCoolant;	//发动机冷却液温度
	static double airFlowRate;		//空气流量传感器的空气流量
	static double acceleratorPedalPosition;		//节气门位置
	static string acceleratorPedalPositionPid;//节气门位置指令
	static double maxAcceleratorPedalPosition;	//最大节气门位置
	static double acceleratorPedalPositionTotal ;//总的节气门位置
	static double avgAcceleratorPedalPosition;   //节气门位置平均值
	static double vehicleSpeed;				//车速
	static double avgVehicleSpeed ;	 //平均车速
	static double engineRpm;	//发动机转速
	static double engineRpmTotal;	//总的发动机转速
	static double avgEngineRpm;		//发动机转速平均值
	static double maxEngineRpm;	//最大发动机转速
	static double timingAdvance;  //1气缸点火时提前角
	static double dist; //里程 （计算）
	static double cal_acc_min; //最小油门踏板
	static double cal_acc_max  ; //最大油门踏板

	static string vinCodeSourceData ;
	static string vinCode ;			//vin码

	static string calibrationIdSourceData ;
	static string calibrationId ;

	static double literAvg ; // 60时速5-100次油耗平均值
	static double idlingFuel ;//怠速油耗
	static double idlingRatio1 ;// 0时速怠速系数
	static double idlingRatio2 ;// 1-10时速怠速系数
	static double instantFuel ;// 行车100公里瞬时油耗，前端显示使用
	static double customIdlingFuel; // 自定义怠速油耗

	static double instantOriginFuel ;// 原始瞬时油耗，单位：升/小时
	static double totalOriginFuel ;// 总油耗，单位：升
	static double totalDriveFuel ;// 行驶油耗，单位：升


	static double idlingFuel1 ;
	static double idlingFuel2;

	static int stopTime;	//停车时间
	static int travelTime;	//行驶时间

	static double usaKPL  ;	//美-公里每升(瞬时)
	static double usaKPLAvg  ; //美-公里每升(平均值)
	static double usaLiter  ;	//美-升每百公里(瞬时)
	static double  usaLiterAvg  ;	//美-升每百公里(平均值)
	static double totalUsaKpl ;//美-公里每升(瞬时) 之和
	static double totalUsaLiter ;//美-升每百公里(瞬时) 之和
	static double totalEnglandKpl ;	// 英-公里每升(瞬时)  之和
	static double totalEnglandLiter; //英-升每百公里(瞬时) 之和
	static double showUsaLiter  ;// 行车100公里平均油耗，前端显示使用


	static double usaLiter2;	// 测试没有MAF的情况下获取油耗（测试）
	static double totalUsaLiter2 ;
	static double  usaLiterAvg2  ;
	static double usaKPL2;

	// 第一个公式用
	static double f_value1 ;
	static double f_value2_pre;
	static double f_value2 ;

	// 第二个公式用
	static double s_value1 ;
	static double s_value2 ;

	static double pl;		//排量


	static double beginMileage ; //开始里程
	static double  totalTime ;//总行驶时间

	static double testTotalTime ;	//测试时间
	static double vehicleSpeedTotal ;	//测试时间中总的车速，用来计算平均车速用的


	//体检新加参数
	static vector<string> powertrainDTCList;	//动力系统故障码

	static vector<string> chassisDTCList;	//底盘系统故障码

	static vector<string> bodyDTCList;	//车身系统故障码

	static vector<string> networkDTCList;	//网络通讯系统故障码

	static int DtcCount;	//故障码个数

	static int DrivingDataUnusualCount;		//行车数据异常个数
	static int DrivingDataNormalCount;		//行车数据正常个数
	static int DrivingDataVoidCount;		//行车数据无值个数
	//	static int DrivingDataTotalCount;	//行车数据总个数

	static string CarCheckUp_Pid_List;  //行车数据pid
	static string CarCheckUp_PidDescription_list;  //行车数据pid描述

	static vector<double> CarCheckUp_Value_list; //行车数据具体值
	static vector<double> CarCheckUp_MaxValue_list; //行车数据最大值
	static vector<double> CarCheckUp_MinValue_list;//行车数据最小值
	static string  CarCheckUp_Units_list;	//单位


	//0161	0162	0163
	static int driverTorque;	//司机的需求引擎的扭矩
	static int actualTorque;	//实际发动机的扭矩
	static int engineReferenceTorque;	//发动机参考转矩


	static int ambientAirTemperature;//环境空气温度
	static double controlModuleVoltage;//控制模块电压

	static	int calculateTraveDataCount;//计算次数
	static int matchVehicleSpeedCount;//符合时速大于60的次数
	static double tmptotalFuel;
	static bool isCalculate;//是否需要计算
	//static double beforeVehicleSpeed;

	//汽车碰撞参数
	//static int vehicleImpactCount;  //调用次数
	static int arrayHead;  //时速数据头位置
	static int arrayEnd;	//时速数据尾位置
	static bool beginCalculate;//时速是否已经有10个
	static vector<double> beforeVehicleSpeed; //10个时速数据
	static double bfImpactVehicleSpeed; //碰撞前
	static double afImapactVehicleSpeed; //碰撞后时速
public:
	static void initData();	//初始化

	//static void calculateFuel(int travelTimeCount, int stopTimeCount); //油耗计算
	static void calculateFuel(); //油耗计算

	static void calculateFuelNew(); //新油耗计算

	static void SensorsDataHandler(string sensorsData,string pid);

	static void testing(double intervalTime) ;

	static string GetVinCode(string vinData); //解析VIN码值

	// android setAccelerator
	static void findRightAccelerator(string tmpData ,string pid ) ;
	// Android getDtcData
	static vector<string> dtcs(string data );

	static void calculateIdlingFuel(double stopTimeCount);  //百公里怠速油耗
	//android   getTravelTotalMileage
	static double calculateTravelTotalDistance();  //计算此次行车总里程

	static double calculateVehicleSpeedAvg();

	static double calculateAcceleratorPedalPositionAvg();

	static double calculateEngineRpmAvg();

	static void calculateTraveData();
	static string analysisDTC(string data);
	static vector<string> dataHandler(string data, bool isSpecialVinValue);

	//体检
	static void Car_CheckUp_init();	//体检初始化

	static int DTC_CheckUp(string data ) ;	//故障码检测

	static vector<int>  EngineBehaviour_CheckUp(string data);   //发动机工况

	static int  DrivingData_CheckUp(string data,string pid);		//行车数据

	static int  Car_CheckUp_score();	//体检得分

	static int isNormal(double tmpNum,double maxNum,double MinNum);//判断值是否正常的，C++自己调用
	static void setCarCheckUpValue(int i,double Value,double MaxValue,double MinValue); //给CarCheckUp_Value_list，CarCheckUp_MaxValue_list，CarCheckUp_MinValue_list赋值
	static void setCarCheckUpValue(int i,double MaxValue,double MinValue);
	static void setCarCheckUpValue(int i,double Value);


	static double analysisAcceleratorPedal(string tmpData ,string pid );//油门踏板解析


	static int vehicleImpact();// 汽车碰撞判断
	static void vehicleImpactInit(); //初始化汽车碰撞数据

	static double oxygenSensor1;	//氧传感器-1
	static double oxygenSensor2;	//氧传感器-2
	static double oxygenSensor3;	//氧传感器-3
	static double oxygenSensor4;	//氧传感器-4
	static double oxygenSensor5;	//氧传感器-5
	static double oxygenSensor6;	//氧传感器-6
	static double oxygenSensor7;	//氧传感器-7
	static double oxygenSensor8;	//氧传感器-8



};

