package com.qcwp.carmanager.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.User;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.ObjectWriter;
import com.qcwp.carmanager.utils.Print;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.edit_text_user)
    EditText editTextUser;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        CommonUtils.setViewCorner(buttonLogin, 20, 20, Color.parseColor("#23943D"));
        KeyboardUtils.clickBlankArea2HideSoftInput();




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_login:{

                String userName = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                if (EmptyUtils.isNotEmpty(userName) && EmptyUtils.isNotEmpty(password)) {
                    if (password.length()>5)
                    login(userName, password);
                    else showToast("密码错误");
                } else {
                    showToast("请输入完整登录信息");
                }

                break;
            }
            case R.id.button_register:
                readyGo(RegisterActivity.class);
                break;
        }
    }

    private void login(String userName, final String password) {

        showLoadingDialog();
        mEngine.login(userName, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                dismissLoadingDialog();
                LoginModel  model=response.body();
                if (model.getStatus()==1) {
                     Print.d(TAG,model.getMemberId()+"");
                      model.setPassword(password);
                      UserData.setInstance(model);
                      //这个接口是用来设置Gson转换的排除策略的
                      String UserDataStr=new Gson().toJson(model);
                      FileIOUtils.writeFileFromString(mApp.getMyFileFolder(PathEnum.UserInfo),UserDataStr);
                      Print.d(TAG,model.toString());
                      readyGoThenKill(MainActivity.class);
                }else {
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                dismissLoadingDialog();
                showToast(t.getMessage()+"");
            }
        });
    }

}
