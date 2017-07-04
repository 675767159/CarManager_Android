package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.PathEnum;

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
        readyGoThenKill(LoginActivity.class);
    }
}
