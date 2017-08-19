package com.qcwp.carmanager.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.mvp.contact.LoginContract;
import com.qcwp.carmanager.mvp.present.LoginPresenter;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;


import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseActivity implements LoginContract.View {


    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.edit_text_user)
    EditText editTextUser;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    private LoginContract.Presenter loginPresenter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        CommonUtils.setViewCorner(buttonLogin, 20, 20, Color.parseColor("#23943D"));
        loginPresenter=new LoginPresenter(this);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_login:{

                String loginName = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                loginPresenter.login(loginName,password);
                break;
            }
            case R.id.button_register:
                readyGo(RegisterActivity.class);
                break;
        }
    }



    @Override
    public void loginSuccess() {

        readyGoThenKill(MainActivity.class);
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



}
