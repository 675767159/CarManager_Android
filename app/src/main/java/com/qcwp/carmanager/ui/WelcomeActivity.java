package com.qcwp.carmanager.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.enumeration.TimeEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.Print;


public class WelcomeActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        new Handler().postDelayed(new Runnable() {
            public void run() {





                LogUtils.file("密码是否为空");

                if (EmptyUtils.isNotEmpty(UserData.getInstance().getUserName())&&EmptyUtils.isNotEmpty(UserData.getInstance().getPassword())){
                    LogUtils.json("密码不为空");
                    readyGoThenKill(MainActivity.class);


                } else {
                    LogUtils.json("密码为空");
                    readyGoThenKill(LoginActivity.class);
                }

            }
        }, TimeEnum.Launch_Time);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }
}
