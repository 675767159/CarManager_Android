package com.qcwp.carmanager.mvp.present;

import com.qcwp.carmanager.mvp.contact.FeedBackContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by qyh on 2017/7/27.
 *
 * @email:675767159@qq.com
 */
public class FeedBackPresenterTest {
    // 用于测试真实接口返回数据
    private FeedBackPresenter presenter;

    // 用于测试模拟接口返回数据
    private FeedBackPresenter mockPresenter;

    @Mock
    private FeedBackContract.View view;


    @Test
    public void addFeedback() throws Exception {




//        // 模拟数据，当api调用addFeedBack接口传入任意值时，就抛出错误error
//        when(api.addFeedBack(any(FeedBack.class)))
//                .thenReturn(Observable.<BaseEntity>error(new Exception("孙贼你说谁辣鸡呢？")));

        String content = "这个App真是辣鸡！";
        String email = "120@qq.com";
        mockPresenter.addFeedback(content, email);

        verify(view).showProgress("");
        verify(view).dismissProgress();
        verify(view).showTip("反馈提交失败");

    }



    @Before
    public void setUp() throws Exception {
        // 使用Mock标签等需要先init初始化一下
        MockitoAnnotations.initMocks(this);

        // 当view调用isActive方法时，就返回true表示UI已激活。方便测试接口返回数据后测试view的方法
        when(view.isActive()).thenReturn(true);

        // 设置单元测试标识
//        BoreConstants.isUnitTest = true;

        // 用真实接口创建反馈
        presenter = new FeedBackPresenter(view);
        // 用mock模拟接口创建反馈
        mockPresenter = new FeedBackPresenter(view);

    }
}