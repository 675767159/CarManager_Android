package com.qcwp.carmanager.utils;

import android.graphics.Color;
import android.view.View;

import com.qcwp.carmanager.implement.StateRoundRectDrawable;


/**
 * Created by qyh on 2016/11/12.
 */

public class CommonUtils {
    public  static void setViewCorner(View view, float topRadius, float bottomRadius, int normalColor){
        StateRoundRectDrawable mRoundRectDradable = new StateRoundRectDrawable(normalColor, Color.GRAY);
        mRoundRectDradable.setBottomLeftRadius(bottomRadius);
        mRoundRectDradable.setBottomRightRadius(bottomRadius);
        mRoundRectDradable.setTopLeftRadius(topRadius);
        mRoundRectDradable.setTopRightRadius(topRadius);
        int alpha= Color.alpha(normalColor);
        mRoundRectDradable.setAlpha(alpha);//最大值FF(十六进制),255(十进制)
        view.setBackground(mRoundRectDradable);

    }

}
