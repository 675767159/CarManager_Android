package com.qcwp.carmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.blankj.utilcode.util.BarUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.engine.Engine;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.obd.BlueteethService;
import com.qcwp.carmanager.utils.Print;
import com.qcwp.carmanager.utils.ToastUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by qyh on 2016/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    private SweetAlertDialog mLoadingDialog;
    protected APP mApp;
    protected Engine mEngine;
    protected String TAG;
    protected DaoSession mDaoSession;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setTransparentStatusBar(this);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());

        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }



        TAG = this.getClass().getSimpleName();
        mApp = APP.getInstance();
        mEngine = mApp.getEngine();
        mDaoSession=mApp.getDaoInstant();
        initViewsAndEvents(savedInstanceState);
        EventBus.getDefault().register(this);


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
//        if (null != getLoadingTargetView()) {
//            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
//        }
    }
    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {

        return (VT) findViewById(id);
    }

    protected abstract int getContentViewLayoutID();
    /**
     * 初始化布局以及事件
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);


    /**
     * get loading target view
     */
//    protected abstract View getLoadingTargetView();


    /**
     * 初始化布局以及事件
     */
    protected abstract void onReceiveMessageEvent(MessageEvent messageEvent);


    /**
     * 需要处理点击事件时，重写该方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    public void showToast(String text) {
        ToastUtil.show(text,this);
    }

    public void showLoadingDialog() {
       showLoadingDialog("加载中...");
    }

    public void showLoadingDialog(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText(text);
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
        }
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        Print.d(TAG,"------"+event.getMessage());
        onReceiveMessageEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //请求蓝牙打开结果
        if (requestCode== BlueteethService.REQUEST_OPEN_BT_CODE){
          EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageEventType.BlueRequestResult,String.valueOf(resultCode)));
        }
    }
}
