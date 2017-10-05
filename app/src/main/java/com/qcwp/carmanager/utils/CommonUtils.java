package com.qcwp.carmanager.utils;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.implement.StateRoundRectDrawable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by qyh on 2016/11/12.
 */

public class CommonUtils {
    public  static void setViewCorner(View view, float topRadius, float bottomRadius, int normalColor){
        StateRoundRectDrawable mRoundRectDradable = new StateRoundRectDrawable(normalColor, Color.GRAY);
        mRoundRectDradable.setBottomLeftRadius(bottomRadius);
        mRoundRectDradable.setBottomRightRadius(bottomRadius);
        mRoundRectDradable.setTopLeftRadius(topRadius);
        mRoundRectDradable.setTopRightRadius(topRadius);
        int alpha= Color.alpha(normalColor);
        mRoundRectDradable.setAlpha(alpha);//最大值FF(十六进制),255(十进制)
        view.setBackground(mRoundRectDradable);

    }
    public static String getMyFileFolder(Context context) {
        String mFileFolder;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mFileFolder = context.getExternalCacheDir().getPath() + File.separator;
        } else {
            mFileFolder = context.getCacheDir().getPath() + File.separator;
        }

        return mFileFolder;
    }

    public static  String longTimeToStr(int sec){
        String timeStr = "";
        int day = sec / (60 * 60 * 24);
        sec -= day * 60 * 60 * 24;
        int ousr = sec / (60 * 60);
        sec -= ousr * 60 * 60;
        int minutes = sec / 60;
        sec -= minutes * 60;

        Locale locale = Locale.getDefault();
        if (day > 0) {
            timeStr = timeStr + String.format(locale, "%d天", day);
        }
        if (ousr > 0) {
            timeStr = timeStr + String.format(locale, "%d小时", ousr);
        }
        if (day > 0) {
            timeStr = timeStr + String.format(locale, "%d分", minutes);
            return  timeStr;
        }
        if (sec < 10) {
            timeStr = timeStr + String.format(locale, "%d分0%d秒", minutes, sec);
        }else{
            timeStr = timeStr + String.format(locale, "%d分%d秒", minutes, sec);
        }
        return  timeStr;
    }

    static public BluetoothSocket setPin(Class btClass, BluetoothDevice btDevice,
                                 String str) throws Exception
    {
        try
        {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
                    byte[].class);
            BluetoothSocket socket = (BluetoothSocket) removeBondMethod.invoke(btDevice,
                    new Object[]
                            {str.getBytes()});
            Print.d("startBluetoothService","startBluetoothService=="+socket);
            return socket;

        }
        catch (SecurityException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    public static int  stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return Integer.parseInt(sbu.toString());
    }

    public static JSONArray getJSONArrayFromText(String fileName){

           /*获取到assets文件下的TExt.json文件的数据，并以输出流形式返回。*/
        InputStream is = APP.getInstance().getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line);
            }
            reader.close();
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray person = null;
        try {
            person = new JSONArray(stringBuilder.toString());
            return person;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String carCheckUpStatus(int score) {
        String status = "未知";
        if (score <= 70) status = "较差";
        else if (score <= 80) status = "一般";
        else if (score <= 90) status = "良好";
        else if (score <= 100) status ="非常棒";
        return  status;
    }

    public static double MIN(double number1,double number2){
        return number1<number2?number1:number2;
    }

    public static double MAX(double number1,double number2){
        return number1>number2?number1:number2;
    }
    //这种方法状态栏是空白，显示不了状态栏的信息
    public static void saveCurrentImage(Activity activity,String crateDate)
    {
        //获取当前屏幕的大小
        int width = activity.getWindow().getDecorView().getRootView().getWidth();
        int height = activity.getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        //找到当前页面的跟布局
        View view =  activity.getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();

        //输出到sd卡
        if (FileUtils.createOrExistsDir(APP.getInstance().getMyFileFolder(""))) {
            File file = new File(APP.getInstance().getMyFileFolder(crateDate));
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream foStream = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
            } catch (Exception e) {
                Print.e("Show", e.toString());
            }
        }
    }

    public static List listEName(DaoSession session,String SQL_DISTINCT_ENAME) {
        ArrayList<String> result = new ArrayList<>();
        Cursor c = session.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try{
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }



    public static boolean currentWifiIsOBDWIFI(WifiManager wifiManager) {

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level=wifiInfo.getRssi();
        Print.d("SCAN_RESULTS_AVAILABLE_ACTION","level="+level);

        return wifiInfo != null && wifiInfo.getSSID().contains("OBD") && level > -100;
    }


    public static Boolean createOBDWIFI(WifiManager wifiManager){


        Print.d("SCAN_RESULTS_AVAILABLE_ACTION","--------------");
        wifiManager.startScan();
        List<ScanResult> resultss = wifiManager.getScanResults();
        Print.d("SCAN_RESULTS_AVAILABLE_ACTION",resultss.size()+"----");
        WifiConfiguration wificong = null;
        for (int i=0;i<10;i++) {

            List<ScanResult> results = wifiManager.getScanResults();
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION",results.size()+"----");
            for (ScanResult result : results) {
                Print.d("SCAN_RESULTS_AVAILABLE_ACTION", result.SSID + "," + result.BSSID + ",  " + result.level);
                if (result.SSID.contains("OBD")) {
                    wificong = new WifiConfiguration();
                    wificong.SSID = "\"" + result.SSID + "\"";
                    wificong.BSSID = result.BSSID;
                    wificong.status = WifiConfiguration.Status.ENABLED;
                    wificong.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wificong.wepKeys[0] = "\"" + "\"";

                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (wificong!=null){
                break;
            }
        }

        Boolean isSuccess=false;
        if (wificong != null) {
            int newNetworkId = wifiManager.addNetwork(wificong);
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "state------" + wifiManager.getWifiState());
            Boolean a=false;
            int i=0;
            while (!a&&i<5) {
                a=wifiManager.enableNetwork(newNetworkId, true);
                i++;
            }
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "a------" + a);
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "state------" + wifiManager.getWifiState());
            if (a) {
                wifiManager.saveConfiguration();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "state------" + wifiManager.getWifiState());

            i=0;
            a=false;
            while (i<5&&!a){
                a=CommonUtils.currentWifiIsOBDWIFI(wifiManager);
                Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "aa------" +a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            Print.d("SCAN_RESULTS_AVAILABLE_ACTION", "a------" +a);

            if (a) {
                isSuccess = true;
            }



        }

        return isSuccess;

    }

    static public Boolean isGpsOPen(Activity activity) {
        PackageManager pm =activity.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", "com.qcwp.carmanager"));
        return permission;
    }

    static public void openGPS(Activity activity,int code) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                code);
        Print.d("startBluetoothService", "result==VERSION");
    }

}
