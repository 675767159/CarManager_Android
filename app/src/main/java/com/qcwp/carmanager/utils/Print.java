package com.qcwp.carmanager.utils;

import android.util.Log;

import com.qcwp.carmanager.APP;


/**
 * Created by qyh on 2016/11/29.
 */

public class Print {

    private final static int LOG_MAXLENGTH = 2000;
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
            if (APP.isDebug&&msg!=null) {
                long length = msg.length();
                if (length <= LOG_MAXLENGTH ) {// 长度小于等于限制直接打印
                    Log.d(tag, msg);
                }else {
                    while (msg.length() > LOG_MAXLENGTH ) {// 循环分段打印日志
                        String logContent = msg.substring(0, LOG_MAXLENGTH );
                        msg = msg.replace(logContent, "");
                        Log.d(tag, logContent);
                    }
                    Log.d(tag, msg);// 打印剩余日志
                }


            }

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
