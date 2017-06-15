#include <jni.h>
#include <string>

#include "SensorsService.cpp"
//#include "SensorsService.cpp"
/* Header for class com_auto_service_SensorsService */







extern "C"
{


JNIEXPORT jstring JNICALL
Java_com_qcwp_carmanager_ui_MainActivity_stringFromJNI(JNIEnv *env, jobject instance) {


    return env->NewStringUTF("哈哈哈");
}


JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_MAIN_1ACTIVITY_1PIDS  (JNIEnv * env,jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::pids.c_str());
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_engineLoadValue   (JNIEnv * env,jclass obj){
    return SensorsService::engineLoadValue;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_engineCoolant (JNIEnv * env,jclass obj){
    return SensorsService::engineCoolant;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_lTFuelTrim(JNIEnv * env,jclass obj){
    return SensorsService::lTFuelTrim;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_sTFuelTrim(JNIEnv * env,jclass obj){
    return SensorsService::sTFuelTrim;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_fuelPressure(JNIEnv * env,jclass obj){
    return SensorsService::fuelPressure;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_map(JNIEnv * env,jclass obj){
    return SensorsService::map;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_engineRpm__D(JNIEnv *env, jclass obj, jdouble engineRpm){
    SensorsService::engineRpm =engineRpm;
    return SensorsService::engineRpm;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_engineRpm__(JNIEnv * env,jclass obj){
    return SensorsService::engineRpm;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxEngineRpm__ (JNIEnv *env, jclass obj){
    return SensorsService::maxEngineRpm;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxEngineRpm__D(JNIEnv *env , jclass obj, jdouble temp){
    SensorsService::maxEngineRpm=temp;
    return SensorsService::maxEngineRpm;
}

JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_engineRpmAve(JNIEnv *env, jclass)
{
    return SensorsService::avgEngineRpm;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeed
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeed__
        (JNIEnv *env, jclass obj){
    return SensorsService::vehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeed
* Signature: (D)Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeed__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::vehicleSpeed=temp;
    return SensorsService::vehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeedAve
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeedAve__
        (JNIEnv *env, jclass obj){
    return SensorsService::avgVehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeedAve
* Signature: (D)Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeedAve__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::avgVehicleSpeed=temp;
    return SensorsService::avgVehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    maxVehicleSpeed
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxVehicleSpeed__
        (JNIEnv *env, jclass obj){
    return SensorsService::maxVehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    maxVehicleSpeed
* Signature: (D)Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxVehicleSpeed__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::maxVehicleSpeed=temp;
    return SensorsService::maxVehicleSpeed;
}
/*
//根据 rule 来改变 double的 小数点后的位数
double GetDoubleOfInteger(double number,char* rule)
{
double tmp=0.0;
sscanf((cdoubleToJstring(number,rule).c_str()),"%f",tmp);
return tmp;
}
*/


/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeedCount
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeedCount__
        (JNIEnv *env, jclass obj){
    //double tmpMaxEngineRpm;
    //sscanf_s((cdoubleToJstring(SensorsService::maxEngineRpm,"%.0f").c_str()),"%f",tmpMaxEngineRpm);
    return SensorsService::vehicleSpeedTotal;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleSpeedCount
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleSpeedCount__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::vehicleSpeedTotal=temp;
    return SensorsService::vehicleSpeedTotal;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    timingAdvance
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_timingAdvance
        (JNIEnv *env, jclass obj){
    return SensorsService::timingAdvance;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    intakeTemp
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_intakeTemp
        (JNIEnv *env, jclass obj){
    return SensorsService::intakeTemp;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    airFlowRate
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_airFlowRate
        (JNIEnv *env, jclass obj){
    return SensorsService::airFlowRate;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    acceleratorPedalPosition
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_acceleratorPedalPosition__
        (JNIEnv *env, jclass obj){
    return SensorsService::acceleratorPedalPosition;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    acceleratorPedalPosition
* Signature: (D)Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_acceleratorPedalPosition__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::acceleratorPedalPosition=temp;
    return SensorsService::acceleratorPedalPosition;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    acceleratorPedalPositionAvg
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_acceleratorPedalPositionAvg
        (JNIEnv *env, jclass obj){
    return SensorsService::avgAcceleratorPedalPosition;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    maxAcceleratorPedalPosition
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxAcceleratorPedalPosition__
        (JNIEnv *env, jclass obj){
    return SensorsService::maxAcceleratorPedalPosition;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    maxAcceleratorPedalPosition
* Signature: (D)Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_maxAcceleratorPedalPosition__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::maxAcceleratorPedalPosition=temp;
    return SensorsService::maxAcceleratorPedalPosition;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vinCode
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vinCode__
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::vinCode.c_str());
}
string JstringToCstring(JNIEnv *env,jstring temp)
{
    const char *str = env->GetStringUTFChars( temp, 0);
    string Cstr=str;
    env->ReleaseStringUTFChars(temp, str);
    return Cstr;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vinCode
* Signature: (Ljava/lang/String;)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vinCode__Ljava_lang_String_2
        (JNIEnv *env, jclass obj, jstring temp){

    SensorsService::vinCode=JstringToCstring(env,temp);
    return (jstring)env->NewStringUTF(SensorsService::vinCode.c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    vinCodeSourceData
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vinCodeSourceData
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::vinCodeSourceData.c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    calibrationId
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_calibrationId
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::calibrationId.c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    calibrationIdSourceData
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_calibrationIdSourceData
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::calibrationIdSourceData.c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    usaLiter
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_usaLiter
        (JNIEnv *env, jclass obj){
    return SensorsService::usaLiter;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    showUsaLiter
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_showUsaLiter
        (JNIEnv *env, jclass obj){
    return SensorsService::showUsaLiter;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    instantFuel
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_instantFuel
        (JNIEnv *env, jclass obj){
    //double tmp=GetDoubleOfInteger(SensorsService::instantFuel,"%.1f");
    return SensorsService::instantFuel;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    dist
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_dist__
        (JNIEnv *env, jclass obj){
    return SensorsService::dist;
    //return (jstring)env->NewStringUTF(cdoubleToJstring(SensorsService::dist,"%.1f").c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    dist
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_dist__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::dist= temp;
    //return (jstring)env->NewStringUTF(cdoubleToJstring(SensorsService::dist,"%.1f").c_str());
    return SensorsService::dist;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    beginMileage
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_beginMileage__
        (JNIEnv *env, jclass obj){
    //return (jstring)env->NewStringUTF(cdoubleToJstring(SensorsService::dist,"%.1f").c_str());
    return SensorsService::beginMileage;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    beginMileage
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_beginMileage__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::beginMileage=temp;
    return SensorsService::beginMileage;
}
/*
int CDoubleToCint(double number)
{
char temp[10];
sprintf(temp,"%.0f",number);
int tmp=0;
sscanf(temp,"%d",tmp);
return tmp;
}*/
/*
* Class:     com_auto_service_SensorsService
* Method:    totalTime
* Signature: ()I
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_totalTime
        (JNIEnv *env, jclass obj){
    //int tmp =  SensorsService::totalTime;

    return int(SensorsService::totalTime);
}


/*
* Class:     com_auto_service_SensorsService
* Method:    totalTime
* Signature: (I)I
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_totalTime__D
        (JNIEnv *env, jclass obj, jdouble temp)
{
    SensorsService::totalTime=temp;
    return int(SensorsService::totalTime);
}


/*
* Class:     com_auto_service_SensorsService
* Method:    idlingFuel
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_idlingFuel
        (JNIEnv *env, jclass obj){
    return SensorsService::idlingFuel;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    customIdlingFuel
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_customIdlingFuel
        (JNIEnv *env, jclass obj){
    return SensorsService::customIdlingFuel;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    literAvg
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_literAvg__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::literAvg=temp;
    return SensorsService::literAvg;
}
/*
* Class:     com_auto_service_SensorsService
* Method:    literAvg
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_literAvg__
        (JNIEnv *env, jclass obj){
    return SensorsService::literAvg;
}
/*
* Class:     com_auto_service_SensorsService
* Method:    f_value2_pre
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_f_1value2_1pre
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::f_value2_pre=temp;
    return SensorsService::f_value2_pre;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    f_value2
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_f_1value2
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::f_value2=temp;
    return SensorsService::f_value2;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    s_value2
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_s_1value2
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::s_value2=temp;
    return SensorsService::s_value2;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    idlingRatio1
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_idlingRatio1
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::idlingRatio1=temp;
    return SensorsService::idlingRatio1;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    idlingRatio2
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_idlingRatio2
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::idlingRatio2=temp;
    return SensorsService::idlingRatio2;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    pl
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_pl
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::pl=temp;
    return SensorsService::pl;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    testTotalTime
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_testTotalTime__
        (JNIEnv *env, jclass obj){
    return SensorsService::testTotalTime;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    testTotalTime
* Signature: (D)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_testTotalTime__D
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::testTotalTime=temp;
    return  SensorsService::testTotalTime;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    stopTime
* Signature: ()I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_stopTime__
        (JNIEnv *env, jclass obj){

    return  SensorsService::stopTime;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    stopTime
* Signature: (I)I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_stopTime__I
        (JNIEnv *env, jclass obj, jint tmp){
    SensorsService::stopTime=tmp;
    return  SensorsService::stopTime;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    travelTime
* Signature: ()I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_travelTime__
        (JNIEnv *env, jclass obj){
    return SensorsService::travelTime;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    travelTime
* Signature: (I)I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_travelTime__I
        (JNIEnv *env, jclass obj, jint tmp){
    SensorsService::travelTime=tmp;
    return SensorsService::travelTime;
}




/*
* Class:     com_auto_service_SensorsService
* Method:    initData
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_initData
        (JNIEnv *env, jclass obj){
    SensorsService::initData();
}

/*
* Class:     com_auto_service_SensorsService
* Method:    SensorsDataHandler
* Signature: (Ljava/lang/String;)V
*/
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_SensorsDataHandler
        (JNIEnv *env, jclass obj, jstring temp, jstring pid){

    SensorsService::SensorsDataHandler(JstringToCstring(env,temp),JstringToCstring(env,pid));
}

/*
* Class:     com_auto_service_SensorsService
* Method:    testing
* Signature: (D)V
*/
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_testing
        (JNIEnv *env, jclass obj, jdouble temp){
    SensorsService::testing(temp);
}

/*
* Class:     com_auto_service_SensorsService
* Method:    GetVinCode
* Signature: (Ljava/lang/String;)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_GetVinCode
        (JNIEnv *env, jclass obj, jstring temp){
    string results =	SensorsService::GetVinCode(JstringToCstring(env,temp));
    return (jstring)env->NewStringUTF(results.c_str());
}

/*
* Class:     com_auto_service_SensorsService
* Method:    setAccelerator
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_setAccelerator
        (JNIEnv *env, jclass obj,jstring data,jstring pid){

    string Cdata =JstringToCstring(env,data);
    string Cpid =JstringToCstring(env,pid);
    SensorsService::findRightAccelerator(Cdata,Cpid);
}

/*
* Class:     com_auto_service_SensorsService
* Method:    getDtcData
* Signature: ()Ljava/util/List;
*/
JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_getDtcData
        (JNIEnv *env, jclass obj ,jstring temp){
    string Cdata=JstringToCstring(env,temp);
    vector<string> results =SensorsService::dtcs(Cdata);


    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < results.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(results[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    calculateTraveData
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_calculateTraveData
        (JNIEnv *env, jclass obj){
    SensorsService::calculateTraveData();
}




//8.1

JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_powertrainDTCList
        (JNIEnv *env, jclass obj){

    //vector<string> results =SensorsService::dtcs(Cdata);
    //SensorsService::powertrainDTCList

    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < SensorsService::powertrainDTCList.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(SensorsService::powertrainDTCList[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}

JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_chassisDTCList
        (JNIEnv *env, jclass obj){
    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < SensorsService::chassisDTCList.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(SensorsService::chassisDTCList[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}

JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_bodyDTCList
        (JNIEnv *env, jclass obj){
    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < SensorsService::bodyDTCList.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(SensorsService::bodyDTCList[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}


JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_networkDTCList
        (JNIEnv *env, jclass obj){
    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < SensorsService::networkDTCList.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(SensorsService::networkDTCList[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}

JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DtcCount
        (JNIEnv *env , jclass obj){
    return SensorsService::DtcCount;
}

JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DrivingDataUnusualCount
        (JNIEnv *env , jclass obj ){
    return SensorsService::DrivingDataUnusualCount;
}
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DrivingDataNormalCount
        (JNIEnv *env, jclass obj){
    return SensorsService::DrivingDataNormalCount;
}

JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DrivingDataVoidCount
        (JNIEnv *env, jclass obj){
    return SensorsService::DrivingDataVoidCount;
}

JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1Pid_1List
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::CarCheckUp_Pid_List.c_str());
}
JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1PidDescription_1list
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::CarCheckUp_PidDescription_list.c_str());
}

JNIEXPORT jdoubleArray JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1Value_1list
        (JNIEnv *env, jclass obj){

    jsize len=SensorsService::CarCheckUp_Value_list.size();
    jdouble * abc= new jdouble [len];
    jdoubleArray  args  = (env)->NewDoubleArray(len);
    for(unsigned int i =0;i<len;i++)
    {
        //args[i]=SensorsService::CarCheckUp_Value_list[i];
        // env->SetDoubleArrayElement(ArrayA,i,env->GetDoubleArrayElement(ArrayB,j));
        abc[i]=SensorsService::CarCheckUp_Value_list[i];
    }
    env->SetDoubleArrayRegion(args,0,len,abc);
    return args;

}

JNIEXPORT jdoubleArray JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1MaxValue_1list
        (JNIEnv *env, jclass obj){

    jsize len=SensorsService::CarCheckUp_MaxValue_list.size();
    jdouble * abc= new jdouble [len];
    jdoubleArray  args  = (env)->NewDoubleArray(len);
    for(unsigned int i =0;i<len;i++)
    {
        //args[i]=SensorsService::CarCheckUp_Value_list[i];
        // env->SetDoubleArrayElement(ArrayA,i,env->GetDoubleArrayElement(ArrayB,j));
        abc[i]=SensorsService::CarCheckUp_MaxValue_list[i];
    }
    env->SetDoubleArrayRegion(args,0,len,abc);
    return args;
}

JNIEXPORT jdoubleArray JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1MinValue_1list
        (JNIEnv *env , jclass obj ){
    jsize len=SensorsService::CarCheckUp_MinValue_list.size();
    jdouble * abc= new jdouble [len];
    jdoubleArray  args  = (env)->NewDoubleArray(len);
    for(unsigned int i =0;i<len;i++)
    {
        //args[i]=SensorsService::CarCheckUp_Value_list[i];
        // env->SetDoubleArrayElement(ArrayA,i,env->GetDoubleArrayElement(ArrayB,j));
        abc[i]=SensorsService::CarCheckUp_MinValue_list[i];
    }
    env->SetDoubleArrayRegion(args,0,len,abc);
    return args;
}

JNIEXPORT jstring JNICALL Java_com_qcwp_carmanager_obd_SensorsService_CarCheckUp_1Units_1list
        (JNIEnv *env, jclass obj){
    return (jstring)env->NewStringUTF(SensorsService::CarCheckUp_Units_list.c_str());
}


/*
* Class:     com_auto_service_SensorsService
* Method:    controlModuleVoltage
* Signature: ()D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_controlModuleVoltage
        (JNIEnv *env, jclass obj){
    return SensorsService::controlModuleVoltage;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    ambientAirTemperature
* Signature: ()I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_ambientAirTemperature
        (JNIEnv *env , jclass obj){
    return SensorsService::ambientAirTemperature;
}



JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_Car_1CheckUp_1init
        (JNIEnv *, jclass){
    SensorsService::Car_CheckUp_init();
}

JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DTC_1CheckUp
        (JNIEnv *env, jclass obj, jstring temp){
    string Cdata=JstringToCstring(env,temp);
    return SensorsService::DTC_CheckUp(Cdata);
}

JNIEXPORT jintArray JNICALL Java_com_qcwp_carmanager_obd_SensorsService_EngineBehaviour_1CheckUp
        (JNIEnv *env, jclass obj, jstring temp){
    string Cdata=JstringToCstring(env,temp);
    vector<int> vecInt= SensorsService::EngineBehaviour_CheckUp(Cdata);

    jsize len=vecInt.size();
    jint * abc= new jint [len];
    jintArray  args  = (env)->NewIntArray(len);

    for(unsigned int i =0;i<len;i++)
    {
        //args[i]=SensorsService::CarCheckUp_Value_list[i];
        // env->SetDoubleArrayElement(ArrayA,i,env->GetDoubleArrayElement(ArrayB,j));
        abc[i]=vecInt[i];
    }
    env->SetIntArrayRegion(args,0,len,abc);

    return args;
}


JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_DrivingData_1CheckUp
        (JNIEnv *env, jclass obj , jstring temp1, jstring temp2){
    string Cdata1=JstringToCstring(env,temp1);
    string Cdata2=JstringToCstring(env,temp2);

    return SensorsService::DrivingData_CheckUp(Cdata1,Cdata2);
}


JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_Car_1CheckUp_1score
        (JNIEnv *, jclass){
    return SensorsService::Car_CheckUp_score();
}

JNIEXPORT jobject JNICALL Java_com_qcwp_carmanager_obd_SensorsService_dataHandler
        (JNIEnv *env, jclass obj, jstring temp1, jboolean temp2)
{
    string Cdata=JstringToCstring(env,temp1);
    unsigned char b = temp2;
    printf("\n c-boolean: %lu  ", b);

    vector<string> results =SensorsService::dataHandler(Cdata, b);


    jclass list_cls = env->FindClass("java/util/ArrayList");//获得ArrayList类引用
    jmethodID list_costruct = env->GetMethodID(list_cls , "<init>","()V"); //获得得构造函数Id
    jobject list_obj = env->NewObject(list_cls , list_costruct); //创建一个Arraylist集合对象
    jmethodID list_add  = env->GetMethodID(list_cls,"add","(Ljava/lang/Object;)Z");


    for(unsigned int i = 0; i < results.size(); i++)
    {
        jstring  qr_obj=(jstring)env->NewStringUTF(results[i].c_str());
        env->CallBooleanMethod(list_obj , list_add , qr_obj); //执行Arraylist类实例的add方法，添加一个QueryResult对象
    }
    return list_obj;
}



/*
* Class:     com_auto_service_SensorsService
* Method:    vehicleImpact
* Signature: ()I
*/
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleImpact
        (JNIEnv *, jclass){
    return SensorsService::vehicleImpact();
}
/*
 * Class:     com_auto_service_SensorsService
 * Method:    vehicleImpactInit
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_vehicleImpactInit
        (JNIEnv *, jclass)
{
    return SensorsService::vehicleImpactInit();
}

/*
 * Class:     com_auto_service_SensorsService
 * Method:    bfImpactVehicleSpeed
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_bfImpactVehicleSpeed
        (JNIEnv *, jclass)
{
    return SensorsService::bfImpactVehicleSpeed;
}

/*
 * Class:     com_auto_service_SensorsService
 * Method:    afImapactVehicleSpeed
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_afImapactVehicleSpeed
        (JNIEnv *, jclass)
{
    return SensorsService::afImapactVehicleSpeed;
}

/*
* Class:     com_auto_service_SensorsService
* Method:    analysisAcceleratorPedal
* Signature: (Ljava/lang/String;Ljava/lang/String;)D
*/
JNIEXPORT jdouble JNICALL Java_com_qcwp_carmanager_obd_SensorsService_analysisAcceleratorPedal
        (JNIEnv *env, jclass obj, jstring sensorsData, jstring pid){

    return SensorsService::analysisAcceleratorPedal(JstringToCstring(env,sensorsData),JstringToCstring(env,pid));
}

/*
 * Class:     com_auto_service_SensorsService
 * Method:    initCoefficient
 * Signature: (DDDDDDDDDD)V
 */
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_initCoefficient
        (JNIEnv *, jclass, jdouble val1, jdouble val2, jdouble val3, jdouble val4, jdouble val5, jdouble val6, jdouble val7, jdouble val8, jdouble val9, jdouble val10){
    SensorsService::initCoefficient(val1,val2,val3,val4,val5,val6,val7,val8,val9,val10);
}


/*
 * Class:     com_auto_service_SensorsService
 * Method:    setMaxLiter
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_setMaxLiter
        (JNIEnv *, jclass, jdouble val){
    SensorsService::setMaxLiter(val);
}

/*
 * Class:     com_auto_service_SensorsService
 * Method:    setOverSpeed
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_qcwp_carmanager_obd_SensorsService_setOverSpeed
        (JNIEnv *, jclass, jdouble overSpeed2){
    SensorsService::setOverSpeed(overSpeed2);
}

/*
 * Class:     com_auto_service_SensorsService
 * Method:    isOverSpeed
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_qcwp_carmanager_obd_SensorsService_isOverSpeed
        (JNIEnv *, jclass){
    return SensorsService::isOverSpeed();
}



}
