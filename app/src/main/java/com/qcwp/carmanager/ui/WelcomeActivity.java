package com.qcwp.carmanager.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
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


                String userInfoStr= FileIOUtils.readFile2String(mApp.getMyFileFolder(PathEnum.UserInfo));
                if (EmptyUtils.isNotEmpty(userInfoStr)){

                    LoginModel userInfo=new Gson().fromJson(userInfoStr,LoginModel.class);
                    Print.d(TAG,userInfo.toString());
                    if (EmptyUtils.isNotEmpty(userInfo)){
                        UserData.setInstance(userInfo);
                        readyGoThenKill(MainActivity.class);
                    }else {
                        readyGoThenKill(LoginActivity.class);
                    }

                } else {
                    readyGoThenKill(LoginActivity.class);
                }

            }
        }, TimeEnum.Launch_Time);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }
}
