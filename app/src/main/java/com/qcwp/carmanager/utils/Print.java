package com.qcwp.carmanager.utils;

import android.util.Log;

import com.qcwp.carmanager.APP;


/**
 * Created by qyh on 2016/11/29.
 */

public class Print {


        public final static void e(String tag, String msg, Throwable tr) {
            if (APP.isDebug&&msg!=null)
                Log.e(tag, msg, tr);
        }
        public final static void e(String tag, String msg) {
            if (APP.isDebug&&msg!=null)
                Log.e(tag, msg);
        }

        public final static void e(String msg) {
            if (APP.isDebug&&msg!=null)
                Log.e("", msg);
        }

        public final static void e(Throwable tr) {
            if (APP.isDebug&&tr!=null)
                Log.e("", "", tr);
        }

        public final static void d(String tag, String msg) {
            if (APP.isDebug&&msg!=null)
                Log.d(tag, msg);

        }

        public final static void d(String msg) {
            if (APP.isDebug&&msg!=null)
                Log.d("", msg);
        }

        public final static void d(Throwable tr) {
            if (APP.isDebug&&tr!=null)
                Log.d("", "", tr);
        }

    }
