package com.qcwp.carmanager.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.SetExpandableListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.CustomDialog;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.implement.GroupClickListener;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity {


    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {



        JSONArray setItemArray = CommonUtils.getJSONArrayFromText("SetItem.json");

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

    @Override
    public void onClick(View v) {
        super.onClick(v);

        FileUtils.deleteFile(mApp.getMyFileFolder(PathEnum.UserInfo));
        MyActivityManager.getInstance().exitToHome();
        UserData.dropInstance();
        readyGo(LoginActivity.class);
    }


    private void onClick(int groupPosition, int childPosition) {

        switch (groupPosition){
            case 0:
                if (childPosition==0){
                    readyGo(RemindSetActivity.class);
                }
                break;
            case 1:
                break;
            case 2:
                if (childPosition==1){
                    Print.d(TAG,"---------");
                    CustomDialog.Builder builder=new CustomDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("是否退出账号？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyActivityManager.getInstance().exitToHome();
                            UserData.dropInstance();
                            readyGo(LoginActivity.class);

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    CustomDialog customDialog= builder.create();
                    customDialog.show();


                }
                break;
        }
    }


}
