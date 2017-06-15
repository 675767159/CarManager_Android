package com.qcwp.carmanager.obd;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;


import com.qcwp.carmanager.utils.Print;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.UUID;

public class BluetoothService {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final UUID MY_UUID_2 = UUID.fromString("00001102-0000-1000-8000-00805F9B34FB");
//    private static final UUID MY_UUID_0 = UUID.fromString("00001100-0000-1000-8000-00805F9B34FB");

//    private final BluetoothAdapter mAdapter;
    private static ConnectThread mConnectThread;
    private static int mState;
    
    private static InputStream mmInStream;
    private static OutputStream mmOutStream;
	
	public static final int STATE_NONE = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_FAILED = 3;
    public static final int STATE_PROTOCOL_CHECKING = 4;
    
    public static int connectLostFlag = 1;
    
    private static String obdType = "BLUETOOTH";
    public static String serverIp = "192.168.0.10";
    
    /**
     * 蓝牙是否断开1为是，0为否
     */
    public static int isBluetoothHadLost = 0;
    
    /**
     * 第三个UUID连接适配器结束，无论是否成功都为true
     */
    public static boolean thirdConnectHadDone = false;
    
    public static boolean isSuccess = true;
	
	public BluetoothService() {
//		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mState = STATE_NONE;
	}
	
	public synchronized void connect(BluetoothDevice device) {
		setState(STATE_CONNECTING);
		obdType = "BLUETOOTH";
		mConnectThread = new ConnectThread(device);
        mConnectThread.start();
	}
	public synchronized void connectWifi() {
		setState(STATE_CONNECTING);
		obdType = "WIFI";
		SocketAddress sa = new InetSocketAddress(serverIp, 35000);

		try {
			Print.d("BluetoothService","当前IP："+serverIp);
//			Socket socket  = new Socket(serverIp, 35000);
			Socket socket  = new Socket();
			socket.connect(sa,3000);
//			socket.setSoTimeout(2000);
			Print.d("BluetoothService",serverIp+"连接成功");
			mmOutStream = socket.getOutputStream();
			mmInStream = socket.getInputStream();
//			Thread.sleep(200);
			setState(STATE_PROTOCOL_CHECKING);





		} catch (Exception e) {
			setState(STATE_FAILED);
			Print.d("BluetoothService",serverIp+"连接失败");
		}
		log("连接状态，getState:"+getState());
		String str1 =  BluetoothService.getData("ATZ");//这两个必须有，不然会出现连上了却读不到数据的情况，（实测过的）
		String str2 = BluetoothService.getData("ATE0");
		String str3 = BluetoothService.getData("ATS0");

		String	data = BluetoothService.getData("0100");
		Print.d("BluetoothService",data+"-----");
	}
	public synchronized boolean connectWifi_v2() {
		setState(STATE_CONNECTING);
		obdType = "WIFI";
		SocketAddress sa = new InetSocketAddress(serverIp, 35000);
		try {
			log("当前IP："+serverIp);
//			Socket socket  = new Socket(serverIp, 35000);
			Socket socket  = new Socket();
			socket.connect(sa,3000);
//			socket.setSoTimeout(2000);
			log(serverIp+"连接成功----------------");
			mmOutStream = socket.getOutputStream();
			mmInStream = socket.getInputStream();
//			Thread.sleep(200);
			setState(STATE_PROTOCOL_CHECKING);
			return true;
		} catch (Exception e) {
			log(e.getLocalizedMessage()+"-----");
			setState(STATE_FAILED);
			log(serverIp+"连接失败！");
		}
		log("连接状态，getState:"+getState());
		return false;
	}
	
	public static  void setState(int state) {
        mState = state;
    }
	
	private static synchronized void connectionLost() {
		System.out.println("connectionLost ... 蓝牙断开");
		isBluetoothHadLost++;
        setState(STATE_NONE);
        if(mmInStream != null){
        	closeBluetooth();
        }
    }
	
	public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
		try {
			mmInStream = socket.getInputStream();
	        mmOutStream = socket.getOutputStream();
	        log(serverIp+"连接成功------mmInStream："+mmInStream);
	        Thread.sleep(1000);
	        
	        setState(STATE_PROTOCOL_CHECKING);
		} catch(Exception e) {
		}
    }
	
	public static int getState() {
        return mState;
    }
	
	private void connectionFailed() {
		setState(STATE_FAILED);
    }
	
	@SuppressWarnings("finally")
	public static synchronized String getData(String pid) {
		StringBuffer tmpValue = new StringBuffer();
		
//		synchronized(BluetoothService.class){
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
					connectionLost();
					connectLostFlag = 1;
				}
			} finally {
//			try {
//				ContentValues values  = new ContentValues();
//				values.put("Pid", pid);
//				values.put("PidReturn", tmpValue.toString());
//				values.put("Time", DateTime.getTimeString());
//				BaseActivity.db.insert("t_original", null, values);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
				
				return tmpValue.toString();
			}
//		}
    }
	
	public void closeSocket() {
		mConnectThread.cancel();
		//mConnectedThread.cancel();
	}
	
	public static void closeBluetooth() {
		try {
			System.out.println("111111111111");
			if(mmInStream != null) mmInStream.close();
			System.out.println("222222222222");
			if(mmOutStream != null) mmOutStream.close();
			System.out.println("33333333333,+mConnectThread:" + mConnectThread);
			if(mConnectThread != null) {
				mConnectThread.cancel();
				System.out.println("4444444444");
				mConnectThread = null;
			}
			System.out.println("5555555555555");
		} catch(Exception e) {
			System.out.println("close bluetooth exception.");
			e.printStackTrace();
		}
	}
	public static String returnConnectBluetoothExceptionStr = "";
	private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            /*BluetoothSocket tmp = null;

            try {
            	tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            	GeneralUtils.w("create socket service fail.");
            }
            mmSocket = tmp;*/
        }

        public void run() {
            try {
            	BluetoothSocket tmp = null;
            	boolean isConnectSuccess = false;
            	thirdConnectHadDone = false;
            	
            	for (int i = 0; i <= 4; i++) {
            		log("RfcommSocket，第"+i+"次，状态："+BluetoothService.getState());
            		try {
            			if(i == 0) {
            				Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);
                			tmp = (BluetoothSocket)m.invoke(mmDevice, 1);
                			log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
                			tmp.connect();
                			isConnectSuccess = true;
            				break;
            			}
            			else if(i==1||i==2){
            				tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            				log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
            				tmp.connect();
            				isConnectSuccess = true;
            				break;
            			}
            			else if(i==3){
            				tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_2);
            				thirdConnectHadDone = true;
            				log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
            				tmp.connect();
            				isConnectSuccess = true;
            				break;
            			}
            			if(!("Host is down".equals(returnConnectBluetoothExceptionStr))){
            				if(i==1){
                				tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
                				log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
                				tmp.connect();
                				isConnectSuccess = true;
                				break;
                			}
                			if(i==2){
                				tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_2);
                				thirdConnectHadDone = true;
                				log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
                				tmp.connect();
                				isConnectSuccess = true;
                				break;
                			}
            			}
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
            			log("连接失败，错误信息："+returnConnectBluetoothExceptionStr);
            			System.out.println(eee.getMessage());
            			Thread.sleep(300);
            			continue;
            		}
            	}
            	log("isConnectSuccess.状态："+getState());
            	if(isConnectSuccess == false) {
            		setState(STATE_FAILED);
            		return;
            	}
                mmSocket = tmp;
                log("isConnectSuccess.状态："+getState());
                /*Thread.sleep(1000);
            	
            	GeneralUtils.d(mmDevice.getName() + "(" + mmDevice.getAddress() + ") connecting...");
            	if(null != mmSocket) {
            		mmSocket.connect();
            	} else {
            		connectionFailed();
            		return;
            	}*/
            } catch (Exception e) {
                connectionFailed();
                log("connect device fail.状态："+getState());
                try {
                    if(null != mmSocket) mmSocket.close();
                } catch (Exception e2) {
                }
                return;
            }

            log("connect device success.");
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }
            
            log("connect device success.状态："+getState());
            connected(mmSocket, mmDevice);
        }

		public void cancel() {
            try {
                if(null != mmSocket) mmSocket.close();
            } catch (IOException e) {
            }
        }
    }
	
	/**
	 * 蓝牙连接，无线程版
	 * @param device
	 */
	public synchronized int connect_v2(BluetoothDevice device) {
		setState(STATE_CONNECTING);
		obdType = "BLUETOOTH";
		return conectBlue_v2(device);
	}
	
	
	private int conectBlue_v2(BluetoothDevice mmDevice){
		BluetoothSocket mmSocket =null;
        try {
        	BluetoothSocket tmp = null;
        	boolean isConnectSuccess = false;
        	thirdConnectHadDone = false;
        	
        	for (int i = 0; i <= 4; i++) {
        		log("RfcommSocket，第"+i+"次，状态："+BluetoothService.getState());
        		try {
        			if(i == 0) {
        				Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);
            			tmp = (BluetoothSocket)m.invoke(mmDevice, 1);
            			log("RfcommSocket，第"+i+"次，tmp"+tmp.toString());
            			tmp.connect();
            			isConnectSuccess = true;
        				break;
        			}

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
			Print.d("连接情况","isConnectSuccess.状态："+getState());
        	if(isConnectSuccess == false) {
        		setState(STATE_FAILED);
        		return STATE_FAILED;
        	}
        	mmSocket = tmp;
			Print.d("连接情况","isConnectSuccess.状态："+getState());

        } catch (Exception e) {
            connectionFailed();
			Print.d("连接情况","connect device fail.状态："+getState());
            try {
                if(null != mmSocket) mmSocket.close();
            } catch (Exception e2) {
            }
            return STATE_FAILED;
        }

        
        log("connect device success.状态："+getState());
//        connected(mmSocket, mmDevice);
        //连接成功，下一步进行协议检测
        try {
			mmInStream = mmSocket.getInputStream();
	        mmOutStream = mmSocket.getOutputStream();
			Print.d("连接情况",serverIp+"连接成功------mmInStream："+mmInStream);
	        Thread.sleep(1000);
	        
	        setState(STATE_PROTOCOL_CHECKING);
	        return STATE_PROTOCOL_CHECKING;
		} catch(Exception e) {
			e.printStackTrace();
		}
        return STATE_FAILED;
	}
	
	
	
	
	/**
	 * 1,是蓝牙，2,是wifi
	 * @return
	 */
	public static int getObdType(){
		int i = 0;
		if(obdType.equals("WIFI")){
			i = 2;
		}else if(obdType.equals("BLUETOOTH")){
			i = 1;
		}
		return i;
	}
	/**
	 * 如果是wifi OBD 返回true
	 * @return
	 */
	public static boolean isWifiObdType(){
		return obdType.equals("WIFI");
	}
	/**
	 * @return
	 */
	public static boolean isConnecting(){
		return getState() == BluetoothService.STATE_CONNECTING || getState() == BluetoothService.STATE_FAILED;
	}
	/**
	 * 是否连接上
	 * 已连接返回true
	 * @return
	 */
	public static boolean isConnected(){
		return getState() == BluetoothService.STATE_CONNECTED;
	}
	/**
	 * 是否在检测协议
	 * @return 在检测返回true
	 */
	public static boolean isProtocoling(){
		return getState() == BluetoothService.STATE_PROTOCOL_CHECKING;
	}
	/**
	 * 是否在检测协议或已经连接上
	 * @return 在检测返回true
	 */
	public static boolean isConnectedOrProtocoling(){
		return getState() == BluetoothService.STATE_PROTOCOL_CHECKING || getState() == BluetoothService.STATE_CONNECTED;
	}
	/**
	 * 是否为空或者连接失败
	 * @return 在检测返回true
	 */
	public static boolean isConnectNoneOrFailed(){
		return getState() == BluetoothService.STATE_NONE || getState() == BluetoothService.STATE_FAILED;
	}
	
	
	
	private void log(String str){
		Log.i("override BluetoothService", ":"+str);
	}
}
