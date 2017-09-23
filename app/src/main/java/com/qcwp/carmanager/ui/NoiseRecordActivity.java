package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.NoiseRecordAdapter;
import com.qcwp.carmanager.adapter.TestRecordAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.NoiseTestModelDao;
import com.qcwp.carmanager.greendao.gen.TestSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.NoiseTestModel;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;

import java.util.List;

import butterknife.BindView;

public class NoiseRecordActivity extends BaseActivity {


    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_noise_record;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        String vinCode= UserData.getInstance().getVinCode();
        if (EmptyUtils.isNotEmpty(vinCode)) {
            final List<NoiseTestModel> list = mDaoSession.queryBuilder(NoiseTestModel.class).where(NoiseTestModelDao.Properties.VinCode.eq(vinCode)).orderDesc(NoiseTestModelDao.Properties.CreateDate).list();

            if (list.size()>0) {
                NoiseRecordAdapter testRecordAdapter = new NoiseRecordAdapter(this, list);
                listView.setAdapter(testRecordAdapter);
            }else {
                showToast("无历史记录!");
            }

        }else {
            showToast("未绑定车辆!");
        }


    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }
}
