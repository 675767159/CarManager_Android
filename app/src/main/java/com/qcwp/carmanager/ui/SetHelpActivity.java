package com.qcwp.carmanager.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.NavBarView;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.implement.GlideImageLoader;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.MySharedPreferences;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.qcwp.carmanager.ui.SetHelpActivity.SetHelpType.FIRST;

public class SetHelpActivity extends BaseActivity {


    @BindView(R.id.navBarView2)
    NavBarView navBarView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.skipButton)
    Button skipButton;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_set_help;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        final SetHelpType type = (SetHelpType) getIntent().getSerializableExtra(KeyEnum.typeKey);


        if (type == FIRST) {
            navBarView.setVisibility(View.GONE);
        }

        List<Integer> images = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            int image = CommonUtils.getImageByString(this, "set_help_" + (i + 1));
            images.add(image);
        }


        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        banner.setDelayTime(4000);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 6 && type == FIRST) {
                    skipButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.start();


    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skipButton:
                new MySharedPreferences(this).setBoolean(KeyEnum.isFirst, false);
                readyGoThenKill(MainActivity.class);
                break;
        }
    }

    enum SetHelpType {
        FIRST, NOFIRST
    }
}
