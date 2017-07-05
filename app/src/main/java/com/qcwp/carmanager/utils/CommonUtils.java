package com.qcwp.carmanager.utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import com.qcwp.carmanager.implement.StateRoundRectDrawable;

import java.io.File;
import java.lang.reflect.Method;


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
        String timeStr = new String();
        int day = sec / (60 * 60 * 24);
        sec -= day * 60 * 60 * 24;
        int ousr = sec / (60 * 60);
        sec -= ousr * 60 * 60;
        int minutes = sec / 60;
        sec -= minutes * 60;

        if (day > 0) {
            timeStr= timeStr+String.format("%d天",day);
        }
        if (ousr > 0) {
            timeStr= timeStr+String.format("%d小时",ousr);
        }
        if (day > 0) {
            timeStr= timeStr+String.format("%d分",minutes);
            return  timeStr;
        }
        if (sec < 10) {
            timeStr= timeStr+String.format("%d分0%d秒",minutes,sec);
        }else{
            timeStr= timeStr+String.format("%d分%d秒",minutes,sec);
        }
        return  timeStr;
    }

    static public boolean setPin(Class btClass, BluetoothDevice btDevice,
                                 String str) throws Exception
    {
        try
        {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
                    byte[].class);
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
                    new Object[]
                            {str.getBytes()});
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
        return true;

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
}
