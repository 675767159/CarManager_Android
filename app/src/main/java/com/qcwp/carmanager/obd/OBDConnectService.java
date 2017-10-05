package com.qcwp.carmanager.obd;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.blankj.utilcode.util.ThreadPoolUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.broadcast.BlueToothReceiver;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectType;
import com.qcwp.carmanager.enumeration.TimeEnum;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Set;

import static android.content.Context.WIFI_SERVICE;
import static com.blankj.utilcode.util.ThreadPoolUtils.SingleThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class OBDConnectService {

	public static final int REQUEST_OPEN_BT_CODE=1000,REQUEST_WIFILocationRequestResult_Code=2000,REQUEST_BluetoothLocationRequestResult_Code=3000;
	// 提供一个全局的静态方法
	public static OBDConnectService getInstance() {
		if (INSTANCE == null) {
			synchronized (OBDConnectService.class) {
				if (INSTANCE == null) {
					INSTANCE = new OBDConnectService();
				}
			}
		}
		return INSTANCE;
	}

	public void startConnectService(){
		switch (connectType){
			case WIFI:
				this.connectWiFi();
				break;

			case BlueTooth:
				this.connectBlueTooth();
				break;
		}
	}

	@SuppressWarnings("finally")
	public static synchronized String getData(String pid) {
		StringBuffer tmpValue = new StringBuffer();

		try {
			mmOutStream.write((pid + "\r").getBytes());

			int buffSize = 1024;
			byte[] buffer2 = new byte[buffSize];
			int bytes = 0;

			while(true) {
				bytes = mmInStream.read(buffer2, 0, buffSize);
				tmpValue.append(new String(buffer2, 0, bytes));
				if(tmpValue.toString().contains(">")) {
					break;
				}
			}
		} catch (IOException e) {
			connectLostFlag++;
			if(connectLostFlag>3){
				OBDConnectService.getInstance().closeConnect();
				connectLostFlag = 1;
			}
		} finally {

			return tmpValue.toString();
		}
	}



	private  void closeConnect() {
		try {
			Print.d("closeBluetooth","----------");
			if(mmInStream != null) mmInStream.close();
			if(mmOutStream != null) mmOutStream.close();
			stopConnectSoket();
			EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.OBDLostDisconnection,null));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public interface ConectOBDListener{
		void completeConect(Boolean success,String message);
	}
	private ConectOBDListener conectOBDListener;
	public void setConectOBDListener(ConectOBDListener conectOBDListener){
		this.conectOBDListener=conectOBDListener;
	}


	private OBDConnectType connectType;
	private static OBDConnectService INSTANCE;
	private final String serverIp = "192.168.0.10";
	private  static InputStream mmInStream;
	private  static OutputStream mmOutStream;
	private static  int connectLostFlag;
	private String message;
	private WifiManager wifiManager;
	private Activity currentActivity;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothSocket bluetoothSocket;
	public void setConnectType(OBDConnectType connectType) {
		this.connectType = connectType;
	}

	private OBDConnectService() {

		EventBus.getDefault().register(this);
		wifiManager=(WifiManager)APP.getInstance().getApplicationContext().getSystemService(WIFI_SERVICE);
		currentActivity=MyActivityManager.getInstance().getCurrentActivity();
	}




	private void connectWiFi(){

		if (wifiManager.isWifiEnabled()||wifiManager.setWifiEnabled(true)){

			if (CommonUtils.currentWifiIsOBDWIFI(wifiManager)){
				Boolean isSuccess=connectWiFiOBD();
				returnConnetResult(isSuccess);

			}else {

				if (Build.VERSION.SDK_INT >= 6.0 && !CommonUtils.isGpsOPen(currentActivity)) {
					CommonUtils.openGPS(currentActivity,REQUEST_WIFILocationRequestResult_Code);
				}else {
					autoConnectOBDWIFI();
				}


			}


		}else {
			message="连接失败，请打开WiFi！";
			returnConnetResult(false);
		}


	}

	private void autoConnectOBDWIFI(){

		Boolean isSuccess = CommonUtils.createOBDWIFI(wifiManager);
		if (isSuccess) {

			isSuccess=connectWiFiOBD();

		}else {

			message="连接失败，未搜索到OBD WIFI";
		}

		returnConnetResult(isSuccess);

	}

	/**
	 * @param event
	 */
	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void onMessageEvent(MessageEvent event) {

		switch (event.getType()){

			case BlueRequestResult:
			{
				if (Build.VERSION.SDK_INT >= 6.0 && !CommonUtils.isGpsOPen(currentActivity)) {
					CommonUtils.openGPS(currentActivity,REQUEST_BluetoothLocationRequestResult_Code);

				}else {
					this.autoConnectBluetoothOBD();
				}
			}
			break;
			case WIFILocationRequestResult:
			{
				if (event.getMessage().equals("0")) {
					autoConnectOBDWIFI();
				}else {
					message="连接失败，请打开定位！";
					returnConnetResult(false);
				}

			}
			break;

			case BluetoothLocationRequestResult:
			{
				if (event.getMessage().equals("0")) {
					autoConnectBluetoothOBD();
				}else {
					message="连接失败，请打开定位！";
					returnConnetResult(false);
				}

			}
			break;
			case BlueToothScaned:
				if (mBluetoothAdapter!=null) {
					Print.d("startBluetoothService","event"+event.getMessage());
					mBluetoothAdapter.cancelDiscovery();
					Boolean isSuccess=connectBlueToothOBD(event.getMessage());
					returnConnetResult(isSuccess);
				}
				break;
		}

	}


	private Boolean isTowTypehadConnect=false;
	private void returnConnetResult(Boolean isSuccess){


		if (!isTowTypehadConnect&&!isSuccess){
			isTowTypehadConnect=true;
			switch (connectType){
				case WIFI:
					connectType=OBDConnectType.BlueTooth;
					break;
				case BlueTooth:
					connectType=OBDConnectType.WIFI;
					break;
			}
			startConnectService();

		}else {

			isTowTypehadConnect=false;
			if (conectOBDListener != null) {
				conectOBDListener.completeConect(isSuccess, message);
			}

			if (isSuccess){
				MySharedPreferences mySharedPreferences=new MySharedPreferences(APP.getInstance());
				mySharedPreferences.setInt(KeyEnum.typeKey,connectType.getValue());

			}
		}


	}


	private Boolean connectWiFiOBD(){

		SocketAddress socketAddress = new InetSocketAddress(serverIp, 35000);

		try {
			Print.d("connectWifi","当前IP："+serverIp);
			Socket socket  = new Socket();
			socket.connect(socketAddress,3000);
			socket.setSoTimeout(20*1000);
			Print.d("connectWifi",serverIp+"连接成功----------------");
			message="连接成功！";
			mmOutStream = socket.getOutputStream();
			mmInStream = socket.getInputStream();

		} catch (Exception e) {
			Print.d("connectWifi",serverIp+"连接失败！");
			message = "连接超时，请确认是否连接上设备！";
			return false;
		}

		return true;

	}

	private void connectBlueTooth(){

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		//第一种打开方法： 调用enable 即可
		boolean result = mBluetoothAdapter.enable();

		Print.d("startBluetoothService","result=="+result);

		//第二种打开方法 ，调用系统API去打开蓝牙
		if (!result) //未打开蓝牙，才需要打开蓝牙
		{
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			currentActivity.startActivityForResult(intent, REQUEST_OPEN_BT_CODE);
			//会以Dialog样式显示一个Activity ， 我们可以在onActivityResult()方法去处理返回值
		}else {

			if (Build.VERSION.SDK_INT >= 6.0 && !CommonUtils.isGpsOPen(currentActivity)) {
				CommonUtils.openGPS(currentActivity,REQUEST_BluetoothLocationRequestResult_Code);

			}else {
				this.autoConnectBluetoothOBD();
			}

		}

	}

	private void autoConnectBluetoothOBD(){


		Print.d("startBluetoothService","result==ddd");
		String address = null;
		Set<BluetoothDevice> sets = mBluetoothAdapter.getBondedDevices();
		for (BluetoothDevice bluetoothDevice : sets) {
			String name = bluetoothDevice.getName();
			Print.d("startBluetoothService","name=="+name);
			if (name.contains("OBD")) {
				address = bluetoothDevice.getAddress();
			}
		}

		if (address != null) {
			Boolean isSuccess=connectBlueToothOBD(address);
			returnConnetResult(isSuccess);
		} else {
			Print.d("startBluetoothService","name=="+"000000");
			BlueToothReceiver.gotOBD = false;
			mBluetoothAdapter.startDiscovery();
			ThreadPoolUtils threadPool = new ThreadPoolUtils(SingleThread, 1);
			threadPool.schedule(new Runnable() {
				@Override
				public void run() {
					Print.d("startBluetoothService","------------");
					if (!BlueToothReceiver.gotOBD){
						Print.d("startBluetoothService","++++++++");
						mBluetoothAdapter.cancelDiscovery();
						message="未连接到OBD设备，请确认是否插上设备再重试!";
						returnConnetResult(false);
					}

				}
			}, TimeEnum.BluetoothDiscovery, SECONDS);

		}




	}


	private Boolean connectBlueToothOBD(String address){

		BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice(address);
		Print.d("startBluetoothService","address=="+address);

		boolean isConnectSuccess = true;
		try {



			for (int i = 0; i <=3; i++) {
				Print.d("startBluetoothService","getRemoteDevice"+i);
				try {

					Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);

					bluetoothSocket =CommonUtils.setPin(mmDevice.getClass(),mmDevice,"1234");
					if (bluetoothSocket==null) {
						Print.d("startBluetoothService","null");
						bluetoothSocket=(BluetoothSocket) m.invoke(mmDevice, 1);
					}
					bluetoothSocket.connect();
					message="连接成功";
					break;

				} catch(Exception eee) {
					
					//【Connection refused】 （不包含中括号）再用第二种方法连接是可以连接上的
					//【Device or resource busy】当另一个软件退出的时候，才可以连接上，可以多试几次第二种方法，可以提示用户退出其它线程
					//【Permission denied】这是主要问题，有时可以多次进行连接而连上，有时不行（还有个大问题就是当连不上时，有一直弹出输入框），解决办法：如果已经配对过重启蓝牙，取消配对再连即可连上
					//【Connection timed out】
					//No route to host 当车易连接上时，我们再连接会出现这情况
					//还有一种情况，obd已经连接上，但蓝牙却没有检测到，解决方法，重插下obd
					//当蓝牙已经给其它
					isConnectSuccess=false;
					if (bluetoothSocket!=null){
						bluetoothSocket.close();
						bluetoothSocket=null;
					}
					message = "连接超时，请确认是否连接上设备！";
					Print.d("连接情况","错误信息："+message);
					System.out.println(eee.getMessage());
					Thread.sleep(300);
					continue;
				}
			}


		} catch (Exception e) {
			try {
				if(null != bluetoothSocket) bluetoothSocket.close();
			} catch (Exception e2) {
			}
			message=e.getLocalizedMessage();
			return false;
		}



		try {
			mmInStream = bluetoothSocket.getInputStream();
			mmOutStream = bluetoothSocket.getOutputStream();

		} catch(Exception e) {
			isConnectSuccess=false;
			e.printStackTrace();
		}


		return isConnectSuccess;


	}

	public void stopConnectSoket(){


		switch (connectType){
			case WIFI:

				break;
			case BlueTooth:
				try {
					if(null != bluetoothSocket) bluetoothSocket.close();
				} catch (Exception e2) {
				}
				break;
		}
	}


}
