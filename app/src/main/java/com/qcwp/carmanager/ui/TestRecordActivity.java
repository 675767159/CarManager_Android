package com.qcwp.carmanager.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.TestRecordAdapter;
import com.qcwp.carmanager.adapter.TrackRecordAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.NavBarView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.greendao.gen.TestSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;

import java.util.List;

import butterknife.BindView;

public class TestRecordActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.NavbarView)
    NavBarView navBarView;

    @BindView(R.id.testDetailImage)
    ImageView testDetailImage;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test_record;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        Intent intent=getIntent();
        ProfessionalTestEnum professionalTestType=(ProfessionalTestEnum)intent.getSerializableExtra(KeyEnum.professionalTestType);

        switch (professionalTestType){
            case HectometerAccelerate:
                navBarView.setTitle(this.getString(R.string.hectometerAccelerateName));
                break;
            case KilometersAccelerate:
                navBarView.setTitle(this.getString(R.string.kilometersAccelerateName));
                break;
            case KilometersBrake:
                navBarView.setTitle(this.getString(R.string.kilometersBrakeName));
                break;
        }

        String vinCode= UserData.getInstance().getVinCode();
        if (EmptyUtils.isNotEmpty(vinCode)) {
            final List<TestSummaryModel> list = mDaoSession.queryBuilder(TestSummaryModel.class).where(TestSummaryModelDao.Properties.VinCode.eq(vinCode),TestSummaryModelDao.Properties.TestType.eq(professionalTestType.getValue())).orderDesc(TestSummaryModelDao.Properties.CreateDate).list();

            if (list.size()>0) {

                TestRecordAdapter testRecordAdapter = new TestRecordAdapter(this, list, professionalTestType);
                listView.setAdapter(testRecordAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (OBDClient.getDefaultClien().getConnectStatus() != OBDConnectStateEnum.connectTypeHaveBinded) {
                            TestSummaryModel testSummaryModel = list.get(position);
                            String crateDate = testSummaryModel.getCreateDate();
                            Glide.with(TestRecordActivity.this).load(TestRecordActivity.this.mApp.getMyFileFolder(crateDate)).into(testDetailImage);
                            testDetailImage.setVisibility(View.VISIBLE);

                        } else {

                            showToast("行驶过程中无法查看记录！");
                        }


                    }
                });
            }else {
                showToast("无历史记录!");
            }
        }else {
            showToast("未绑定车辆！");
        }


    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.testDetailImage:
                testDetailImage.setVisibility(View.GONE);
                break;
        }
    }
}
