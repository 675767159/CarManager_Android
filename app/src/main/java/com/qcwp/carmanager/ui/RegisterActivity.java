package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.RegisterInputView;
import com.qcwp.carmanager.mvp.contact.RegisterContract;
import com.qcwp.carmanager.mvp.present.RegisterPresenter;
import com.qcwp.carmanager.utils.MyActivityManager;


import butterknife.BindView;


public class RegisterActivity extends BaseActivity  implements RegisterContract.View{


    @BindView(R.id.userName)
    RegisterInputView userName;
    @BindView(R.id.nickName)
    RegisterInputView nickName;
    @BindView(R.id.mobilePhone)
    RegisterInputView mobilePhone;
    @BindView(R.id.password1)
    RegisterInputView password1;
    @BindView(R.id.password2)
    RegisterInputView password2;
    @BindView(R.id.verifCode)
    EditText verifCode;



    private RegisterContract.Presenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter=new RegisterPresenter(this);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_getVerify:
              presenter.getVerifyCode(mobilePhone.getEditTextToString());
                break;
            case R.id.button_register:

                presenter.registerUser(userName.getEditTextToString(),mobilePhone.getEditTextToString(),password1.getEditTextToString(), password2.getEditTextToString(), nickName.getEditTextToString(),verifCode.getText().toString());

                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregisterEventHandler();

    }


    @Override
    public void showProgress(String text) {

        showLoadingDialog(text);
    }

    @Override
    public void dismissProgress() {

        dismissLoadingDialog();
    }

    @Override
    public void showTip(String message) {

        showToast(message);
    }

    @Override
    public Boolean isActive() {
        return isActive;
    }

    @Override
    public void registerSuccess() {

        showLoadingDialog("注册成功，正在登录。。。");
        new Handler().postDelayed(new Runnable() {

            public void run() {
                dismissProgress();
                MyActivityManager.getInstance().exitToHome();
                readyGo(MainActivity.class);
            }
        }, 3000);

    }
}
