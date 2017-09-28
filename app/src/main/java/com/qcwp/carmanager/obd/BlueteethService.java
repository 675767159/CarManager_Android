package com.qcwp.carmanager.obd;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;


import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ThreadPoolUtils;
import com.qcwp.carmanager.broadcast.BlueToothReceiver;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.TimeEnum;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.ui.LoginActivity;
import com.qcwp.carmanager.ui.MainActivity;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;

import static com.blankj.utilcode.util.ThreadPoolUtils.FixedThread;
import static com.blankj.utilcode.util.ThreadPoolUtils.SingleThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class BlueteethService {


    private static InputStream mmInStream;
    private static OutputStream mmOutStream;

    
    public static int connectLostFlag = 1;
	private static String returnConnectBluetoothExceptionStr = "";
	public static final int REQUEST_OPEN_BT_CODE=1000,MY_PERMISSION_REQUEST_CONSTANT=2000;
    /**
     * 蓝牙是否断开1为是，0为否
     */

	private BluetoothAdapter mBluetoothAdapter;
	private BlueteethService() {

		EventBus.getDefault().register(this);
	}
	private static class BluetoothServiceHolder {

		static final BlueteethService INSTANCE = new BlueteethService();
	}

	public static BlueteethService getDefaultBluetoothService(){
		return  BlueteethService.BluetoothServiceHolder.INSTANCE;
	}


	public void startBluetoothService() {








		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		//第一种打开方法： 调用enable 即可
		boolean result = mBluetoothAdapter.enable();

		Print.d("startBluetoothService","result=="+result);


	//第二种打开方法 ，调用系统API去打开蓝牙
		if (!result) //未打开蓝牙，才需要打开蓝牙
		{
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    MyActivityManager.getInstance().getCurrentActivity().startActivityForResult(intent, REQUEST_OPEN_BT_CODE);
		//会以Dialog样式显示一个Activity ， 我们可以在onActivityResult()方法去处理返回值
		}else {
			this.startDiscoveryOrconectOBD();
		}
	}

	private void startDiscoveryOrconectOBD(){

        if (Build.VERSION.SDK_INT >= 6.0) {
			PackageManager pm =MyActivityManager.getInstance().getCurrentActivity().getPackageManager();

			boolean permission = (PackageManager.PERMISSION_GRANTED ==
					pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", "com.qcwp.carmanager"));

			if (!permission)  {
				ActivityCompat.requestPermissions(MyActivityManager.getInstance().getCurrentActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
						MY_PERMISSION_REQUEST_CONSTANT);
				Print.d("startBluetoothService","result==VERSION");
			}


        }

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
			conectOBD(address);
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
						conectOBDListener.completeConect(false,"未连接到OBD设备，请确认是否插上设备再重试!");
					}

				}
			}, TimeEnum.BluetoothDiscovery, SECONDS);

		}




	}

	/**
	 * @param event
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MessageEvent event) {

		switch (event.getType()){
			case BlueToothScaned:
				if (mBluetoothAdapter!=null) {
					Print.d("startBluetoothService","event"+event.getMessage());
					mBluetoothAdapter.cancelDiscovery();
					conectOBD(event.getMessage());
				}
				break;
			case BlueRequestResult:
				//				允许打开蓝牙
				if (event.getMessage().equals("-1")){
					this.startDiscoveryOrconectOBD();
				}else {//  0则是拒绝
					conectOBDListener.completeConect(false,"用户拒绝打开蓝牙!");
				}
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
					BlueteethService.getDefaultBluetoothService().closeBluetooth();
					connectLostFlag = 1;
				}
			} finally {

				return tmpValue.toString();
			}
    }
	

	
	private  void closeBluetooth() {
		try {
			Print.d("closeBluetooth","----------");
			if(mmInStream != null) mmInStream.close();
			if(mmOutStream != null) mmOutStream.close();

			EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.OBDLostDisconnection,null));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}




	private Boolean conectOBD(String address){

		BluetoothDevice mmDevice = mBluetoothAdapter.getRemoteDevice(address);
		Print.d("startBluetoothService","getRemoteDevice");
		BluetoothSocket mmSocket =null;
		try {
			BluetoothSocket tmp = null;
			boolean isConnectSuccess = false;


			for (int i = 0; i <=5; i++) {
				Print.d("startBluetoothService","getRemoteDevice"+i);
				try {
//					if(i == 0) {

//					Method removeBondMethod = btClass.getDeclaredMethod("setPin",
//							new Class[]
//									{byte[].class});
//					tmp = (BluetoothSocket) removeBondMethod.invoke(mmDevice,
//							new Object[]{"1234".getBytes()});
//
//					tmp.connect();
//					isConnectSuccess = true;


						Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);

						tmp =CommonUtils.setPin(mmDevice.getClass(),mmDevice,"1234");
					    if (tmp==null) {
							Print.d("startBluetoothService","null");
						tmp=(BluetoothSocket) m.invoke(mmDevice, 1);
				      	}
						tmp.connect();
						isConnectSuccess = true;
						break;
//					}

				} catch(Exception eee) {
					//TODO 蓝牙连接失败返回的代码有：
					//【Connection refused】 （不包含中括号）再用第二种方法连接是可以连接上的
					//【Device or resource busy】当另一个软件退出的时候，才可以连接上，可以多试几次第二种方法，可以提示用户退出其它线程
					//【Permission denied】这是主要问题，有时可以多次进行连接而连上，有时不行（还有个大问题就是当连不上时，有一直弹出输入框），解决办法：如果已经配对过重启蓝牙，取消配对再连即可连上
					//【Connection timed out】
					//No route to host 当车易连接上时，我们再连接会出现这情况
					//还有一种情况，obd已经连接上，但蓝牙却没有检测到，解决方法，重插下obd
					//当蓝牙已经给其它
					returnConnectBluetoothExceptionStr = eee.getMessage().toString();
					Print.d("连接情况","错误信息："+returnConnectBluetoothExceptionStr);
					System.out.println(eee.getMessage());
					Thread.sleep(300);
					continue;
				}
			}

			if(isConnectSuccess == false) {
				if (conectOBDListener!=null){
					conectOBDListener.completeConect(false,returnConnectBluetoothExceptionStr);
				}
				return false;
			}

			mmSocket = tmp;

		} catch (Exception e) {
			try {
				if(null != mmSocket) mmSocket.close();
			} catch (Exception e2) {
			}
			if (conectOBDListener!=null){
				conectOBDListener.completeConect(false,e.getLocalizedMessage());
			}
			return false;
		}



		try {
			mmInStream = mmSocket.getInputStream();
			mmOutStream = mmSocket.getOutputStream();

		} catch(Exception e) {
			e.printStackTrace();
		}

		if (conectOBDListener!=null){
			conectOBDListener.completeConect(true,"连接成功!");
		}
		return true;
	}

	public interface ConectOBDListener{
		void completeConect(Boolean success,String message);
	}
	private ConectOBDListener conectOBDListener;
	public void setConectOBDListener(ConectOBDListener conectOBDListener){
	    this.conectOBDListener=conectOBDListener;
	}




}
