package com.qcwp.carmanager.mvp.contact;



/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void registerSuccess();
    }

    interface Presenter  {
         void registerUser(String userName, String mobilePhone, String password1,String password2, String nickName,String verifCode);
         void getVerifyCode(String phone);

        void unregisterEventHandler();
    }
}
