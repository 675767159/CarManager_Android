package com.qcwp.carmanager.mvp.present;

import android.app.Activity;
import android.os.Handler;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.qcwp.carmanager.engine.ApiException;
import com.qcwp.carmanager.engine.MyCallBack;
import com.qcwp.carmanager.enumeration.PathEnum;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.PhoneAuthModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.mvp.contact.LoginContract;
import com.qcwp.carmanager.mvp.contact.RegisterContract;
import com.qcwp.carmanager.ui.MainActivity;
import com.qcwp.carmanager.ui.RegisterActivity;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter {

    final RegisterContract.View view;
    final Activity activity;
    final private String TAG="RegisterPresenter";
    private EventHandler  eh;

    private String
            userName,
            mobilePhone,
            nickName,
            password;

    public RegisterPresenter(RegisterContract.View view){
        this.view=view;
        this.activity=(Activity)view;
        this.registerEventHandler();

    }

    private void registerEventHandler(){

        eh = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, final Object data) {

                 activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.dismissProgress();
                        if (result == SMSSDK.RESULT_COMPLETE) {

                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                                RegisterPresenter.this.register();
                            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                //获取验证码成功
                                view.showTip("验证码发送成功");
                            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                                //返回支持发送验证码的国家列表
                            }
                        } else {
                            view.showTip("请输入正确的验证码!");
                        }
                        Print.d(TAG,result+"---"+event+"===="+data.toString());

                    }
                });
            }



        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }


    @Override
    public void registerUser(String userName, String mobilePhone, String password1,String password2, String nickName,String verifCode) {

        if (EmptyUtils.isEmpty(userName)){
            view.showTip("请输入用户名!");
            return;
        }
        if (!RegexUtils.isMobileSimple(mobilePhone)){
            view.showTip("请输入正确的手机号!");
            return;
        }
        if (EmptyUtils.isEmpty(password1)){
            view.showTip("请输入密码!");
            return;
        }
        if (EmptyUtils.isEmpty(password2)){
            view.showTip("请确认密码!");
            return;
        }

        if (EmptyUtils.isEmpty(nickName)){
            view.showTip("请输入昵称!");
            return;
        }

        if (EmptyUtils.isEmpty(verifCode)){
            view.showTip("请输入验证码!");
            return;
        }

        if (!password1.equals(password2)){
            view.showTip("两次密码不相同!");
            return;
        }

        if (password1.length()<6||password1.length()>16)
        {
            view.showTip("密码长度在6~16之间");
            return;
        }



        this.userName=userName;
        this.mobilePhone=mobilePhone;
        this.nickName=nickName;
        this.password=password1;
        view.showProgress("验证验证码...");
        SMSSDK.submitVerificationCode("86", mobilePhone, verifCode);

    }


    @Override
    public void getVerifyCode(String phone) {

        if (!RegexUtils.isMobileSimple(phone)){
            view.showTip("请输入正确的手机号!");
            return;
        }
        this.mobilephoneAuth(phone);
    }

    @Override
    public void unregisterEventHandler() {
        SMSSDK.unregisterEventHandler(eh);
    }

    private  void mobilephoneAuth(final String phone) {

        view.showProgress("正在验证手机是否注册...");
        mEngine.mobilephoneAuth(phone).enqueue(new MyCallBack<PhoneAuthModel>() {
            @Override
            public void onCompleted() {
                if (!view.isActive()) {
                    return;
                }
                view.dismissProgress();
                completeOnceRequest();
            }

            @Override
            public void onSuccess(Call<PhoneAuthModel> call, PhoneAuthModel model) {

                if (model.getCanUse() == 1)//手机号未被注册
                {
                    view.showProgress("正在获取验证码...");
                    SMSSDK.getVerificationCode("86", phone);

                } else if (EmptyUtils.isNotEmpty(model.getMsg())) {
                   view.showTip(model.getMsg());
                } else {
                    view.showTip("该手机号已经注册！");
                }

            }

            @Override
            public void onFailed(Call<PhoneAuthModel> call, ApiException throwable) {
                view.showTip(throwable.getDisplayMessage());
            }
        });



    }


    private void register(){

        view.showProgress("正在注册...");
        mEngine.registerUser(userName,mobilePhone,password,nickName).enqueue(new MyCallBack<LoginModel>() {
            @Override
            public void onCompleted() {
                if (!view.isActive()) {
                    return;
                }
                view.dismissProgress();
                completeOnceRequest();
            }

            @Override
            public void onSuccess(Call<LoginModel> call, LoginModel model) {

                if (model.getStatus() == 1) {
                    model.setPassword(password);
                    UserData.setInstance(model);
                }else {

                   view.showTip(model.getMsg());
                }
            }

            @Override
            public void onFailed(Call<LoginModel> call,  ApiException throwable) {
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
