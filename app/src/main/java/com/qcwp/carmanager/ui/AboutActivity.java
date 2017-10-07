package com.qcwp.carmanager.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.createDate)
    TextView createDate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        version.setText("版本：" + AppUtils.getAppVersionName());

        String releaseTime = null;
        try {
            releaseTime = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData.getString("releaseTime");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        createDate.setText("日期：" + releaseTime);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


}
