package com.qcwp.carmanager.control;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.greendao.gen.CarInfoModelDao;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.obd.BluetoothService;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.obd.SensorsService;
import com.qcwp.carmanager.ui.BaseActivity;
import com.qcwp.carmanager.ui.EditCarActivity;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;


/**
 * Created by qyh on 2016/11/30.
 */

public class NavBarView extends RelativeLayout {

    private  TextView textView;
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


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.NavBarView);
        String  text=typedArray.getString(R.styleable.NavBarView_title);
        int background=typedArray.getResourceId(R.styleable.NavBarView_background_self_define,0);
        Boolean hiddenBackButton=typedArray.getBoolean(R.styleable.NavBarView_hiddenBackButton,false);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_navbar,null);

        textView=(TextView)view.findViewById(R.id.navBar_title);
        textView.setText(text);

        Button button=(Button)view.findViewById(R.id.navBar_back);
        if (background!=0){
            view.setBackgroundResource(background);
        }

        ImageButton button_obd_connect=(ImageButton)view.findViewById(R.id.button_obd);

        final BaseActivity currentActivity=(BaseActivity) context;

        button_obd_connect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                currentActivity.showLoadingDialog("正在连接...");
                OBDClient.readVinCode(new OBDClient.ReadVinCodeCompleteListener() {

                    @Override
                    public void connectComplete(Boolean success, final String message) {
                        currentActivity.dismissLoadingDialog();
                        if (success) {
                            currentActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currentActivity.showToast(message);
                                }
                            });

                            CarInfoModel carInfoModel = APP.getInstance().getDaoInstant().queryBuilder(CarInfoModel.class).where(CarInfoModelDao.Properties.VinCode.eq(message)).unique();
                            if (carInfoModel==null){

                                Intent intent=new Intent(currentActivity, EditCarActivity.class);
                                currentActivity.startActivity(intent);

                            }
                        }else {

                            currentActivity.showToast(message);
                        }


                    }
                });



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




    }
    public void setTitle(String title){
        textView.setText(title);
    }
    public String getTitle(){
       return (String) textView.getText();
    }



}
