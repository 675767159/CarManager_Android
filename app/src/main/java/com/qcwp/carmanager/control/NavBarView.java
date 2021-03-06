package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.adapter.VinCodeListAdapter;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.CarBrandModelDao;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.greendao.gen.TravelSummaryModelDao;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.implement.LocationClient;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.obd.SensorsService;
import com.qcwp.carmanager.ui.BaseActivity;
import com.qcwp.carmanager.ui.CarEditActivity;
import com.qcwp.carmanager.utils.MyActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by qyh on 2016/11/30.
 */

public class NavBarView extends RelativeLayout {

    private  TextView textView;
    private Context context;
    private ImageButton button;
    private DaoSession mDaoSession;
    public NavBarView(Context context) {
        super(context);
    }

    public NavBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public NavBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(final Context context, AttributeSet attrs) {
        this.context=context;
        mDaoSession=APP.getInstance().getDaoInstant();
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.NavBarView);
        String  text=typedArray.getString(R.styleable.NavBarView_title);
        int background=typedArray.getResourceId(R.styleable.NavBarView_background_self_define,0);
        Boolean hiddenBackButton=typedArray.getBoolean(R.styleable.NavBarView_hiddenBackButton,false);
        Boolean hiddenConnectButton=typedArray.getBoolean(R.styleable.NavBarView_hiddenConnectButton,false);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_navbar,null);

        textView=(TextView)view.findViewById(R.id.navBar_title);
        textView.setText(text);



        if (hiddenConnectButton){

            textView.setEnabled(false);
            textView.setCompoundDrawables(null, null, null, null);
        }else {
            if (OBDClient.getDefaultClien().getConnectStatus()== OBDConnectStateEnum.connectTypeHaveBinded){
                NavBarView.this.setTitleConnectedStatus(true);
            }

        }

        button=(ImageButton)view.findViewById(R.id.navBar_back);
        if (background!=0){
            view.setBackgroundResource(background);
        }





        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBarView.this.onClick(context);

            }
        });

        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view,param);

        if (!hiddenBackButton) {
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivityManager.getInstance().getCurrentActivity().finish();
                }
            });
        }else {
            button.setVisibility(GONE);
        }

        if (!EventBus.getDefault().isRegistered(NavBarView.this)) {
            EventBus.getDefault().register(NavBarView.this);
        }


    }
    public void setTitle(String title){
        textView.setText(title);
    }
    public String getTitle(){
       return (String) textView.getText();
    }
    public void setBackButtonHidden(Boolean hidden){
        if (hidden){
            button.setVisibility(GONE);
        }else {
            button.setVisibility(VISIBLE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        switch (event.getType()){
            case CarBlindSuccess:
                NavBarView.this.setTitleConnectedStatus(true);
                break;
            case OBDLostDisconnection:
                NavBarView.this.setTitleConnectedStatus(false);
                break;
        }

    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();
        EventBus.getDefault().unregister(this);

    }

   private void onClick(Context context){
       final BaseActivity currentActivity=(BaseActivity)context ;
       currentActivity.showLoadingDialog("正在连接...");
       final OBDClient obdClient=OBDClient.getDefaultClien();
       TravelSummaryModel travelSummaryModel=APP.getInstance().getDaoInstant().queryBuilder(TravelSummaryModel.class).orderDesc(TravelSummaryModelDao.Properties.OnlyFlag).limit(1).unique();
       if (travelSummaryModel==null){
           obdClient.setOnlyFlag(0);
       }else {
           obdClient.setOnlyFlag(travelSummaryModel.getOnlyFlag()+1);
       }
       obdClient.setStartTime(TimeUtils.getNowString());
       SensorsService.initData();
       obdClient.setReadVinCodeCompleteListener(new OBDClient.ReadVinCodeCompleteListener() {
           @Override
           public void connectComplete(Boolean success, String message) {
               currentActivity.dismissLoadingDialog();
               if (success) {

                   if (message==null||message.length()!=17){

                       AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity,android.R.style.Theme_Holo_Light_Dialog);
                       final List<CarInfoModel> list=mDaoSession.queryBuilder(CarInfoModel.class).list();
                       builder.setTitle("读不到车架号,请选择或输入您的车架号");
                       builder.setAdapter(new VinCodeListAdapter(currentActivity,list), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               if (which<list.size()){

                                   MessageEvent messageEvent = new MessageEvent();
                                   messageEvent.setType(MessageEvent.MessageEventType.CarBlindSuccess);
                                   String vinCode=list.get(which).getVinCode();
                                   messageEvent.setMessage(vinCode);
                                   obdClient.setVinCode(vinCode);
                                   UserData.getInstance().setVinCode(vinCode);
                                   EventBus.getDefault().post(messageEvent);

                               }else {
                                   Intent intent = new Intent(currentActivity, CarEditActivity.class);
                                   intent.putExtra(KeyEnum.vinCode, "");
                                   intent.putExtra(KeyEnum.typeKey, CarEditActivity.Type.Bind);
                                   currentActivity.startActivity(intent);
                               }
                           }
                       });

                       builder.show();

                   }else {

                       CarInfoModel carInfoModel = mDaoSession.queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(message)).unique();
                       if (carInfoModel == null) {

                           Intent intent = new Intent(currentActivity, CarEditActivity.class);
                           intent.putExtra(KeyEnum.vinCode, message);
                           intent.putExtra(KeyEnum.typeKey, CarEditActivity.Type.Bind);
                           currentActivity.startActivity(intent);

                       } else {
                           MessageEvent messageEvent = new MessageEvent();
                           UserData.getInstance().setVinCode(message);
                           messageEvent.setType(MessageEvent.MessageEventType.CarBlindSuccess);
                           messageEvent.setMessage(message);
                           EventBus.getDefault().post(messageEvent);
                       }

                   }

                   LocationClient locationClient =LocationClient.getDefaultClien();
                   locationClient.startLocation();
               }

               currentActivity.showToast(message);


           }

       });
       obdClient.startOBDClient();

   }

   private void setTitleConnectedStatus(Boolean success){

       Drawable leftDrawableOne = null;
       if (success) {
           leftDrawableOne = ContextCompat.getDrawable(context, R.mipmap.obd_connect_succes);
           textView.setEnabled(false);
       }else {
           leftDrawableOne = ContextCompat.getDrawable(context, R.mipmap.obd_connect_failed);
           textView.setEnabled(true);
       }
       leftDrawableOne.setBounds(0, 0, leftDrawableOne.getIntrinsicWidth(), leftDrawableOne.getIntrinsicHeight());//非常重要，必须设置，否则图片不会显示

       textView.setCompoundDrawables(leftDrawableOne, null, null, null);


   }




}
