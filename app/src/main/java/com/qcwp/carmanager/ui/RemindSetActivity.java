package com.qcwp.carmanager.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.SetExpandableListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.implement.GroupClickListener;
import com.qcwp.carmanager.utils.CommonUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class RemindSetActivity extends BaseActivity {


    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_remind_set;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        JSONArray setItemArray = CommonUtils.getJSONArrayFromText("SetReminderItem.json");

        SetExpandableListAdapter setExpandableListAdapter=new SetExpandableListAdapter(this,setItemArray);
        expandablelistview.setAdapter(setExpandableListAdapter);
        for (int i=0; i<setItemArray.length(); i++) {
            expandablelistview.expandGroup(i);
        }

        expandablelistview.setOnGroupClickListener(new GroupClickListener());
        expandablelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onClick(groupPosition,childPosition);
                return false;
            }
        });



    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    private void onClick(int groupPosition, int childPosition) {


    }



}
