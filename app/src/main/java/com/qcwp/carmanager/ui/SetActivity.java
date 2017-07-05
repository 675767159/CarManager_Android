package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.MyActivityManager;

public class SetActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        FileUtils.deleteFile(mApp.getMyFileFolder(PathEnum.UserInfo));
        MyActivityManager.getInstance().exitToHome();
        UserData.dropInstance();
        readyGo(LoginActivity.class);
    }
}
