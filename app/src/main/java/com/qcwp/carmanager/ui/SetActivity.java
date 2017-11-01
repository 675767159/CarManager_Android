package com.qcwp.carmanager.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.SetExpandableListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.CustomDialog;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.OBDConnectType;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.implement.GroupClickListener;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.mvp.present.UploadDataPresenter;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.service.MyIntentService;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.qcwp.carmanager.utils.Print;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SetActivity extends BaseActivity{


    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        final   MySharedPreferences mySharedPreferences=new MySharedPreferences(this);

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

        OBDConnectType connectType=OBDConnectType.fromInteger(mySharedPreferences.getInt(KeyEnum.connectTypeKey,OBDConnectType.WIFI.getValue()));

        Boolean connectTypeVaue=connectType!=OBDConnectType.WIFI;

         Map<String, Boolean> swictMap = new HashMap<>();
         swictMap.put("OBD类型", connectTypeVaue);
         setExpandableListAdapter.setSwicthData(swictMap);

        setExpandableListAdapter.setOnCheckedChangeListener(new SetExpandableListAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(String title, boolean isChecked) {

                switch (title) {
                    case "OBD类型":
                        OBDConnectType connectType=isChecked?OBDConnectType.BlueTooth:OBDConnectType.WIFI;
                        mySharedPreferences.setInt(KeyEnum.connectTypeKey, connectType.getValue());
                        break;

                }
                Print.d(TAG, title + "=" + isChecked);
            }
        });





    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        switch (messageEvent.getType()){
            case UploadComplete:
                dismissLoadingDialog();
                showToast("数据上传完毕!");
                break;
        }
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
                if (childPosition == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KeyEnum.typeKey, SetHelpActivity.SetHelpType.NOFIRST);
                    readyGo(SetHelpActivity.class, bundle);
                } else if (childPosition == 1) {

                    if (OBDClient.getDefaultClien().getConnectStatus()!= OBDConnectStateEnum.connectTypeHaveBinded) {
                        showLoadingDialog("正在上传数据");
                        UploadDataPresenter uploadDataPresenter = new UploadDataPresenter();
                        uploadDataPresenter.setUploadDataListener(new UploadDataPresenter.UploadDataListener() {

                            @Override
                            public void onComplete(String message) {
                                dismissLoadingDialog();
                                showToast(message);
                            }
                        });
                        uploadDataPresenter.startUploadData();
                    }else {

                        showToast("车辆行驶中，不可上传数据!");
                    }

                }
                break;
            case 2:
                if (childPosition == 0) {
                    readyGo(AboutActivity.class);

                } else if (childPosition == 1) {
                    Print.d(TAG,"---------");
                    exitAccount();

                }
                break;
        }
    }


    private void exitAccount() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
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
        builder.setNegativeButton("取消", null);
        CustomDialog customDialog = builder.create();
        customDialog.show();


    }


}
