package com.qcwp.carmanager.obd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.blankj.utilcode.util.ThreadPoolUtils.SingleThread;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectSuccess;
import static com.qcwp.carmanager.enumeration.OBDConnectStateEnum.connectTypeConnectingWithSP;

/**
 * Created by qyh on 2017/6/6.
 */

public class OBDClient {

    private static   OBDConnectStateEnum  connectStatus;//连接状态
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

    public  static void readVinCode(final ReadVinCodeCompleteListener  readVinCodeCompleteListener)  {

        ThreadPoolUtils threadPool=new ThreadPoolUtils(SingleThread,1);

       threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                BluetoothService bluetoothService=new BluetoothService();
                BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice("00:0D:18:00:00:01");
                bluetoothService.conectOBD(device, new BluetoothService.ConectOBDListener() {
                    @Override
                    public void completeConect(Boolean success, String message) {

                        connectStatus=connectTypeConnectingWithSP;
                        Boolean  blockSuccess=success;
                        String  blockMessage=message;
                        if (success) {
                            if (EmptyUtils.isNotEmpty(OBDClient.detectProtocol())) {

                                String data = BluetoothService.getData(SensorsService.VIN_PIDS);
                                Print.d("BluetoothService", data + "----");

                                SensorsService.SensorsDataHandler(data, SensorsService.VIN_PIDS);
                                String vinCode = SensorsService.GetVinCode(data);

                                connectStatus = connectTypeConnectSuccess;
                                blockMessage=vinCode;
                                blockSuccess=true;


                            } else {
                                blockMessage="不是通用协议";
                                blockSuccess=false;

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



                    }
                });

                return null;
            }
        });






    }

    static String  detectProtocol() {

        String SP=new  MySharedPreferences(APP.getInstance()).getString(KeyEnum.currentOBDrotocol,"");

        if (EmptyUtils.isNotEmpty(SP)){
            String data =  BluetoothService.getData(SP);
           if (OBDClient.checkProtocolData(data)){
                return SP;
            }
        }

        //1.用协议去判断连接状态，成功返回：OK（ATDPN 例外，返回 0），失败返回的数据目前未知
        //2.用0100去拿数据，成功返回：BUS INIT: OK和(4100或41 00)  失败返回 BUS INIT: ... ERROR、NO DATA、UNABLE TO CONNECT
        //3.必须同时满足1和2才能正常连接
        String  data;
        //    NSLog(@"SP = %@",SP);
        String str1 =  BluetoothService.getData("ATZ");//这两个必须有，不然会出现连上了却读不到数据的情况，（实测过的）
        String str2 = BluetoothService.getData("ATE0");
        String str3 = BluetoothService.getData("ATS0");


        if (OBDClient.checkProtocolData("OK")){
            return "ATZ";
        }


        String sp_Value;
       String[] spArr=APP.getInstance().getResources().getStringArray(R.array.OBDProtocol);

        for (int i = 0; i <spArr.length; i++) {
            if (connectStatus == connectTypeConnectingWithSP) {
                sp_Value = spArr [i];
               if (OBDClient.checkProtocolData(BluetoothService.getData(sp_Value))) {
                   MySharedPreferences mySharedPreferences = new MySharedPreferences(APP.getInstance());
                   mySharedPreferences.setString(KeyEnum.currentOBDrotocol, sp_Value);
                   return sp_Value;
               }
            }
        }
        return null;
    }

   static Boolean  checkProtocolData(String data){

        if (data.toUpperCase().contains("OK")) {
            data = BluetoothService.getData("0100");

            if (data.contains("4100") ||data.contains("41 00")) {
                //当车辆还未初始化完成时，再初始化一次
                while (data.contains("ERROR")){
                    data = BluetoothService.getData("0100");
                }


                return true;
            }

        }

        return false;
    }
    public interface ReadVinCodeCompleteListener{
        void connectComplete(Boolean success,String message);
    }

}
