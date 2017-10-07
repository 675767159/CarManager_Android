package com.qcwp.carmanager.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.blankj.utilcode.util.EmptyUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.SetExpandableListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.implement.GroupClickListener;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class RemindSetActivity extends BaseActivity {


    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;

    private AlertDialog alertDialog;
    private int ID = 0;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_remind_set;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        JSONArray setItemArray = CommonUtils.getJSONArrayFromText("SetReminderItem.json");

        final SetExpandableListAdapter setExpandableListAdapter = new SetExpandableListAdapter(this, setItemArray);
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

        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        final Boolean isOpenedOverspeed = mySharedPreferences.getBoolean(KeyEnum.isOpenedOverspeed, false);
        final Boolean isOpenedCrashRemind = mySharedPreferences.getBoolean(KeyEnum.isOpenedCrashRemind, false);

        Map<String, Boolean> switchMap = new HashMap<String, Boolean>() {{
            put("开启状态", isOpenedOverspeed);
            put("通知联系人", isOpenedCrashRemind);
        }};


        setExpandableListAdapter.setSwicthData(switchMap);
        setExpandableListAdapter.setOnCheckedChangeListener(new SetExpandableListAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(String title, boolean isChecked) {

                switch (title) {
                    case "开启状态":
                        mySharedPreferences.setBoolean(KeyEnum.isOpenedOverspeed, isChecked);
                        break;
                    case "通知联系人":
                        mySharedPreferences.setBoolean(KeyEnum.isOpenedCrashRemind, isChecked);
                        break;
                }
                Print.d(TAG, title + "=" + isChecked);
            }
        });

        final String overSpeed = mySharedPreferences.getString(KeyEnum.overspeed, "");
        final String contactPhone = mySharedPreferences.getString(KeyEnum.contactPhone, "");

        final Map<String, String> contentMap = new HashMap<String, String>() {{
            put("提醒速度", overSpeed);
            put("联系人号码", contactPhone);
        }};
        setExpandableListAdapter.setContentMap(contentMap);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = editText.getText().toString();
                switch (ID) {
                    case 0:
                        mySharedPreferences.setString(KeyEnum.overspeed, str);
                        if (EmptyUtils.isNotEmpty(str))
                            contentMap.put("提醒速度", str);
                        break;
                    case 1:
                        mySharedPreferences.setString(KeyEnum.contactPhone, str);
                        if (EmptyUtils.isNotEmpty(str))
                            contentMap.put("联系人号码", str);
                        break;
                }

                setExpandableListAdapter.setContentMap(contentMap);
            }
        });
        alertDialog = builder.create();

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    private void onClick(int groupPosition, int childPosition) {

        switch (groupPosition) {
            case 0:
                ID = 0;
                alertDialog.setTitle("请输入提醒速度");
                alertDialog.show();

                break;
            case 1:
                ID = 1;
                alertDialog.setTitle("请输入联系人手机号");
                alertDialog.show();
                break;
        }
    }



}
