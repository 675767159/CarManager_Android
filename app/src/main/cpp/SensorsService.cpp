#include "SensorsService.hpp"
string SensorsService:: pids = "0105,010B,010C,010D,010F,0110,0142,0146,015A";
double SensorsService:: engineLoadValue=0.0;
double SensorsService::lTFuelTrim=0.0;
double SensorsService::fuelPressure=0.0;
double SensorsService::map=0.0;
double SensorsService::maxVehicleSpeed=0.0;
double SensorsService::intakeTemp=0.0;
double SensorsService::engineCoolant=0.0;
double SensorsService::airFlowRate=0.0;
double SensorsService::acceleratorPedalPosition=0.0;
double SensorsService::maxAcceleratorPedalPosition=0.0;
double SensorsService::vehicleSpeed=0.0;
double SensorsService::avgVehicleSpeed =0.0;
double SensorsService::sTFuelTrim=0.0;
double SensorsService:: engineRpm=0.0;
double SensorsService::maxEngineRpm=0.0;
//  double vehicleSpeed=0.0;
double SensorsService::timingAdvance=0.0;
double SensorsService::dist=0.0; //计算
double SensorsService::cal_acc_min=10 ; //最小油门踏板
double SensorsService::cal_acc_max  = 75;//最大油门踏板
string SensorsService::vinCodeSourceData = "";
string SensorsService::vinCode = "";
string SensorsService::calibrationIdSourceData = "";
string SensorsService::calibrationId = "";
double SensorsService::literAvg = 5; // 60时速5-100次油耗平均值
double SensorsService::idlingFuel = 0;
// 0时速怠速系数
double SensorsService::idlingRatio1 = 20;
// 1-10时速怠速系数
double SensorsService::idlingRatio2 = 2 * idlingRatio1;
// 行车100公里瞬时油耗，前端显示使用
double SensorsService::instantFuel = 0.0;
double SensorsService::customIdlingFuel = 0; // 自定义怠速油耗
double SensorsService::totalUsaKpl = 0.0;
double SensorsService::totalUsaLiter = 0.0;
double SensorsService::totalUsaLiter2 = 0.0;
double SensorsService::totalEnglandKpl = 0.0;
double SensorsService::totalEnglandLiter = 0.0;

double SensorsService::idlingFuel1 = 0.0;
double SensorsService::idlingFuel2 = 0.0;
double SensorsService::usaKPL = 0;
double SensorsService::usaKPLAvg = 0.0;

double SensorsService::usaLiter = 0.0;
// 行车100公里平均油耗，前端显示使用
double SensorsService::showUsaLiter = 0.0;
double  SensorsService::usaLiterAvg = 0.0;
double SensorsService::usaLiter2=0.0;

// 第一个公式用
double SensorsService::f_value1 = 0.48;
double SensorsService::f_value2_pre = 3600;
double SensorsService::f_value2 = 3600;

// 第二个公式用
double SensorsService:: s_value1 = 0.48;
double SensorsService::s_value2 = 3000;

double SensorsService::pl = 1.8;

double  SensorsService::usaLiterAvg2 = 0.0;
double SensorsService::usaKPL2=0.0;

int SensorsService::stopTime=0;
int SensorsService::travelTime=0;
double SensorsService::beginMileage = 0.0;  //开始里程
double SensorsService::totalTime=0.0; //总行驶时间

double SensorsService::testTotalTime = 0.0;
double SensorsService::vehicleSpeedTotal = 0;
double SensorsService::acceleratorPedalPositionTotal =0;
double SensorsService::avgAcceleratorPedalPosition =0.0;
int SensorsService::driverTorque=0;	//司机的需求引擎的扭矩
int SensorsService::actualTorque=0;	//实际发动机的扭矩
int SensorsService::engineReferenceTorque=0;	//发动机参考转矩

vector<string> SensorsService::powertrainDTCList;	//动力系统故障码

vector<string> SensorsService::chassisDTCList;	//底盘系统故障码

vector<string> SensorsService::bodyDTCList;	//车身系统故障码

vector<string> SensorsService::networkDTCList;	//网络通讯系统故障码

int SensorsService::DtcCount;	//故障码个数

int SensorsService::DrivingDataUnusualCount;		//行车数据异常个数
int SensorsService::DrivingDataNormalCount;		//行车数据正常个数
int SensorsService::DrivingDataVoidCount;		//行车数据无值个数
string SensorsService::CarCheckUp_Pid_List="0104,0105,0106,0107,0108,0109,010A,010B,010C,010D,"
"010E,010F,0110,0111,011F,0121,0122,0131,0133,0142,0145,0146,0147,0148,0149,014A,014B,014C,014D,014E";
string SensorsService::CarCheckUp_PidDescription_list="发动机负载,冷却剂温度,燃油修正(第1排)短时,"
"燃油修正(第1排)长时,燃油修正(第2排)短时,燃油修正(第2排)长时,燃油压力,进气歧管压力,发动机转速,"
"车速,点火提前角,进气温度,空气流量速率,节气门开度,发动机运行时间,故障指示灯灯亮后行驶距离,燃油压力(相对歧管真空),"
"故障码清除后行驶公里数,气压,电压,相对节气门位置,环境温度,绝对节气门门位置B,绝对节气门门位置C,绝对节气门门位置D,"
"加速踏板位置E,加速踏板位置F,节气门控制指令,MIL时发动机运转时间,故障码清除后时间";
vector<double> SensorsService::CarCheckUp_Value_list(MAXNUM,0); //行车数据具体值
vector<double> SensorsService::CarCheckUp_MaxValue_list(MAXNUM,0); //行车数据最大值
vector<double> SensorsService::CarCheckUp_MinValue_list(MAXNUM,0);//行车数据最小值
string SensorsService::CarCheckUp_Units_list="%,°C,%,%,%,%,kPa,kPa,rpm,km/h,°,°C,g/s,%,sec,km,kPa,km,kPa,V,%,°C,%,%,%,%,%,%,minutes,minutes";

int  SensorsService::ambientAirTemperature=0;//环境空气温度
double  SensorsService::controlModuleVoltage=0;//控制模块电压

/**
 * 分割string成vector<string>
 * @param vinData
 * @return
 */
static vector<string> split_for_string(string str,const string separator)
{
    vector<string> arr;
    string::size_type perPos(0);
    for(string::size_type pos(0);pos!=string::npos;pos+=separator.length())
    {
        pos = str.find(separator,pos);
        if(pos!=string::npos)
        {
            //str.replace(pos,old_value.length(),new_value);
            arr.push_back(str.substr(perPos,pos-perPos));
            perPos=pos+separator.length();
        }
        else{
            arr.push_back(str.substr(perPos,str.length()-perPos));
            break;
        }
    }
    return arr;
}



void SensorsService::initData()   //初始化
{
    
    
    pids = "0105,010B,010C,010D,010F,0110,0142,0146,015A";
     engineLoadValue=0.0;
    lTFuelTrim=0.0;
    fuelPressure=0.0;
    map=0.0;
    maxVehicleSpeed=0.0;
    intakeTemp=0.0;
    engineCoolant=0.0;
    airFlowRate=0.0;
    acceleratorPedalPosition=0.0;
    maxAcceleratorPedalPosition=0.0;
    vehicleSpeed=0.0;
    avgVehicleSpeed =0.0;
    sTFuelTrim=0.0;
     engineRpm=0.0;
    maxEngineRpm=0.0;
    //  double vehicleSpeed=0.0;
    timingAdvance=0.0;
    dist=0.0; //计算
    cal_acc_min=10 ; //最小油门踏板
    cal_acc_max  = 75;//最大油门踏板
    vinCodeSourceData = "";
    vinCode = "";
    calibrationIdSourceData = "";
    calibrationId = "";
    literAvg = 5; // 60时速5-100次油耗平均值
    idlingFuel = 0;
    // 0时速怠速系数
    idlingRatio1 = 20;
    // 1-10时速怠速系数
    idlingRatio2 = 2 * idlingRatio1;
    // 行车100公里瞬时油耗，前端显示使用
    instantFuel = 0.0;
    customIdlingFuel = 0; // 自定义怠速油耗
    totalUsaKpl = 0.0;
    totalUsaLiter = 0.0;
    totalUsaLiter2 = 0.0;
    totalEnglandKpl = 0.0;
    totalEnglandLiter = 0.0;
    
    idlingFuel1 = 0.0;
    idlingFuel2 = 0.0;
    usaKPL = 0;
    usaKPLAvg = 0.0;
    
    usaLiter = 0.0;
    // 行车100公里平均油耗，前端显示使用
    showUsaLiter = 0.0;
    usaLiterAvg = 0.0;
    usaLiter2=0.0;
    
    // 第一个公式用
    f_value1 = 0.48;
    f_value2_pre = 3600;
    f_value2 = 3600;
    
    // 第二个公式用
     s_value1 = 0.48;
    s_value2 = 3000;
    
    pl = 1.8;
    
    usaLiterAvg2 = 0.0;
    usaKPL2=0.0;
    
    stopTime=0;
    travelTime=0;
    beginMileage = 0.0;  //开始里程
    totalTime=0.0; //总行驶时间
    
    testTotalTime = 0.0;
    vehicleSpeedTotal = 0;
    acceleratorPedalPositionTotal =0;
    avgAcceleratorPedalPosition =0.0;
    driverTorque=0;	//司机的需求引擎的扭矩
    actualTorque=0;	//实际发动机的扭矩
    engineReferenceTorque=0;	//发动机参考转矩
    ambientAirTemperature=0;//环境空气温度
    controlModuleVoltage=0;//控制模块电压
    
    powertrainDTCList.clear();	//动力系统故障码
    chassisDTCList.clear();	//底盘系统故障码
    bodyDTCList.clear();	//车身系统故障码
    networkDTCList.clear();	//网络通讯系统故障码

    
     DrivingDataUnusualCount=0;		//行车数据异常个数
     DrivingDataNormalCount=0;		//行车数据正常个数
     DrivingDataVoidCount=0;		//行车数据无值个数
     CarCheckUp_Pid_List="0104,0105,0106,0107,0108,0109,010A,010B,010C,010D,"
    "010E,010F,0110,0111,011F,0121,0122,0131,0133,0142,0145,0146,0147,0148,0149,014A,014B,014C,014D,014E";
     CarCheckUp_PidDescription_list="发动机负载,冷却剂温度,燃油修正(第1排)短时,"
    "燃油修正(第1排)长时,燃油修正(第2排)短时,燃油修正(第2排)长时,燃油压力,进气歧管压力,发动机转速,"
    "车速,点火提前角,进气温度,空气流量速率,节气门开度,发动机运行时间,故障指示灯灯亮后行驶距离,燃油压力(相对歧管真空),"
    "故障码清除后行驶公里数,气压,电压,相对节气门位置,环境温度,绝对节气门门位置B,绝对节气门门位置C,绝对节气门门位置D,"
    "加速踏板位置E,加速踏板位置F,节气门控制指令,MIL时发动机运转时间,故障码清除后时间";
    
   
     CarCheckUp_Units_list="%,°C,%,%,%,%,kPa,kPa,rpm,km/h,°,°C,g/s,%,sec,km,kPa,km,kPa,V,%,°C,%,%,%,%,%,%,minutes,minutes";
    
    

    //lkm = "0.0"; //计算
    usaKPL = 0;
    usaKPL2=0;
    usaKPLAvg = 0.0;
    usaLiter = 0.0;
    usaLiterAvg = 0.0;
    usaLiter2 = 0.0;
    usaLiterAvg2 = 0.0;
    showUsaLiter = 0.0;
    instantFuel = 0.0;
    //englandKPL = "0.0";
    //englandKPLAvg = "0.0";
    //englandLiter = "0.0";
    //englandLiterAvg = "0.0";
    dist = 0.0; //计算
    beginMileage = 0.0;  //开始里程
    //	totalMileage = 0.0;//总里程
    
    totalTime = 0; //总行驶时间
    idlingFuel = 0;// 100公里怠速油耗
    customIdlingFuel = 0;  // 自定义怠速油耗
    literAvg = 5; // 60时速5-100次油耗平均值
    
    // 第一个公式用
    f_value1 = 0.48;
    f_value2_pre = 3600;
    f_value2 = 3600;
    
    // 第二个公式用
    s_value1 = 0.48;
    s_value2 = 3000;
    
    // 0时速怠速系数
    idlingRatio1 = 20;
    
    // 1-10时速怠速系数
    idlingRatio2 = 2 * idlingRatio1;
    
    pl = 1.8;
    
    totalUsaKpl = 0.0;
    totalUsaLiter = 0.0;
    totalUsaLiter2 = 0.0;
    totalEnglandKpl = 0.0;
    totalEnglandLiter = 0.0;
    
    idlingFuel1 = 0.0;
    idlingFuel2 = 0.0;
    
    vector<string>arr = split_for_string(CarCheckUp_Pid_List,",");
    for(int i=0;i<arr.size();i++)
    {
        Car_CheckUp_location.insert(pair<string,int>(arr[i],i));
        CarCheckUp_Value_list[i]=0;
    }
    setCarCheckUpValue(Car_CheckUp_location["0104"],75,15);
    setCarCheckUpValue(Car_CheckUp_location["0105"],185,-39);
    setCarCheckUpValue(Car_CheckUp_location["0106"],10,-10);
    setCarCheckUpValue(Car_CheckUp_location["0107"],16,-23);
    setCarCheckUpValue(Car_CheckUp_location["0108"],10,-10);
    setCarCheckUpValue(Car_CheckUp_location["0109"],16,-23);
    setCarCheckUpValue(Car_CheckUp_location["010A"],800,0);
    setCarCheckUpValue(Car_CheckUp_location["010B"],300,0);
    setCarCheckUpValue(Car_CheckUp_location["010C"],8000,0);
    setCarCheckUpValue(Car_CheckUp_location["010D"],180,0);
    setCarCheckUpValue(Car_CheckUp_location["010E"],60,10);
    setCarCheckUpValue(Car_CheckUp_location["010F"],184,-39);
    setCarCheckUpValue(Car_CheckUp_location["0110"],800,0);
    setCarCheckUpValue(Car_CheckUp_location["0111"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["011F"],65535,0);
    setCarCheckUpValue(Car_CheckUp_location["0121"],0,0);
    setCarCheckUpValue(Car_CheckUp_location["0122"],80,0);
    setCarCheckUpValue(Car_CheckUp_location["0131"],65535,0);
    setCarCheckUpValue(Car_CheckUp_location["0133"],125,10);
    setCarCheckUpValue(Car_CheckUp_location["0142"],60,0);
    setCarCheckUpValue(Car_CheckUp_location["0145"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["0146"],120,-40);
    setCarCheckUpValue(Car_CheckUp_location["0147"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["0148"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["0149"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["014A"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["014B"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["014C"],100,0);
    setCarCheckUpValue(Car_CheckUp_location["014D"],0,0);
    setCarCheckUpValue(Car_CheckUp_location["014E"],65535,0);
    setCarCheckUpValue(Car_CheckUp_location["0154"],65535,0);
    setCarCheckUpValue(Car_CheckUp_location["0159"],655350,0);
    
}


/**
 * 油耗计算
 * @param count
 */
//void SensorsService::calculateFuel(int travelTimeCount, int stopTimeCount) {
void SensorsService::calculateFuel(){
    /*java.text.NumberFormat formater = java.text.DecimalFormat.getInstance();
     formater.setMaximumFractionDigits(1);
     formater.setMinimumFractionDigits(1);*/
    //char tmpChar[15];
    //double usakpl,usakplavg,usaliter,showusaliter,usaliteravg,usaliter2,usaliteravg2,usakpl2;
    
    
    //if(travelTime <= 0) travelTime = 1;
    //if(stopTime <= 0) stopTime = 1;
    
    double tmpMAF = 0.0;
    if(airFlowRate > 0)
        tmpMAF = airFlowRate;
    
    f_value2 = f_value2_pre;
    
    // 如果不能读取空气流量
    if(tmpMAF <= 0&&intakeTemp!=0) {
        tmpMAF = (((engineRpm * map / intakeTemp)/120)*(0.85/100*100)*(pl)*(28.97)/(8.314)) / 10;
        f_value2 = s_value2;
    }
    
    if ((tmpMAF <= 0 || intakeTemp==0)
        && map <= 0) {
        showUsaLiter = -1;
        return;
    }
    double mpg;
    
    if(f_value2 * tmpMAF / 100==0){
        mpg = 0.0;
    }
    else{
        mpg = (14.7 * 6.17 * 4.54 * vehicleSpeed * 0.621371) / (f_value2 * tmpMAF / 100);
    }
    
    //行车,踩油门,有车速
    if(vehicleSpeed > 0 || engineRpm > 0) {
        //美-公里每升(瞬时)
        usaKPL = mpg * 1.609344 / 3.78;
        //char usaKplChar[15];
        //sprintf_s(usaKplChar,"%.1f",usaKpl);
        //usaKPL=usaKplChar;
        totalUsaKpl += usaKPL;
        //美-公里每升(平均值)
        if(travelTime<=0)
        {
            usaKPLAvg=totalUsaKpl;
        }
        else
        {
            usaKPLAvg=totalUsaKpl / travelTime;
        }
        //sprintf(tmpChar,"%.1f",usakplavg);
        //usaKPLAvg = tmpChar;
        //sprintf_s(usaKplChar,"%.1f",(totalUsaKpl / travelTimeCount));
        //usaKPLAvg =usaKplChar;
        //美-升每百公里(瞬时)
        if(usaKPL==0)
        {
            usaLiter=0;
        }
        else
        {
            usaLiter=100 / usaKPL;
            //sprintf_s(usaKplChar,"%.1f",(100 / usaKpl));
            //usaLiter=usaKplChar;
        }
        //sprintf(tmpChar,"%.1f",usakpl);
        
        //usaKPL = tmpChar;
        //double tmpUsaLiter;
        //sscanf(usaLiter.c_str(),"%.1f",tmpUsaLiter);
        if(vehicleSpeed > 0 && vehicleSpeed <= 3) {
            usaLiter = usaLiter * 0.3;
        }
        if(vehicleSpeed > 3 && vehicleSpeed <= 10) {
            usaLiter = usaLiter * 0.3;
        }
        if(vehicleSpeed > 10 && vehicleSpeed <= 40) {
            usaLiter = usaLiter * 0.05+usaLiter;
        }
        if(vehicleSpeed > 40 && vehicleSpeed <= 70) {
            usaLiter = usaLiter * 0.1+usaLiter;
        }
        if(vehicleSpeed > 70) {
            usaLiter = usaLiter * 0.2+usaLiter;
        }
        //12.31 新加油耗最高39.99
        if(usaLiter>40)
        {
            usaLiter=39.99;
        }
        totalUsaLiter += usaLiter;
        //sprintf(tmpChar,"%.1f",usaliter);
        //usaLiter = tmpChar;
        
        //美-升每百公里(平均值)
        //SensorsService.usaLiterAvg = formater.format(totalUsaLiter / travelTimeCount);
        //	sprintf_s(usaKplChar,"%.1f",(totalUsaLiter / travelTimeCount));
        //	usaLiterAvg=usaKplChar;
        if(travelTime<=0)
        {
            usaLiterAvg=totalUsaLiter ;
        }
        else
        {
            usaLiterAvg=(totalUsaLiter / travelTime);
        }
        //sprintf(tmpChar,"%.1f",usaliteravg);
        //usaLiterAvg = tmpChar;
        // 测试没有MAF的情况下获取油耗（测试）
        double mpg2;
        if(s_value2 * tmpMAF / 100==0)
        {
            mpg2 = 0.0;
        }
        else
        {
            mpg2 = (14.7 * 6.17 * 4.54 * vehicleSpeed* 0.621371) / (s_value2 * tmpMAF / 100);
        }
        
        usaKPL2 = mpg2 * 1.609344 / 3.78;
        
        //	double tmpusaLiter2 ;
        if(usaKPL2==0)
            usaLiter2 = 0.0;
        else
            usaLiter2 = 100 / usaKPL2;
        
        //sprintf(usaKplChar,"%.1f",tmpusaLiter2);
        //usaLiter2=usaKplChar;
        //sscanf(usaKplChar,"%.1f",tmpusaLiter2);
        if(vehicleSpeed > 0 && vehicleSpeed <= 3) {
            usaLiter2 = usaLiter2* 0.3;
        }
        if(vehicleSpeed > 3 && vehicleSpeed <= 10) {
            usaLiter2 = usaLiter2* 0.3;
        }
        if(vehicleSpeed > 10 && vehicleSpeed <= 40) {
            usaLiter2 = usaLiter2* 0.05+usaLiter2;
        }
        if(vehicleSpeed > 40 && vehicleSpeed <= 70) {
            usaLiter2 = usaLiter2* 0.1+usaLiter2;
        }
        if(vehicleSpeed > 70 ) {
            usaLiter2 = usaLiter2* 0.2+usaLiter2;
        }
        
        //12.31 新加油耗最高39.99
        if(usaLiter2>40)
        {
            usaLiter2=39.99;
        }
        
        totalUsaLiter2+=usaLiter2;
        
        
        //美-升每百公里(平均值)
        //SensorsService.usaLiterAvg2 = formater.format(totalUsaLiter2 / travelTimeCount);
        //sprintf(usaKplChar,"%.1f",(totalUsaLiter2 / travelTimeCount));
        //usaLiterAvg2=usaKplChar;
        if(travelTime<=0)
        {
            usaLiterAvg2=totalUsaLiter2 ;
        }
        else
        {
            usaLiterAvg2=(totalUsaLiter2 / travelTime);
        }
        
        
        if(usaLiterAvg > 0) showUsaLiter = usaLiterAvg+idlingFuel+customIdlingFuel;
        else if(usaLiterAvg2 > 0) showUsaLiter = usaLiterAvg2+idlingFuel+customIdlingFuel;
        else showUsaLiter=idlingFuel+customIdlingFuel;
        //sprintf(tmpChar,"%.1f",showusaliter);
        //showUsaLiter = tmpChar;
        
        if(usaLiter > 0) instantFuel = usaLiter ;
        else if (usaLiter2 > 0) instantFuel = usaLiter2;
        
        
        /*
         sprintf_s(tmpChar,"%.1f",usakplavg);
         usaKPLAvg = tmpChar;
         sprintf_s(tmpChar,"%.1f",usaliter);
         usaLiter = tmpChar;
         // 行车100公里平均油耗，前端显示使用
         
         sprintf_s(tmpChar,"%.1f",usaliteravg);
         usaLiterAvg = tmpChar;
         sprintf_s(tmpChar,"%.1f",usaliter2);
         usaLiter2=tmpChar;
         
         */
        // ~ 将油耗添加至lkm
        //if(null != SensorsService.usaLiter && !"".equals(SensorsService.usaLiter)) {
        //	lkm = SensorsService.usaLiter;
        //} else {
        //	lkm = SensorsService.usaLiter2;
        //}
        /*if(usaliter>0)
         {
         lkm = usaliter;
         }
         else
         {
         lkm = usaliter2;
         }
         
         //英-公里每升(瞬时)
         double englandKpl = mpg * 1.609344 / 4.54;
         //englandKPL = formater.format(englandKpl);
         totalEnglandKpl += englandKpl;
         //英-公里每升(平均值)
         //SensorsService.englandKPLAvg = formater.format(totalEnglandKpl / travelTimeCount);
         
         //英-升每百公里(瞬时)
         double englandLiter = 100 / englandKpl;
         //SensorsService.englandLiter = formater.format(englandLiter);
         totalEnglandLiter += englandLiter;
         //英-升每百公里(平均值)
         //SensorsService.englandLiterAvg = formater.format(totalEnglandLiter / travelTimeCount);
         */
    }
}


string replace_all_distinct(string str,const string old_value,const string new_value)
{
    for(string::size_type pos(0);pos!=string::npos;pos+=new_value.length())
    {
        pos = str.find(old_value,pos);
        if(pos!=string::npos)
            str.replace(pos,old_value.length(),new_value);
        else
            break;
    }
    return str;
}



vector<string> SensorsService::dataHandler(string data, bool isSpecialVinValue)
{
    string tmpData=replace_all_distinct(data,">","");
    tmpData=replace_all_distinct(tmpData,"\r","");
    tmpData=replace_all_distinct(tmpData,"\t","");
    tmpData=replace_all_distinct(tmpData,"\n","");
    string pidStr="";
    int len=0;
    //trim 实现
    //tmpData.erase(0,tmpData.find_first_not_of("\r\t\n "));
    //tmpData.erase(tmpData.find_last_not_of("\r\t\n ")+1);
    
    if(isSpecialVinValue==false)
    {
        pidStr=tmpData.substr(0,4);
        tmpData=tmpData.erase(0,4);
        while(len!=-1)
        {
            len=tmpData.find(pidStr);
            if(len==-1)
                break;
            tmpData=tmpData.erase(len,4);
        }
    }
    
    vector<string> arr ;
    int stopNum= tmpData.length()/2;
    for(int i=0;i<stopNum;i++)
    {
        arr.push_back(tmpData.substr(i*2,2));
    }
    return arr;
}



/**
 * 解析VIN码值
 * @param vinData
 * @return
 */
string SensorsService::GetVinCode(string vinData)
{
    vinData=replace_all_distinct(vinData,">","");
    vinData=replace_all_distinct(vinData,"\r", "@");
    vinData=replace_all_distinct(vinData," ", "");
    string retValue = "";
    //	char tmpStr[5];
    bool isSpecialVinValue = false;
    
    vector<string> arr = split_for_string(vinData,"@");
    for(vector<string *>::size_type  i=0;i<arr.size();i++)
    {
      
        if (arr[i].find("BUS INIT:")!= string::npos) {
            std::vector<string>::iterator it = arr.begin()+i;
            arr.erase(it);
            
        }
        if(arr[i].length()<8) continue;
        
        string tmpStr = arr[i];
        string::size_type pos;
        if( !tmpStr.empty()&&(pos=tmpStr.find(":"))!=string::npos)
        {
            isSpecialVinValue = true;
            arr[i] = tmpStr.substr(pos+1,tmpStr.length());
        }
    }
    for(vector<string *>::size_type  k =0;k<arr.size();k++)
    {
        if(arr[k].length()<8) continue;
        
        vector<string> tmpData = dataHandler(arr[k],isSpecialVinValue);
        
        for(vector<string *>::size_type  j=0;j<tmpData.size();j++){
            if(tmpData[j].find("00")!=string::npos) continue;
            int intValue;
            sscanf(tmpData[j].c_str(),"%x",&intValue);
            
            if ((intValue >= 48 && intValue <= 57) || (intValue >= 65 && intValue <= 90) || (intValue >= 97 && intValue <= 122))
            {
                //sprintf(retValue.c_str(),"%s%d",retValue,intValue);
                retValue += (char)intValue;
                //				sprintf(tmpStr,"%d",intValue);
                //				retValue+=tmpStr;
            }
        }
    }
    
    if(retValue.length()>17){
        retValue = retValue.substr(retValue.length()-17,retValue.length());
    }
    printf("%s\n",retValue.c_str());
    return retValue;
}



/**
 * 处理ECU数据
 * @param sensorsData
 */

void SensorsService::SensorsDataHandler(string sensorsData,string pid )
{
    

    
    if( sensorsData.empty()||sensorsData.length() == 0||sensorsData.find("NO DATA")!= string::npos||sensorsData.find("CAN ERROR")!= string::npos||sensorsData.find("STOPPED")!= string::npos||sensorsData.find("ERROR")!= string::npos){

        return;
    }
    int data1=0;
    int data2=0;
    //char tmpStr[5];
    //sprintf(tmpStr, "%.0f", engineLoadValue);     //double 转 char
    
    if(sensorsData.find("4104")!= string::npos&&pid.find("0104")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            engineLoadValue=(data1 * 100 / 255);
        }
        //	sprintf(tmpStr, "%d", (data1 * 100 / 255));
        //	engineLoadValue=tmpStr;
        
    }
    else if(sensorsData.find("4105")!= string::npos&&pid.find("0105")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            engineCoolant=(data1 - 40);
        }
        //	sprintf(tmpstr, "%d", (data1 - 40));
        //	engineCoolant=tmpstr;
    }
    else if(sensorsData.find("4106")!= string::npos&&pid.find("0106")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sTFuelTrim=((data1 - 128) * 100 / 128);
        }
        //sprintf(tmpStr, "%d", ((data1 - 128) * 100 / 128));
        //sTFuelTrim=tmpStr;
    }
    else if(sensorsData.find("4107")!= string::npos&&pid.find("0107")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            lTFuelTrim=((data1 - 128) * 100 / 128);
        }
        //sprintf(tmpStr, "%d", ((data1 - 128) * 100 / 128));
        //lTFuelTrim=tmpStr;
    }
    else if(sensorsData.find("410A")!= string::npos&&pid.find("010A")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            fuelPressure=(data1 * 3);
        }
        //sprintf(tmpStr, "%d", (data1 * 3));
        //fuelPressure=tmpStr;
    }
    else if(sensorsData.find("410B")!= string::npos&&pid.find("010B")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            map=data1;
        }
        //sprintf(tmpStr, "%d", (data1));
        //map=tmpStr;
    }
    else if(sensorsData.find("410C")!= string::npos&&pid.find("010C")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>1)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sscanf(arr[1].c_str(),"%x",&data2);
            engineRpm=(((data1 * 256) + data2) / 4);
            //sprintf(tmpStr, "%d", (((data1 * 256) + data2) / 4));
            //engineRpm=tmpStr;
            if(engineRpm>maxEngineRpm)
                maxEngineRpm=engineRpm;
        }
    }
    else if(sensorsData.find("410D")!= string::npos&&pid.find("010D")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            vehicleSpeed = data1;
            if(160==vehicleSpeed&&0==engineRpm&&0==fuelPressure){
                vehicleSpeed=0;
            }
            if (vehicleSpeed > 50) {
                vehicleSpeed = vehicleSpeed * 1.02;
            }
            if(vehicleSpeed>maxVehicleSpeed)
                maxVehicleSpeed = vehicleSpeed;
        }
       
    }
    else if(sensorsData.find("410E")!= string::npos&&pid.find("010E")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            timingAdvance = (data1 / 2 - 64) ;
        }
    }
    else if(sensorsData.find("410F")!= string::npos&&pid.find("010F")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            intakeTemp = (data1 - 40) ;
        }
    }
    else if(sensorsData.find("4110")!= string::npos&&pid.find("0110")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>1)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sscanf(arr[1].c_str(),"%x",&data2);
            airFlowRate = (((data1 * 256) + data2) / 100) ;
        }
    }
    else if((sensorsData.find("4111")!=string::npos&&pid.find("0111")!=string::npos)||(sensorsData.find("415A")!=string::npos&&pid.find("015A")!=string::npos)||
            (sensorsData.find("4145")!=string::npos&&pid.find("0145")!=string::npos)||(sensorsData.find("4147")!=string::npos&&pid.find("0147")!=string::npos)||
            (sensorsData.find("4148")!=string::npos&&pid.find("0148")!=string::npos)||(sensorsData.find("4149")!=string::npos&&pid.find("0149")!=string::npos)||
            (sensorsData.find("414A")!=string::npos&&pid.find("014A")!=string::npos)||(sensorsData.find("414B")!=string::npos&&pid.find("014B")!=string::npos)){
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            acceleratorPedalPosition = (data1 * 100*1.0 / 255);
            if(acceleratorPedalPosition >= cal_acc_min && acceleratorPedalPosition<= cal_acc_max){
                acceleratorPedalPosition=((acceleratorPedalPosition-cal_acc_min)/(cal_acc_max-cal_acc_min)*100);
            }
            //获取最小油门 值 cal_acc_min 默认为10
            if(acceleratorPedalPosition < cal_acc_min)
                cal_acc_min = acceleratorPedalPosition;
            if(acceleratorPedalPosition > cal_acc_max){
                cal_acc_max = 100;
            }
            if(acceleratorPedalPosition > maxAcceleratorPedalPosition)
                maxAcceleratorPedalPosition = acceleratorPedalPosition;
        }
    }
    else if(sensorsData.find("4142")!=string::npos&&pid.find("0142")!=string::npos){
        //((A*256)+B)/1000
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>1)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sscanf(arr[1].c_str(),"%x",&data2);
            controlModuleVoltage=((data1*256)+data2)*1.0/1000;
        }
    }
    else if(sensorsData.find("4146")!=string::npos&&pid.find("0146")!=string::npos){
        //A-40
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            ambientAirTemperature=data1-40;
        }
    }
    else if(sensorsData.find("4161")!=string::npos&&pid.find("0161")!=string::npos){
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            driverTorque=data1-125;
        }
    }
    else if(sensorsData.find("4162")!=string::npos&&pid.find("0162")!=string::npos){
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            actualTorque=data1-125;
        }
    }
    else if(sensorsData.find("4163")!=string::npos&&pid.find("0163")!=string::npos){
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>1)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sscanf(arr[1].c_str(),"%x",&data2);
            engineReferenceTorque=data1*256+data2;
        }
    }
    else if(pid.find("0902")!=string::npos){
        vinCodeSourceData = sensorsData;
        printf("sensorsData====%s",sensorsData.c_str());
       vinCode = GetVinCode(vinCodeSourceData);
       
    }
    //新模拟器sensorsData4 "0: 4900004A5333\r1: 54443034563046\r2: 34323031363832\r\r>"
    else if(sensorsData.find("4904")!= string::npos&&pid.find("0904")!=string::npos){
        calibrationIdSourceData = sensorsData;
        calibrationId = sensorsData;
    }
    
    
}


/*
 * 100米加速测试
 *
 * 总时间
 * SensorsService.testTotalTime
 *
 * 最高时速
 * SensorsService.maxVehicleSpeed
 *
 * 平均时速
 * SensorsService.vehicleSpeedAve
 *
 * 行驶路程
 * SensorsService.dist
 * */
void SensorsService::testing(double intervalTime) {
    
    testTotalTime += intervalTime;
    
    vehicleSpeedTotal += vehicleSpeed;
    
    // 平均时速
    avgVehicleSpeed = vehicleSpeedTotal / testTotalTime;
    
    // 行驶路程
    double tmpDist = vehicleSpeed * intervalTime / 3600;
    dist += tmpDist;
}


void SensorsService::findRightAccelerator(string tmpData ,string pid ) {
    //string tmpData = BluetoothService.getData("015A");
    if( pid.find("015A")!=string::npos &&tmpData.find("415A")!= string::npos){
        //if(tmpData.contains("415A")) {
        //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "015A" };
        pids ="0105,010B,010C,010D,010F,0110,0142,0146,015A";
        return;
    }
    //tmpData = BluetoothService.getData("0145");
    else if( pid.find("0145")!=string::npos ){
        SensorsDataHandler(tmpData,pid);
        //if(tmpData.contains("4145") && Double.parseDouble(SensorsService.acceleratorPedalPosition) > 0) {
        if(tmpData.find("4145")!=string::npos && acceleratorPedalPosition > 0) {
            //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "0145" };
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,0145";
            return;
        }
    }
    else if(pid.find("0147")!=string::npos){
        SensorsDataHandler(tmpData,pid);
        if(tmpData.find("4147")!=string::npos && acceleratorPedalPosition > 0) {
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,0147";
            return;
        }
    }
    /*tmpData = BluetoothService.getData("0147");
     if(tmpData.contains("4147")) {
     MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "0147" };
     return;
     }*/
    else if( pid.find ("0148")!=string::npos){
        //tmpData = BluetoothService.getData("0148");
        SensorsDataHandler(tmpData,pid);
        //	if(tmpData.contains("4148") && Double.parseDouble(SensorsService.acceleratorPedalPosition) > 0) {
        if(tmpData.find("4148")!=string::npos &&acceleratorPedalPosition> 0) {
            //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "0148" };
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,0148";
            return;
        }
    }
    else if( pid.find ("0149")!=string::npos){
        //tmpData = BluetoothService.getData("0149");
        SensorsDataHandler(tmpData,pid);
        //if(tmpData.contains("4149") && Double.parseDouble(SensorsService.acceleratorPedalPosition) > 0) {
        if(tmpData.find("4149")!=string::npos && acceleratorPedalPosition > 0) {
            //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "0149" };
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,0149";
            return;
        }
    }
    else if( pid.find ("014A")!=string::npos){
        //tmpData = BluetoothService.getData("014A");
        SensorsDataHandler(tmpData,pid);
        //if(tmpData.contains("414A") && Double.parseDouble(SensorsService.acceleratorPedalPosition) > 0) {
        if(tmpData.find("414A")!=string::npos && acceleratorPedalPosition > 0) {
            //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "014A" };
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,014A";
            return;
        }
    }
    else if( pid.find ("014B")!=string::npos){
        //tmpData = BluetoothService.getData("014B");
        SensorsDataHandler(tmpData,pid);
        //if(tmpData.contains("414B") && Double.parseDouble(SensorsService.acceleratorPedalPosition) > 0) {
        if(tmpData.find("414B")!=string::npos && acceleratorPedalPosition > 0) {
            //MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "014B" };
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,014B";
            return;
        }
    }
    else if(pid.find("0111")!=string::npos){
        SensorsDataHandler(tmpData,pid);
        if(tmpData.find("4111")!=string::npos && acceleratorPedalPosition > 0) {
            pids ="0105,010B,010C,010D,010F,0110,0142,0146,0111";
            return;
        }
    }
    /*tmpData = BluetoothService.getData("0111");
     if(tmpData.contains("4111")) {
     MAIN_ACTIVITY_PIDS = new String[] { "0105", "010B", "010C", "010D", "010F", "0110", "0111" };
     return;
     }*/
}


string SensorsService::analysisDTC(string data)
{
    string retValue = "";
    string firstValue = data.substr(0, 1);
    //if(tmpValue.compare("0000")!=0){
    if (firstValue.compare("0")==0)
    {
        retValue = "P0" + data.substr(1);
        powertrainDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("1")==0)
    {
        retValue = "P1" + data.substr(1);
        powertrainDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("2")==0)
    {
        retValue = "P2" + data.substr(1);
        powertrainDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("3")==0)
    {
        retValue = "P3" + data.substr(1);
        powertrainDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("4")==0)
    {
        retValue = "C0" + data.substr(1);
        chassisDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("5")==0)
    {
        retValue = "C1" + data.substr(1);
        chassisDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("6")==0)
    {
        retValue = "C2" + data.substr(1);
        chassisDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("7")==0)
    {
        retValue = "C3" + data.substr(1);
        chassisDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("8")==0)
    {
        retValue = "B0" + data.substr(1);
        bodyDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("9")==0)
    {
        retValue = "B1" + data.substr(1);
        bodyDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("A")==0)
    {
        retValue = "B2" + data.substr(1);
        bodyDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("B")==0)
    {
        retValue = "B3" + data.substr(1);
        bodyDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("C")==0)
    {
        retValue = "U0" + data.substr(1);
        networkDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("D")==0)
    {
        retValue = "U1" + data.substr(1);
        networkDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("E")==0)
    {
        retValue = "U2" + data.substr(1);
        networkDTCList.push_back(retValue);
    }
    else if  (firstValue.compare("F")==0)
    {
        retValue = "U3" + data.substr(1);
        networkDTCList.push_back(retValue);
    }
    
    return retValue;
    
}



// Android getDtcData
vector<string> SensorsService::dtcs(string data ) {
    //String data = BluetoothService.getData("03");
    //if(null == data || "".equals(data)) return null;
    vector<string> valueList;
    if(data.empty()) return valueList;
    
    //System.out.println("--------------->>>>" + data);
    data = replace_all_distinct(data ,">","");
    //data = data.replace(">", "").trim();
    //    list<string> valueList = new ArrayList<string>();
    
    data.erase(0,data.find_first_not_of("\r\t\n "));
    data.erase(data.find_last_not_of("\r\t\n ")+1);
    
    data = replace_all_distinct(data ,"\r","@");
    vector<string> arr =split_for_string(data,"@");
    //vector<string> arr = data.replace("\r", "@").split("@");
    string tmpStr = "";
    if (arr.size() > 0 && arr[0].length() < 14) {
        
        if (arr.size() == 1) {
            tmpStr = arr[0];
            //tmpStr = tmpStr.substring(4);
            tmpStr=tmpStr.erase(0,4);
            int num = tmpStr.length() / 4;
            for (int i = 0; i < num; i++) {
                string tmpValue =tmpStr.substr(i*4,4);
                //String tmpValue = tmpStr.substring(i * 4, i * 4 + 4);
                //if (!"0000".equals(tmpValue)) {
                if(tmpValue.compare("0000")!=0){
                    // valueList.add(analysisDTC(tmpValue));
                    valueList.push_back(analysisDTC(tmpValue));
                }
            }
        } else {
            for (vector<string *>::size_type  i = 1; i < arr.size(); i++) {
                //0:430320290082
                tmpStr += arr[i].substr(arr[i].find_first_of(":") + 1);
            }
            
            // tmpStr = tmpStr.substring(4);
            tmpStr= tmpStr.erase(0,4);
            int num = tmpStr.length() / 4;
            for (int i = 0; i < num; i++) {
                //   string tmpValue = tmpStr.substring(i * 4, i * 4 + 4);
                string tmpValue = tmpStr.substr(i * 4,  4);
                if(tmpValue.compare("0000")!=0){
                    // valueList.add(analysisDTC(tmpValue));
                    valueList.push_back(analysisDTC(tmpValue));
                }
            }
        }
    } else {
        for (vector<string *>::size_type  i = 0; i < arr.size(); i++) {
            //string tmpStr = arr[i].substring(2, arr[i].length());
            string tmpStr = arr[i].erase(0,2);
            for (int j = 0; j < 3; j++) {
                string tmpValue = tmpStr.substr(i * 4,  4);
                if(tmpValue.compare("0000")!=0){
                    valueList.push_back(analysisDTC(tmpValue));
                }
            }
        }
    }
    
    /*for (int i = 0; i < valueList.size(); i++) {
     System.out.println(valueList.get(i) + " - ");
     }*/
    
    
    return valueList;
}



/**
 *
 * @param stopTimeCount 停车时间
 * @return 百公里怠速油耗
 */
void SensorsService::calculateIdlingFuel(double stopTimeCount) {
    /**
     * 说明：
     * literAvg：60时速5-100次瞬时油耗平均值，用X代替 (值越大，百公里怠速油耗越大)
     * stopTimeCount：怠速（停车）时间，单位秒 (值越大，百公里怠速油耗越大)
     * dist：行驶里程，单位公里 (值越大，百公里怠速油耗越小)
     *
     * 公式：
     * X / 3600 * stopTimeCount(停车时间) / dist(当次行驶里程) * 100
     * 每秒怠速油耗 = X / 3600
     * 总怠速油耗 = 每秒怠速油耗 * 停车时间(stopTimeCount)
     * 每公里怠速油耗 = 总怠速油耗 / 行驶里程(dist)
     * 百公里怠速油耗 = 每公里怠速油耗 * 100
     */
    
    // 如果 avgLiter 小于等于1或大于等于15时，将avgLiter设置为5
    if (literAvg <= 1 || literAvg >= 15) {
        literAvg = 5;
    }
    
    double km100StopLiter = 0;
    double tmpDist = 0;
    
    if(stopTimeCount > 0) {
        // 每秒油耗 = X / 3600秒
        double sLiter = literAvg / 3600;
        
        // 总怠速油耗 = 每秒油耗 * 停车时间
        double totalStopAvg = sLiter * stopTimeCount;
        
        // 每公里怠速油耗 = 总怠速油耗 / 行驶里程
        tmpDist = dist;
        if (tmpDist <= 0) tmpDist = 0.1;
        
        double kmStopLiter = totalStopAvg / tmpDist;
        
        // 百公里怠速油耗 = 每公里怠速油耗 * 100
        km100StopLiter = kmStopLiter * 100;
    }
    
    if(km100StopLiter < 0) km100StopLiter = 0;
    if(km100StopLiter >= 99.9) km100StopLiter = 99.9;
    
    // 怠速油耗人为调高一倍 *************
    idlingFuel = (km100StopLiter / idlingRatio1) * 2;
    if(vehicleSpeed <= 0)
        instantFuel = literAvg ;
    if(vehicleSpeed <= 0 && engineRpm <= 0)
        instantFuel = 0.0;
    
    /**
     * 自定义怠速油耗
     * 1、如果时速>0 && <10
     * 2、转速>0
     */
    if(vehicleSpeed > 0 && vehicleSpeed <= 10 && engineRpm > 0) {
        // 老算法
        /*double tmpRatio = 0.45D;
         double tmpCustomIdlingFuel = tmpRatio / 100;
         customIdlingFuel += tmpCustomIdlingFuel;*/
        
        customIdlingFuel = km100StopLiter / idlingRatio2;
    }
}


/**
 * 获取本次行车里程
 * */
double SensorsService::calculateTravelTotalDistance() {
    double tmpDist = vehicleSpeed * 1.0 / 3600;
    dist += tmpDist;
    
    return dist;
}

double SensorsService::calculateVehicleSpeedAvg() {
    vehicleSpeedTotal += vehicleSpeed;
    avgVehicleSpeed = vehicleSpeedTotal / totalTime;
    return avgVehicleSpeed;
}

double SensorsService::calculateAcceleratorPedalPositionAvg()
{
    acceleratorPedalPositionTotal += acceleratorPedalPosition;
    avgAcceleratorPedalPosition = acceleratorPedalPositionTotal / totalTime;
    return avgAcceleratorPedalPosition;
}

double SensorsService::engineRpmTotal=0.0;
double SensorsService ::avgEngineRpm=0.0;
double SensorsService::calculateEngineRpmAvg()
{
    engineRpmTotal += engineRpm;
    avgEngineRpm = engineRpmTotal / totalTime;
    return avgEngineRpm;
}
int SensorsService::calculateTraveDataCount=0;//计算次数
int SensorsService::matchVehicleSpeedCount=0;//符合时速大于60的次数
double SensorsService::tmptotalFuel=0;
bool SensorsService::isCalculate=false;//是否需要计算

//int calculateTraveDataCount=0;//计算次数
//int matchVehicleSpeedCount=0;//符合时速大于60的次数
//double tmptotalFuel=0;
//bool isCalculate=false;//是否需要计算
void SensorsService::calculateTraveData()
{
    // totalTime大于0，已行车状态
    if(totalTime>0)
    {
        totalTime++;
    }
    // 判断是否处于行车状态
    if(engineRpm>0||vehicleSpeed>0||engineCoolant>0||map>0||airFlowRate>0||intakeTemp>0)
    {
        if(vehicleSpeed>0) travelTime++;
        if(totalTime<=0)
            totalTime++;
    }
    // 如果totalTime大于0，表示汽车已开始行车或汽车已发动
    if(totalTime > 0 && (engineRpm > 0 || vehicleSpeed > 0 || engineCoolant > 0 ||
                         map > 0 || airFlowRate > 0 || intakeTemp > 0))
    {
        if(vehicleSpeed <= 0)
        {
            stopTime++;
        }
        if(calculateTraveDataCount==0&&literAvg==5)
        {
            isCalculate=true;
        }
        // 计算怠速油耗
        calculateIdlingFuel(stopTime);
        // 计算此次行车总里程
        calculateTravelTotalDistance();
        // 计算油耗
        calculateFuel();
        // 计算时速平均值
        calculateVehicleSpeedAvg();
        // 计算油门平均值
        calculateAcceleratorPedalPositionAvg();
        // 计算转速平均值
        calculateEngineRpmAvg();
        
        calculateTraveDataCount++;
        if(isCalculate&&vehicleSpeed>=55&&vehicleSpeed<=65)
        {
            matchVehicleSpeedCount++;
            if(usaLiter>0)
            {
                tmptotalFuel+=usaLiter;
            }
            else
            {
                tmptotalFuel+=usaLiter2;
            }
            if(matchVehicleSpeedCount>=5)
            {
                literAvg=tmptotalFuel/matchVehicleSpeedCount;
            }
        }
    }
}

/**
 * 体检：初始化参数
 *
 * */
void SensorsService::Car_CheckUp_init()
{
    DtcCount=0;
    powertrainDTCList.clear();
    chassisDTCList.clear();
    bodyDTCList.clear();
    networkDTCList.clear();
    DrivingDataUnusualCount=0;
    DrivingDataNormalCount=0;
    
}

/**
 * 体检：故障码检测
 * 返回值：-1： 发送03得到的string 为空
 * 		 故障码个数：根据发送03得到的string解析出来故障码个数
 * */
int SensorsService::DTC_CheckUp(string data )
{
    DtcCount=0;
    powertrainDTCList.clear();
    chassisDTCList.clear();
    bodyDTCList.clear();
    networkDTCList.clear();
    
    if(data.empty()) return -1;
    
    DtcCount=dtcs(data).size();
    return DtcCount;
}

/**
 * 体检：发动机工况
 * 返回值：
 *
 * */
vector<int>  SensorsService::EngineBehaviour_CheckUp(string data)
{//"失火系统","燃油系统","蒸汽排放物控制系统","A / C制冷剂","氧传感器"，"废气再循环系统"
    vector<int> result;
    for(int i=0;i<6;i++)
    {
        result.push_back(-1);
    }
    int dataB=0,dataC=0,dataD=0;
    vector<string> arr= dataHandler(data,false);
    int dataCount=arr.size();
    if(dataCount>1)
        sscanf(arr[1].c_str(),"%x",&dataB);
    if(dataCount>=2)
    {
        sscanf(arr[1].c_str(),"%x",&dataB);
        if((dataB&17)==1)	{
            result[0]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[0]=0;
            DrivingDataUnusualCount++;
        }
        if((dataB&18)==2){
            result[1]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[1]=0;
            DrivingDataUnusualCount++;
        }
    }else
    {
        DrivingDataNormalCount+=2;
    }
    if(dataCount>=3)
    {
        sscanf(arr[2].c_str(),"%x",&dataC);
        sscanf(arr[3].c_str(),"%x",&dataD);
        if((dataC&2)==2&&(dataD&2)==0){
            result[2]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[2]=0;
            DrivingDataUnusualCount++;
        }
        if((dataC&16)==16&&(dataD&16)==0){
            result[3]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[3]=0;
            DrivingDataUnusualCount++;
        }
        if((dataC&32)==32&&(dataD&32)==0){
            result[4]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[4]=0;
            DrivingDataUnusualCount++;
        }
        if(((dataC&128)==128)&&(dataD&128)==0){
            result[5]=1;
            DrivingDataNormalCount++;
        }
        else{
            result[5]=0;
            DrivingDataUnusualCount++;
        }
    }else
    {
        DrivingDataNormalCount+=4;
    }
    return result;
}

int SensorsService::isNormal(double tmpNum,double maxNum,double MinNum)
{
    if(tmpNum<=maxNum&&tmpNum>=MinNum)
    {
        DrivingDataNormalCount++;
        return 1;
    }
    else
    {
        DrivingDataUnusualCount++;
        return 0;
    }
}
void SensorsService::setCarCheckUpValue(int i,double Value,double MaxValue,double MinValue)
{
    if(i<MAXNUM)
    {
        CarCheckUp_Value_list[i]=Value;
        CarCheckUp_MaxValue_list[i]=MaxValue;
        CarCheckUp_MinValue_list[i]=MinValue;
    }
    else
    {
        printf("赋值出错！");
    }
}
void SensorsService::setCarCheckUpValue(int i,double MaxValue,double MinValue)
{
    if(i<MAXNUM)	{
        
        CarCheckUp_MaxValue_list[i]=MaxValue;
        CarCheckUp_MinValue_list[i]=MinValue;
    }
    else
    {
        printf("赋值出错！");
    }
}

void SensorsService::setCarCheckUpValue(int i,double Value)
{
    if(i<MAXNUM)
    {
        CarCheckUp_Value_list[i]=Value;
        
    }
    else
    {
        printf("赋值出错！");
    }
}
/**
 * 体检：行车数据
 * 返回值：-1：无值
 *		   0：异常
 * 		   1：正常
 * */
int  SensorsService::DrivingData_CheckUp(string sensorsData,string pid)
{
    if( sensorsData.empty()||sensorsData.length() == 0||sensorsData.find("NO DATA")!= string::npos||sensorsData.find("CAN ERROR")!= string::npos||
       sensorsData.find("STOPPED")!= string::npos||sensorsData.find("BUS INIT:")!= string::npos)
    {
        //自发动机启动的时间、故障码清楚后行驶公里数、相对节气门位置、绝对节气门位置B、绝对节气门位置C
        //加速踏板位置D、加速踏板位置E、加速踏板位置F、节气门执行器控制指令、故障码清楚后时间
        //if(pid.find("010F")!=string::npos||pid.find("0131")!=string::npos||pid.find("0145")!=string::npos||
        //	pid.find("0147")!=string::npos||pid.find("0148")!=string::npos||pid.find("0149")!=string::npos||pid.find("014A")!=string::npos||
        //	pid.find("014B")!=string::npos||pid.find("014C")!=string::npos||pid.find("014E")!=string::npos)
        //{
        //	DrivingDataUnusualCount++;
        //	return 0;
        //}
        DrivingDataVoidCount++;
        return -1;
        
    }
    int data1=0;
    int data2=0;
    double tmpNum=0;
    if(sensorsData.find("410")!= string::npos&&pid.find("010")!=string::npos){
        
        /******************** 0104-- 010F 开始********************/
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()==0)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        if(sensorsData.find("4104")!= string::npos&&pid.find("0104")!=string::npos){	//计算发动机负荷值
            tmpNum=(data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0104"],tmpNum,75,15);
            //return isNormal(tmpNum,75,15);
            int i= Car_CheckUp_location["0104"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        
        else if(sensorsData.find("4105")!= string::npos&&pid.find("0105")!=string::npos){		//发动机冷却液温度
            tmpNum=(data1 - 40);
            //setCarCheckUpValue(Car_CheckUp_location["0105"],tmpNum,185,-39);
            //return isNormal(tmpNum,185,-39);
            int i= Car_CheckUp_location["0105"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4106")!= string::npos&&pid.find("0106")!=string::npos){		//短时燃料补偿值（气缸组1)
            tmpNum=((data1 - 128) * 100 / 128);
            //setCarCheckUpValue(Car_CheckUp_location["0106"],tmpNum,10,-10);
            //return isNormal(tmpNum,10,-10);
            int i= Car_CheckUp_location["0106"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4107")!= string::npos&&pid.find("0107")!=string::npos){			//长时燃料补偿值（气缸组1)
            tmpNum=((data1 - 128) * 100 / 128);
            //setCarCheckUpValue(Car_CheckUp_location["0107"],tmpNum,16,-23);
            //return isNormal(tmpNum,16,-23);
            int i= Car_CheckUp_location["0107"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4108")!=string::npos&&pid.find("0108")!=string::npos){		//短时燃料补偿值（气缸组2）
            tmpNum=((data1 - 128) * 100 / 128);
            //setCarCheckUpValue(Car_CheckUp_location["0108"],tmpNum,10,-10);
            //return isNormal(tmpNum,10,-10);
            int i= Car_CheckUp_location["0108"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
            
        }
        else if(sensorsData.find("4109")!=string::npos&&pid.find("0109")!=string::npos){			//长时燃料补偿值（气缸组2）
            tmpNum=((data1 - 128) * 100 / 128);
            //setCarCheckUpValue(Car_CheckUp_location["0109"],tmpNum,16,-23);
            //return isNormal(tmpNum,16,-23);
            int i= Car_CheckUp_location["0109"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410A")!= string::npos&&pid.find("010A")!=string::npos){			//燃料压力
            tmpNum=(data1 * 3);
            //setCarCheckUpValue(Car_CheckUp_location["010A"],tmpNum,800,0);
            //return isNormal(tmpNum,800,0);
            int i= Car_CheckUp_location["010A"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410B")!= string::npos&&pid.find("010B")!=string::npos){		//进气歧管绝对压力
            tmpNum=data1;
            //setCarCheckUpValue(Car_CheckUp_location["010B"],tmpNum,300,0);
            //return isNormal(tmpNum,300,0);
            int i= Car_CheckUp_location["010B"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410C")!= string::npos&&pid.find("010C")!=string::npos){			//发动机转速
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=(((data1 * 256) + data2) / 4);
            //setCarCheckUpValue(Car_CheckUp_location["010C"],tmpNum,8000,0);
            //return isNormal(tmpNum,8000,0);
            int i= Car_CheckUp_location["010C"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410D")!= string::npos&&pid.find("010D")!=string::npos){		//车辆速度
            tmpNum = data1;
            //setCarCheckUpValue(Car_CheckUp_location["010D"],tmpNum,180,0);
            //return isNormal(tmpNum,180,0);
            int i= Car_CheckUp_location["010D"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410E")!= string::npos&&pid.find("010E")!=string::npos){			//1气缸点火时提前角
            tmpNum = (data1 / 2 - 64) ;
            //setCarCheckUpValue(Car_CheckUp_location["010E"],tmpNum,60,10);
            //return isNormal(tmpNum,60,10);
            int i= Car_CheckUp_location["010E"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("410F")!= string::npos&&pid.find("010F")!=string::npos){			//进气温度
            tmpNum = (data1 - 40) ;
            //setCarCheckUpValue(Car_CheckUp_location["010F"],tmpNum,184,-39);
            //return isNormal(tmpNum,184,-39);
            int i= Car_CheckUp_location["010F"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else {
            DrivingDataVoidCount++;
            return -1;
        }
        
        
        
        
        
        /******************** 0104-- 010F 结束********************/
        
    }
    else if(sensorsData.find("411")!= string::npos&&pid.find("011")!=string::npos){
        
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()==0)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        
        /******************** 0110-- 011F 开始********************/
        if(sensorsData.find("4110")!= string::npos&&pid.find("0110")!=string::npos){      //空气流量速率
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum = (((data1 * 256) + data2) / 100) ;
            //setCarCheckUpValue(Car_CheckUp_location["0110"],tmpNum,800,0);
            //return isNormal(tmpNum,800,0);
            int i= Car_CheckUp_location["0110"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4111")!=string::npos&&pid.find("0111")!=string::npos){       //节气门位置
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0111"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["0111"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find("411F")!=string::npos&&pid.find("011F")!=string::npos){		//自发动机启动的时间
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=(data1*256)+data2;
            //setCarCheckUpValue(Car_CheckUp_location["011F"],tmpNum,65535,0);
            //return isNormal(tmpNum,65535,0);
            int i= Car_CheckUp_location["011F"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else {
            DrivingDataVoidCount++;
            return -1;
        }
        /******************** 0110-- 011F 结束********************/
        
    }
    else if(sensorsData.find("412")!= string::npos&&pid.find("012")!=string::npos){
        
        
        
        /******************** 0121-- 0122 开始********************/
        
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()<=1)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        sscanf(arr[1].c_str(),"%x",&data2);
        
        if(sensorsData.find("4121")!=string::npos&&pid.find("0121")!=string::npos){      //在故障指示灯激活状态下行驶的里程
            tmpNum=(data1*256)+data2;
            //setCarCheckUpValue(Car_CheckUp_location["0121"],tmpNum,0,0);
            //return isNormal(tmpNum,0,0);
            int i= Car_CheckUp_location["0121"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find ("4122")!=string::npos&&pid.find("0122")!=string::npos){       //油轨压力（相对于歧管真空）
            tmpNum=((data1*256)+data2) * 0.079;
            //setCarCheckUpValue(Car_CheckUp_location["0122"],tmpNum,80,0);
            //return isNormal(tmpNum,80,0);
            int i= Car_CheckUp_location["0122"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else
        {
            DrivingDataVoidCount++;
            return -1;
        }
        /******************** 0121-- 0122 结束********************/
        
    }
    else if(sensorsData.find("413")!= string::npos&&pid.find("013")!=string::npos){
        
        /******************** 0131-- 013F 开始********************/
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()<=0)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        if(sensorsData.find("4131")!=string::npos&&pid.find("0131")!=string::npos){	   //故障码清楚后行驶公里数
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=(data1*256)+data2;
            //setCarCheckUpValue(Car_CheckUp_location["0131"],tmpNum,65535,0);
            //return isNormal(tmpNum,65535,0);
            int i= Car_CheckUp_location["0131"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4133")!=string::npos&&pid.find("0133")!=string::npos){		//大气压
            tmpNum=data1;
            //setCarCheckUpValue(Car_CheckUp_location["0133"],tmpNum,125,10);
            //return isNormal(tmpNum,125,10);
            int i= Car_CheckUp_location["0133"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }/*
          else if(sensorsData.find("413C")!=string::npos||sensorsData.find("413D")!=string::npos||
          sensorsData.find("413E")!=string::npos||sensorsData.find("413F")!=string::npos){		//催化剂温度
          sscanf(arr[1].c_str(),"%x",&data2);
          tmpNum=((data1*256)+data2)*1.0/10 - 40;
          //return isNormal(tmpNum,125,10);
          }*/
        else
        {
            DrivingDataVoidCount++;
            return -1;
        }
        
        /******************** 0131-- 013F 结束********************/
        
    }
    else if(sensorsData.find("414")!= string::npos&&pid.find("014")!=string::npos){
        
        /******************** 0142-- 014E 开始********************/
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()<=0)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        if (sensorsData.find("4142")!=string::npos&&pid.find("0142")!=string::npos){	//控制模块电压
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=((data1*256)+data2)*1.0/1000;
            //setCarCheckUpValue(Car_CheckUp_location["0142"],tmpNum,60,0);
            //return isNormal(tmpNum,60,0);
            int i= Car_CheckUp_location["0142"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4145")!=string::npos&&pid.find("0145")!=string::npos){
            //相对节气门位置
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0145"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["0145"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find("4146")!=string::npos&&pid.find("0146")!=string::npos){     //环境空气温度
            tmpNum=data1-40;
            //setCarCheckUpValue(Car_CheckUp_location["0146"],tmpNum,120,-40);
            //return isNormal(tmpNum,120,-40);
            int i= Car_CheckUp_location["0146"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4147")!=string::npos&&pid.find("0147")!=string::npos){
            //绝对节气门位置B
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0147"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["0147"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4148")!=string::npos&&pid.find("0148")!=string::npos){
            //加速踏板位置C
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0148"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["0148"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4149")!=string::npos&&pid.find("0149")!=string::npos){
            //加速踏板位置D
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["0149"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["0149"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("414A")!=string::npos&&pid.find("014A")!=string::npos){
            //加速踏板位置E
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["014A"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["014A"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("414B")!=string::npos&&pid.find("014B")!=string::npos){
            //加速踏板位置F
            tmpNum = (data1 * 100 / 255);
            //setCarCheckUpValue(Car_CheckUp_location["014B"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["014B"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find("414C")!=string::npos&&pid.find("014C")!=string::npos){   //节气门执行器控制指令
            tmpNum=data1*100.0/255;
            //setCarCheckUpValue(Car_CheckUp_location["014C"],tmpNum,100,0);
            //return isNormal(tmpNum,100,0);
            int i= Car_CheckUp_location["014C"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find("414D")!=string::npos&&pid.find("014D")!=string::npos){     //故障指示灯激活状态下发动机的运转时间
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=(data1*256)+data2;
            //setCarCheckUpValue(Car_CheckUp_location["014D"],tmpNum,0,0);
            //return isNormal(tmpNum,0,0);
            int i= Car_CheckUp_location["014D"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if (sensorsData.find("414E")!=string::npos&&pid.find("014E")!=string::npos){   //故障码清楚后时间
            if(arr.size()<=1)
            {
                DrivingDataVoidCount++;
                return -1;
            }
            sscanf(arr[1].c_str(),"%x",&data2);
            tmpNum=(data1*256)+data2;
            //setCarCheckUpValue(Car_CheckUp_location["014E"],tmpNum,65535,0);
            //return isNormal(tmpNum,65535,0);
            int i= Car_CheckUp_location["014E"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else
        {
            DrivingDataVoidCount++;
            return -1;
        }
        /******************** 0142-- 014E 结束********************/
        
    }
    else if(sensorsData.find("415")!= string::npos&&pid.find("015")!=string::npos){
        
        /******************** 0151-- 0159 开始********************/
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()<=1)
        {
            DrivingDataVoidCount++;
            return -1;
        }
        sscanf(arr[0].c_str(),"%x",&data1);
        sscanf(arr[1].c_str(),"%x",&data2);
        if(sensorsData.find("4154")!=string::npos&&pid.find("0154")!=string::npos){		//蒸气压力
            tmpNum=((data1*256)+data2)-32767;
            //setCarCheckUpValue(Car_CheckUp_location["0154"],tmpNum,65535,0);
            //return isNormal(tmpNum,65535,0);
            int i= Car_CheckUp_location["0154"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else if(sensorsData.find("4159")!=string::npos&&pid.find("0159")!=string::npos){		//燃油绝对压力
            tmpNum=((data1*256)+data2) * 10;
            //setCarCheckUpValue(Car_CheckUp_location["0159"],tmpNum,655350,0);
            int i= Car_CheckUp_location["0159"];
            setCarCheckUpValue(i,tmpNum);
            return isNormal(tmpNum,CarCheckUp_MaxValue_list[i],CarCheckUp_MinValue_list[i]);
        }
        else
        {
            DrivingDataVoidCount++;
            return -1;
        }
        /******************** 0151-- 0159 结束********************/
        
    }
    else
    {
        DrivingDataVoidCount++;
        return -1;
    }
    
}


/**
 * 体检：体检得分
 * 返回值：60   显示得分为：低于60分
 * 		 >60   显示得分为： 具体返回分值
 * */
int  SensorsService::Car_CheckUp_score()
{
    if(DrivingDataUnusualCount==0&&DrivingDataNormalCount==0&&DrivingDataVoidCount==0)
    {
        return 0;
    }
    else if(DtcCount>0||DrivingDataUnusualCount>=20)
    {
        return 60;
    }
    else
    {
        return 100-2*DrivingDataUnusualCount;
    }
}

//double SensorsService::beforeVehicleSpeed=-1;
/**
 *判断现在是否为停车状态
 **/
bool nowVehicleStop(int i,int count,int max)
{
    int tempI=0;
    for(int j=0;j<count;j++)
    {
        tempI=(i-j+max)%max;
        if(SensorsService::beforeVehicleSpeed[tempI]!=0)
            break;
        else if(j==count-1)
            return true;
    }
    return false;
}
/**
 *判断2秒降幅是否达到50Km/h
 **/
bool CarTiresSqel(int i,int count,int max)
{
    for(int j=0,tempI=0,tempJ=0;j<max;j++)
    {
        tempI=(i-j+max)%max;
        for(int k=0;k<count&&j+k<max;k++)
        {
            tempJ=(tempI-k+max)%max;
            if(SensorsService::beforeVehicleSpeed[tempJ]-SensorsService::beforeVehicleSpeed[tempI]>=50)
            {
                //SensorsService::arrayEnd=SensorsService::vehicleImpactCount;
                SensorsService::bfImpactVehicleSpeed=SensorsService::beforeVehicleSpeed[tempJ];
                SensorsService::afImapactVehicleSpeed=SensorsService::beforeVehicleSpeed[tempI];
                SensorsService::arrayEnd=SensorsService::arrayHead;
                SensorsService::beginCalculate=false;
                return true;
            }
        }
    }
    return false;
}
/**
 * 汽车碰撞判断
 * 返回值 0:没有发生碰撞
 *		 1：发生碰撞（符合现有碰撞条件）
 **/
int SensorsService::vehicleImpact()
{
    /*int data1=0;
     int data2=0;
     
     if(beforeVehicleSpeed>=60&&beforeVehicleSpeed-vehicleSpeed>=50)
     {
     beforeVehicleSpeed=vehicleSpeed;
     return 1;
     }
     beforeVehicleSpeed=vehicleSpeed;
     return 0;*/
    //vehicleImpactCount++;
    arrayHead++;
    int maxCount=beforeVehicleSpeed.size();
    //vehicleImpactCount =vehicleImpactCount%maxCount;
    arrayHead=arrayHead%maxCount;
    if(!beginCalculate&&arrayHead==arrayEnd)
    {
        beginCalculate=true;
    }
    
    beforeVehicleSpeed[arrayHead]=vehicleSpeed;
    if(beginCalculate)
    {
        //当前时速为0
        if(nowVehicleStop(arrayHead,3,maxCount)&&CarTiresSqel(arrayHead,2,maxCount))
        {
            return 1;
        }
        //10秒内有急刹车现象
        
    }
    return 0;
}

/**
 * 汽车碰撞初始化
 **/
//int SensorsService::vehicleImpactCount=0;  //调用次数
int SensorsService::arrayHead=0;
int SensorsService::arrayEnd=0;
bool SensorsService::beginCalculate=false;//时速是否已经有10个
vector<double> SensorsService::beforeVehicleSpeed(10,-1); //10个时速数据
double SensorsService::bfImpactVehicleSpeed=0;
double SensorsService::afImapactVehicleSpeed=0;
void SensorsService::vehicleImpactInit()
{
    //vehicleImpactCount=0;
    arrayHead=0;
    arrayEnd=0;
    bfImpactVehicleSpeed=-1;
    afImapactVehicleSpeed=-1;
    beginCalculate=false;
    for(int i=0;i<10;i++)
        beforeVehicleSpeed[i]=-1;
}

double SensorsService::analysisAcceleratorPedal(string sensorsData ,string pid )
{
    if( sensorsData.empty()||sensorsData.length() == 0||sensorsData.find("NO DATA")!= string::npos||sensorsData.find("CAN ERROR")!= string::npos||sensorsData.find("STOPPED")!= string::npos||sensorsData.find("BUS INIT:")!= string::npos)
        return 0;
    int data1=0;
    int data2=0;
    if((sensorsData.find("4111")!=string::npos&&pid.find("0111")!=string::npos)||(sensorsData.find("415A")!=string::npos&&pid.find("015A")!=string::npos)||
       (sensorsData.find("4145")!=string::npos&&pid.find("0145")!=string::npos)||(sensorsData.find("4147")!=string::npos&&pid.find("0147")!=string::npos)||
       (sensorsData.find("4148")!=string::npos&&pid.find("0148")!=string::npos)||(sensorsData.find("4149")!=string::npos&&pid.find("0149")!=string::npos)||
       (sensorsData.find("414A")!=string::npos&&pid.find("014A")!=string::npos)||(sensorsData.find("414B")!=string::npos&&pid.find("014B")!=string::npos)){
        vector<string> arr = dataHandler(sensorsData,false);
        if(arr.size()>0)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            return (data1 * 100*1.0 / 255);				
        }
    }
    else if(sensorsData.find("410C")!= string::npos&&pid.find("010C")!=string::npos){
        vector<string> arr= dataHandler(sensorsData,false);
        if(arr.size()>1)
        {
            sscanf(arr[0].c_str(),"%x",&data1);
            sscanf(arr[1].c_str(),"%x",&data2);
            return (((data1 * 256) + data2) / 4.0);			
        }
    }
    return 0;
    
}
