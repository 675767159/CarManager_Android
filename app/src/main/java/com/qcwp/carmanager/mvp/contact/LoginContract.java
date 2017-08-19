package com.qcwp.carmanager.mvp.contact;


import com.qcwp.carmanager.model.LoginModel;


/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();
    }

    interface Presenter  {
         void login(String loginName,String password);
    }
}
