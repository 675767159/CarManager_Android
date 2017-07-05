package com.qcwp.carmanager.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.InputMethodLayout;
import com.qcwp.carmanager.control.RegisterInputView;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.PhoneAuthModel;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends BaseActivity {


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
    @BindView(R.id.button_getVerify)
    Button buttonGetVerify;
    @BindView(R.id.button_register)
    Button buttonRegister;
    @BindView(R.id.verifCode)
    EditText verifCode;
    @BindView(R.id.scrollView)
    ScrollView scrollView;


    private String
            userName_str,
            mobilePhone_str,
            nickName_str,
            password1_str,
            password2_str,
            verifCode_str;
    private EventHandler eh;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        eh = new EventHandler() {

            @Override
            public void afterEvent(final int event, final int result, final Object data) {

                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoadingDialog();
                        if (result == SMSSDK.RESULT_COMPLETE) {

                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                                RegisterActivity.this.register();
                            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                //获取验证码成功
                                showToast("获取验证码成功");
                            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                                //返回支持发送验证码的国家列表
                            }
                        } else {

                            showToast("请输入正确的验证码!");
                        }
                        Print.d(TAG,result+"---"+event+"===="+data.toString());

                    }
                });
            }



        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
//        this.addLayoutListener(inputMethodLayout,buttonRegister);

    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

//    /**
//     *  1、获取main在窗体的可视区域
//     *  2、获取main在窗体的不可视区域高度
//     *  3、判断不可视区域高度
//     *      1、大于100：键盘显示  获取Scroll的窗体坐标
//     *                           算出main需要滚动的高度，使scroll显示。
//     *      2、小于100：键盘隐藏
//     *
//     * @param main 根布局
//     * @param scroll 需要显示的最下方View
//     */
//    public void addLayoutListener(final View main, final View scroll) {
//        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                main.getWindowVisibleDisplayFrame(rect);
//                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
//                if (mainInvisibleHeight > 100) {
//                    int[] location = new int[2];
//                    scroll.getLocationInWindow(location);
//                    Print.d("addLayoutListener",location[0]+"---"+location[1]);
//                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
//                    main.scrollTo(0, srollHeight);
//                } else {
//                    main.scrollTo(0, 0);
//                }
//            }
//        });
//    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_getVerify:
                String phone = mobilePhone.getEditTextToString();
                if (!RegexUtils.isMobileSimple(phone)) {
                    showToast("请输入正确的手机号码");
                } else {
                    this.getVerifyCode(phone);
                }
                break;
            case R.id.button_register:
                userName_str = userName.getEditTextToString();
                mobilePhone_str = mobilePhone.getEditTextToString();
                nickName_str = nickName.getEditTextToString();
                password1_str = password1.getEditTextToString();
                password2_str = password2.getEditTextToString();
                verifCode_str = verifCode.getText().toString();

                if (EmptyUtils.isNotEmpty(userName_str) && EmptyUtils.isNotEmpty(mobilePhone_str) && EmptyUtils.isNotEmpty(nickName_str) && EmptyUtils.isNotEmpty(password1_str) && EmptyUtils.isNotEmpty(password2_str) && EmptyUtils.isNotEmpty(verifCode_str)) {
                    if (password1_str.equals(password2_str)) {
                        showLoadingDialog();
                        Print.d(TAG, "---");
                        SMSSDK.submitVerificationCode("86", mobilePhone_str, verifCode_str);
                    } else
                        showToast("两次密码不一样");
                } else {
                    showToast("请完善注册信息");
                }
                break;

        }
    }

    private void getVerifyCode(final String phone) {


        showLoadingDialog();
        mEngine.mobilephoneAuth(phone).enqueue(new Callback<PhoneAuthModel>() {
            @Override
            public void onResponse(Call<PhoneAuthModel> call, Response<PhoneAuthModel> response) {
                dismissLoadingDialog();
                PhoneAuthModel model = response.body();
                if (model.getCanUse() == 1)//手机号未被注册
                {
                    showLoadingDialog();

                    SMSSDK.getVerificationCode("86", phone);


                } else if (EmptyUtils.isNotEmpty(model.getMsg())) {
                    showToast(model.getMsg());
                } else {
                    showToast("该手机号已经注册！");
                }
            }

            @Override
            public void onFailure(Call<PhoneAuthModel> call, Throwable t) {
                dismissLoadingDialog();
            }
        });

    }


    private void register() {

        mEngine.registerUser(userName_str, mobilePhone_str, password1_str, nickName_str).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel model = response.body();
                if (model.getStatus() == 1) {
                    model.setPassword(password1_str);
                    UserData.setInstance(model);
                    showToast("注册成功，正在登录。。。");
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            MyActivityManager.getInstance().exitToHome();
                            readyGo(MainActivity.class);
                        }
                    }, 3000);
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


}
