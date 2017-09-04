package com.qcwp.carmanager.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by qyh on 2017/1/11.
 */

public class TouchHighlightImageView extends ImageView {
    /**
     * 焦点与点击状态时View的覆盖层Drawable，一般设置半透明的Drawable
     */
//    如果想用系统默认的点击效果可以改成Android.R.attr.selectableItemBackground。
    private Drawable mForegroundDrawable;

    /**
     * View的大小范围记录
     */
    private Rect mCachedBounds = new Rect();

    public TouchHighlightImageView(Context context) {
        super(context);
        init();
    }

    public TouchHighlightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchHighlightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 重置默认的背景样式和内边距
        setBackgroundColor(0);
        setPadding(0, 0, 0, 0);

        // 设置点击时显示的Drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(0x99000000));
        mForegroundDrawable = stateListDrawable;
        mForegroundDrawable.setCallback(this);

    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        // 更新Drawable状态与View状态匹配
        if (mForegroundDrawable.isStateful()) {
            mForegroundDrawable.setState(getDrawableState());
        }

        // 重绘
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画Drawable
        mForegroundDrawable.setBounds(mCachedBounds);
        mForegroundDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 保存View大小范围
        mCachedBounds.set(0, 0, w, h);
    }
}
