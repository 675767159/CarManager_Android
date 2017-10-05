package com.qcwp.carmanager.mvp.present;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.engine.ApiException;
import com.qcwp.carmanager.engine.MyCallBack;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.mvp.contact.FeedBackContract;
import com.qcwp.carmanager.mvp.contact.LoginContract;
import com.qcwp.carmanager.ui.MainActivity;
import com.qcwp.carmanager.utils.Print;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    public LoginPresenter(LoginContract.View view){
        this.view=view;

    }

    @Override
    public void login(String  loginName,final String password) {

        // 开始验证输入内容
        if (EmptyUtils.isEmpty(loginName)) {
            view.showTip("用户名不能为空");
            return;
        }
        if (EmptyUtils.isEmpty(password)) {
            view.showTip("密码不能为空!");
            return;
        }
        if (password.length()<6) {
            view.showTip("密码错误!");
            return;
        }

        view.showProgress("正在登陆");



        mEngine.login(loginName,password).enqueue(new MyCallBack<LoginModel>() {
            @Override
            public void onCompleted() {
                if (!view.isActive()) {
                    return;
                }
                view.dismissProgress();
                completeOnceRequest();

            }


            @Override
            public void onSuccess(Call<LoginModel> call, final LoginModel model) {

                if (model.getStatus()==1) {
                    model.setPassword(password);
                    UserData.setInstance(model);
                    view.loginSuccess();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //这个接口是用来设置Gson转换的排除策略的
                            String UserDataStr=new Gson().toJson(model);
                            FileIOUtils.writeFileFromString(mApp.getMyFileFolder(PathEnum.UserInfo),UserDataStr);
                            Print.d("LoginPresenter",model.toString());
                        }
                    }).start();

                }else {
                    view.showTip(model.getMsg());
                }
            }

            @Override
            public void onFailed(Call<LoginModel> call, ApiException throwable) {
                view.showTip(throwable.getDisplayMessage());
            }
        });



    }

    @Override
    int getRequestCount() {
        return 1;
    }

    @Override
    void completeAllRequest() {

    }
}
