package com.qcwp.carmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/1/9.
 */

public class MySharedPreferences {
    private Context mcontext;
    public MySharedPreferences(Context context){
        this.mcontext=context;
    }
    public String getString(String keyname, String defaultvalue){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
       return pref.getString(keyname,defaultvalue);
    }
    public void setString(String keyname, String value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor edtior = pref.edit();
        edtior.putString(keyname, value);
        edtior.commit();
    }
    public long getLong(String keyname, long defaultvalue){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        return pref.getLong(keyname,defaultvalue);
    }
    public void setLong(String keyname, long value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor edtior = pref.edit();
        edtior.putLong(keyname, value);
        edtior.commit();
    }
    public int getInt(String keyname, int defaultvalue){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        return pref.getInt(keyname,defaultvalue);
    }
    public void setInt(String keyname, int value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor edtior = pref.edit();
        edtior.putInt(keyname, value);
        edtior.commit();
    }
    public boolean getBoolean(String keyname, boolean defaultvalue){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        return pref.getBoolean(keyname,defaultvalue);
    }
    public void setBoolean(String keyname, boolean value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor edtior = pref.edit();
        edtior.putBoolean(keyname, value);
        edtior.commit();
    }
    public float getFloat(String keyname, float defaultvalue){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        return pref.getFloat(keyname,defaultvalue);
    }
    public void setFloat(String keyname, float value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        SharedPreferences.Editor edtior = pref.edit();
        edtior.putFloat(keyname, value);
        edtior.commit();
    }
}
