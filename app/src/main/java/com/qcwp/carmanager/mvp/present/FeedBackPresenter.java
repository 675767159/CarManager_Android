package com.qcwp.carmanager.mvp.present;

import android.util.Log;

import com.blankj.utilcode.util.EmptyUtils;
import com.qcwp.carmanager.model.retrofitModel.AllCarModel;
import com.qcwp.carmanager.mvp.contact.FeedBackContract;
import com.qcwp.carmanager.utils.Print;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public class FeedBackPresenter extends BasePresenter implements FeedBackContract.Presenter {

    private final FeedBackContract.View view;

    public FeedBackPresenter(FeedBackContract.View view){
        this.view=view;
    }

    @Override
    public void addFeedback(String content, String email) {


        // 开始验证输入内容
        if (EmptyUtils.isEmpty(content)) {
            view.showTip("反馈内容不能为空");
            return;
        }
        if (EmptyUtils.isEmpty(email)) {
            view.showTip("请输入邮箱地址,方便我们对您的意见进行及时回复");
            return;
        }

        view.showProgress("");

        // 使用自定义对象存至云平台,作为简易版的反馈意见收集
        mEngine.getAllCarBrand(1).enqueue(new Callback<AllCarModel>() {
            @Override
            public void onResponse(Call<AllCarModel> call, Response<AllCarModel> response) {
                if (!view.isActive()) {
                    return;
                }

                view.dismissProgress();
                view.addFeedbackSuccess();
            }

            @Override
            public void onFailure(Call<AllCarModel> call, Throwable throwable) {
                if (!view.isActive()) {
                    return;
                }

                view.dismissProgress();
                view.showTip("反馈提交失败");
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
