package com.qcwp.carmanager.control;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.qcwp.carmanager.R;


/**
 * Created by qyh on 2016/12/2.
 */

public class SystemWindow {

    private Context context;
    private WindowManager mWindowManager;
    private View mView;
    private WindowManager.LayoutParams params;

    public SystemWindow(Context context) {
        this.context = context;
        initPopupWindow();
    }


    private void initPopupWindow() {


        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mView = LayoutInflater.from(context).inflate(R.layout.view_over_speed_reminder, null);


        params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//        FIRST_APPLICATION_WINDOW
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;

        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        mWindowManager.addView(mView, params);
    }

    /**
     * 隐藏弹出框
     */
    public void hidePopupWindow() {

        mView.setVisibility(View.GONE);
    }

    public void showPopupWindow() {

        mView.setVisibility(View.VISIBLE);
    }
}
