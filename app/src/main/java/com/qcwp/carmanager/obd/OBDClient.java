package com.qcwp.carmanager.obd;

import com.qcwp.carmanager.enumeration.LoadDataTypeEnum;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.Print;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

    public  void readVinCode(int tempOnlyFlag,SensorsService sensorsService,ReadVinCodeCompleteListener     tempSensorsService)  {



       String data = BluetoothService.getData("0100");
        tempSensorsService.readVinCodeComplete();


    }


    public  void getCarData(){

    }

    public  void  stop(){


    }

//   public Boolean  connectSocket(int sock, String host, String port) {
//        Print.d("");
//        int ret;
//
//
//
//        struct addrinfo  result, hints;
//        struct addrinfo  addr;
//
//
//        int tempsock=0;
//
//        memset(&hints, 0, sizeof hints);
//        hints.ai_family = AF_INET;
//        hints.ai_socktype = SOCK_STREAM;
//        hints.ai_flags = AI_PASSIVE;
//
//        ret = getaddrinfo([host cStringUsingEncoding:(NSStringEncoding)NSASCIIStringEncoding],
//                      [port cStringUsingEncoding:(NSStringEncoding)NSASCIIStringEncoding],
//                      &hints, &result);
//        NSLog(@"ret====%d",ret);
//
//        if (ret != 0) return FALSE;
//        for(addr = result; addr != NULL; addr = addr->ai_next) {
//            if ((tempsock = socket(addr->ai_family, addr->ai_socktype, addr->ai_protocol)) == -1) {
//                perror("talker: socket");
//                NSLog(@"wtf is this");
//                continue;
//            }
//            break;
//        }
//
//        if (tempsock == -1) {
//            return FALSE;
//        }
//
//
//        unsigned long ul = 1;
//        int rm = ioctl(tempsock, FIONBIO, &ul);
//        if(rm == -1){
//        [OBDClient stop];
//            return NO;
//        }
//        NSLog(@"rm====%d",rm);
//        ret = connect(tempsock, addr->ai_addr, addr->ai_addrlen);
//        if (ret == 0){
//            NSLog(@"connected");
//        }
//        NSLog(@"ret====%d",ret);
//        struct timeval timeout = {10,0};
//        fd_set r;
//        FD_ZERO(&r);
//        FD_SET(tempsock,&r);
//        int retval = select(tempsock+1,NULL,&r,NULL,&timeout);
//        if(retval == -1){
//            NSLog(@"select");
//        [OBDClient stop];
//            return NO;
//        }else if(retval == 0){
//            NSLog(@"OBD timeout");
//        [OBDClient stop];
//            return NO;
//        }
//        NSLog(@"retval====%d",retval);
//        unsigned long ul1=0;
//
//        rm = ioctl(tempsock,FIONBIO,(unsigned long*)&ul1);
//        if(rm == -1){
//        [OBDClient stop];
//            return NO;
//        }
//        NSLog(@"rm======%d",rm);
//
//        NSLog(@"errno======%d",errno);
//
//
//        if(errno != EINPROGRESS){
//        *sock = tempsock;
//            timeout = {20,0};
//            setsockopt(tempsock, SOL_SOCKET, SO_SNDTIMEO, (char *)&timeout, sizeof(struct timeval));
//            setsockopt(tempsock, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(struct timeval));
//            NSLog(@"connected/n");
//            NSLog(@"tempsock====%d",tempsock);
//            return YES;
//        }
//
//        //2017-5-9所加非阻塞connect errno为EINPROGRESS,如何判断已经连接上了？
//        int so_error;
//        socklen_t len = sizeof(so_error);
//        int tempsock2=tempsock;
//        getsockopt(tempsock2, SOL_SOCKET, SO_ERROR, &so_error, &len);
//
//        if (so_error == 0) {
//        /* socket is connected */
//        *sock = tempsock;
//            timeout = {20,0};
//            setsockopt(tempsock, SOL_SOCKET, SO_SNDTIMEO, (char *)&timeout, sizeof(struct timeval));
//            setsockopt(tempsock, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(struct timeval));
//            NSLog(@"connected/n");
//            NSLog(@"tempsock====%d",tempsock);
//            return YES;
//
//        }
//
//        return false;
//    }
//




////    连接相关方法
//    public static void connect(SensorsService tempSensorsService, ConnectCompleteListener connectComplete){
//
//        static  OBDClient*OBD=nil;
//        if(OBD==nil)  OBD=[OBDClient new];
//        sensorsService=tempSensorsService;
//        ConnectStatus = connectTypeConnectingWithWiFi;
//
//        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT,0), ^{
//        if ([OBD connectSocket:&_odbSock host:OBDKEY_HOST port:OBDKEY_PORT])
//        {
//            ConnectStatus = connectTypeConnectingWithOBD;
//            BOOL isConnect = [OBD connectOBD];
//            if (isConnect)
//            {
//                ConnectStatus = connectTypeConnectingWithSP;
//                spValue = nil;
//                spValue = [OBD detectProtocol:[OBDClient readOdbDataPid:@"ATDPN"]];
//
//                if (!spValue)
//                {
//                    ConnectStatus = connectTypeDisconnectionWithProtocol;
//                }else
//                {
//                        [OBDClient shareOBDdata].spValue=spValue;
//                    NSLog(@"协议 = %@",spValue);
//                }
//
//                if (block) block(ConnectStatus,nil);
//
//            }
//
//            else
//            {
//                ConnectStatus = connectTypeDisconnectionWithOBD;
//                if (block) block(ConnectStatus,nil);
//            }
//
//
//
//
//        }
//            else
//        {
//            ConnectStatus = connectTypeDisconnectionWithWIFi;
//                [OBDClient stop];
//            if (block) block(ConnectStatus,nil);
//        }
//    });
//
//
//
//
//    }
//
//
//////
//////    public native String stringFromJNI();
////    static {
////        System.loadLibrary("native-lib");
////    }
////
////
////    public static void connectBySocket() throws Exception{
//////		Socket socket  = new Socket("192.168.0.10", 35000);
//////		Socket socket  = new Socket("192.168.11.245", 35000);
////
//////		for(int i = 0;i<255;i++){
//////			String serverIp = "192.168.0."+i;
////        String serverIp = "192.168.0.10";
////        try {
////
////            Socket socket  = new Socket(serverIp, 35000);
////
////             InputStream mmInStream= socket.getInputStream();
////             OutputStream mmOutStream= socket.getOutputStream();
////
//////				myThread.start();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//////		}

    public interface ReadVinCodeCompleteListener{
        void readVinCodeComplete();
    }
    public interface ConnectCompleteListener{
        void connectComplete();
    }
}
