package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListActivity extends BaseActivity {


    @BindView(R.id.ListView)
    android.widget.ListView ListView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        final List<CarInfoModel>carInfoModels=mDaoSession.queryBuilder(CarInfoModel.class).orderDesc(CarInfoModelDao.Properties.Timestamp).list();
        List<Map<String,String>> list=new ArrayList<>();
        for (CarInfoModel carInfoModel:carInfoModels){
            Map<String,String> map=new HashMap();
            map.put(KeyEnum.vinCode,carInfoModel.getVinCode());
            map.put(KeyEnum.carNumber,carInfoModel.getCarNumber());
            list.add(map);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.view_car_edit_select_item,new String[]{KeyEnum.vinCode,KeyEnum.carNumber},new int[]{R.id.name,R.id.name_spell});
        ListView.setAdapter(simpleAdapter);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarInfoModel carInfoModel=carInfoModels.get(position);
                carInfoModel.setTimestamp(TimeUtils.getNowMills());
                mDaoSession.update(carInfoModel);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.CarSelected,carInfoModel.getVinCode()));
                finish();
            }
        });

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


}
