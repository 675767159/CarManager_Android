package com.qcwp.carmanager.ui;

import android.os.Bundle;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;

public class AboutActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }
}
