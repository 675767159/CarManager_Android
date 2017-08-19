package com.qcwp.carmanager.mvp.contact;


import com.qcwp.carmanager.mvp.present.BasePresenter;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public interface FeedBackContract {
    interface View extends BaseView<Presenter> {
        void addFeedbackSuccess();
    }

    interface Presenter  {
        void addFeedback(String content, String email);
    }
}
