package com.qcwp.carmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.EmptyUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.TravelRecordAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.obd.OBDClient;

import java.util.List;

import butterknife.BindView;

public class TravelRecordActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_travel_record;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        String vinCode = UserData.getInstance().getVinCode();
        if (EmptyUtils.isNotEmpty(vinCode)) {
            final List<TravelSummaryModel> list = mDaoSession.queryBuilder(TravelSummaryModel.class).where(TravelSummaryModelDao.Properties.VinCode.eq(vinCode)).orderDesc(TravelSummaryModelDao.Properties.StartDate).list();

            if (list != null && list.size() > 0) {
                TravelRecordAdapter travelRecordAdapter = new TravelRecordAdapter(this, list);
                listView.setAdapter(travelRecordAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (OBDClient.getDefaultClien().getConnectStatus() != OBDConnectStateEnum.connectTypeHaveBinded) {

                            TravelSummaryModel travelSummaryModel = list.get(position);
                            String travelSummaryModelStr = new Gson().toJson(travelSummaryModel);
                            Bundle bundle = new Bundle();
                            bundle.putString("travelSummaryModel", travelSummaryModelStr);
                            readyGo(DriveTrackActivity.class,bundle);


                        } else {

                            showToast("行驶过程中无法查看记录！");
                        }


                    }
                });
            } else {
                showToast("无历史记录!");
            }
        } else {
            showToast("未绑定车辆！");
        }

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }
}
