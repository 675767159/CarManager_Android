package com.qcwp.carmanager.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.litesuits.android.log.Log;
import com.qcwp.carmanager.utils.Print;

/**
 * Created by qyh on 2017/9/2.
 *
 * @email:675767159@qq.com
 */

public class InstrumentView extends View {
    private String color_outcircle = "#DEDEDE";
    private String color_bg_outcircle = "#2690F8";
    private String color_bg_incircle = "#58ADE4";
    private String color_progress = "#87CEEB";
    private String color_smart_circle = "#C2B9B0";
    private String color_indicator_left = "#E1DCD6";
    private String color_indicator_right = "#F4EFE9";

    /**
     * 当前进度
     */
    private int progress = 0;
    /**
     * 要画的内容的实际宽度
     */
    private int contentWidth;
    /**
     * view的实际宽度
     */
    private int viewWidth;
    /**
     * view的实际高度
     */
    private int viewHeight;
    /**
     * 外环线的宽度
     */
    private int outCircleWidth = 1;
    /**
     * 外环的半径
     */
    private int outCircleRadius = 0;
    /**
     * 内环的半径
     */
    private int inCircleRedius = 0;
    /**
     * 内环与外环的距离
     */
    private int outAndInDistance = 0;
    /**
     * 内环的宽度
     */
    private int inCircleWidth = 0;
    /**
     * 刻度盘距离它外面的圆的距离
     */
    private int dialOutCircleDistance = 0;
    /**
     * 内容中心的坐标
     */
    private int[] centerPoint = new int[2];


    /**
     * 刻度线的数量
     */
    private int dialCount = 0;
//    /**


    /**
     * 每隔几次出现一个长线
     */
    private int dialPer = 0;


    /**
     * 长线的长度
     */
    private int dialLongLength = 0;
    /**
     * 短线的长度
     */
    private int dialShortLength = 0;
    /**
     * 刻度线距离圆心最远的距离
     */
    private int dialRadius = 0;


    /**
     * 圆弧开始的角度
     */
    private int startAngle = 0;
    /**
     * 圆弧划过的角度
     */
    private int allAngle = 0;

    private Paint mPaint;
    /**
     * 刻度盘上数字的数量
     */
    private int figureCount = 9;



    /**
     * 刻度盘数字大小
     */
    private int figureFontSize=35;

    /**
     * 刻度盘数字最大值
     */
    private int figureMaxNumber=8;



    /**
     * 显示单位
     */
    private String text="km/h";

    private int value=0;


    public InstrumentView(Context context) {
        this(context, null);
    }

    public InstrumentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InstrumentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();
    }

    /**
     * 初始化尺寸
     */
    private void initValues() {
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        contentWidth = viewWidth > viewHeight ? viewHeight : viewWidth;
        outCircleRadius = contentWidth / 2 - outCircleWidth;
        outAndInDistance = (int) (contentWidth / 26.5);
        inCircleWidth = (int) (contentWidth / 18.7);
        centerPoint[0] = viewWidth / 2;
        centerPoint[1] = viewHeight / 2;
        inCircleRedius = outCircleRadius - outAndInDistance - inCircleWidth / 2;
        startAngle = 150;
        allAngle = 240;
        dialOutCircleDistance = inCircleWidth;


        dialLongLength = (int) (dialOutCircleDistance / 1.2);
        dialShortLength = (int) (dialLongLength / 1.8);
        dialRadius = inCircleRedius - dialOutCircleDistance;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStatic(canvas);
        drawDynamic(canvas);

    }


    /**
     * 刷新视图
     */
    public void initView(int maxValue,int count,int per,String text){
        this.figureMaxNumber=maxValue;
        this.dialPer=per;
        this.text=text;
        this.figureCount=count;
        this.dialCount=per*(count-1);
        this.invalidate();
    }


    public void setFigureFextFontSize(int size){

        this.figureFontSize=size;

    }

    /**
     * 绘制静态的部分
     *
     * @param canvas
     */
    private void drawStatic(Canvas canvas) {
        drawOutCircle(canvas);
        drawCircleWithRound(startAngle, allAngle, inCircleWidth, inCircleRedius, color_outcircle, canvas);
        drawDial(startAngle, allAngle, dialCount, dialPer, dialLongLength, dialShortLength, dialRadius, canvas);
        drawBackGround(canvas);
        drawFigure(canvas, figureCount);
    }

    /**
     * 绘制刻度盘数字
     * @param canvas
     * @param count
     */
  private void drawFigure(Canvas canvas, int count) {
        int figure = 0;
        int angle;
        for (int i = 0; i < count; i++) {
            figure =  (int)(figureMaxNumber/(1f *figureCount-1) * i);
            angle = (int) ((allAngle) / ((count-1) * 1f) * i) + startAngle;
            int[] pointFromAngleAndRadius = getPointFromAngleAndRadius(angle, dialRadius - dialLongLength * 2 );
            mPaint.setTextSize(figureFontSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.save();
            canvas.rotate(angle+90,pointFromAngleAndRadius[0],pointFromAngleAndRadius[1]);
            canvas.drawText(String.valueOf(figure),pointFromAngleAndRadius[0],pointFromAngleAndRadius[1],mPaint);
            canvas.restore();
        }
    }

    /**
     * 画内层背景
     *
     * @param canvas
     */
    private void drawBackGround(Canvas canvas) {
        mPaint.setColor(Color.parseColor(color_bg_outcircle));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outCircleRadius / 3 / 2);
        canvas.drawCircle(centerPoint[0], centerPoint[1], outCircleRadius / 3, mPaint);
        mPaint.setColor(Color.parseColor(color_bg_incircle));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint[0], centerPoint[1], (outCircleRadius / 3f / 2), mPaint);
    }

    /**
     * 画刻度盘
     *
     * @param startAngle  开始画的角度
     * @param allAngle    总共划过的角度
     * @param dialCount   总共的线的数量
     * @param per         每隔几个出现一次长线
     * @param longLength  长仙女的长度
     * @param shortLength 短线的长度
     * @param radius      距离圆心最远的地方的半径
     */
    private void drawDial(int startAngle, int allAngle, int dialCount, int per, int longLength, int shortLength, int radius, Canvas canvas) {
        int length;
        int angle;
        if (per==0){
             per=1;
        }
        for (int i = 0; i <= dialCount; i++) {
            angle = (int) ((allAngle) / (dialCount * 1f) * i) + startAngle;

            if (i % per == 0) {
                length = longLength;
            } else {
                length = shortLength;
            }
            drawSingleDial(angle, length, radius, canvas);
        }
    }

    /**
     * 画刻度中的一条线
     *
     * @param angle  所处的角度
     * @param length 线的长度
     * @param radius 距离圆心最远的地方的半径
     */
    private void drawSingleDial(int angle, int length, int radius, Canvas canvas) {
        int[] startP = getPointFromAngleAndRadius(angle, radius);
        int[] endP = getPointFromAngleAndRadius(angle, radius - length);
        canvas.drawLine(startP[0], startP[1], endP[0], endP[1], mPaint);
    }

    /**
     * 画最外层的圆
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        mPaint.setStrokeWidth(outCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(color_outcircle));
        canvas.drawCircle(centerPoint[0], centerPoint[1], outCircleRadius, mPaint);
    }

    /**
     * 绘制动态的部分
     *
     * @param canvas
     */
    private void drawDynamic(Canvas canvas) {

        if (progress>0) {
            drawProgress(progress, canvas);
        }
        drawIndicator(progress, canvas);
        drawCurrentProgressTv(progress, canvas);
    }

    /**
     * 绘制当前进度是文字
     *
     * @param progress
     * @param canvas
     */
    private void drawCurrentProgressTv(int progress, Canvas canvas) {
//        canvas.drawText("当前进度："+progress+"%",);
        mPaint.setTextSize(45);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLine1 = centerPoint[1] + (outCircleRadius / 20f * 9 - fontMetrics.top - fontMetrics.bottom);
        canvas.drawText(text, centerPoint[0], baseLine1, mPaint);
//drawText的第二个参数的值=要让文字的中心放在哪-（fontMetrics.top+fontMetrics.bottom）/2
//此时求出来的baseline可以使文字竖直居中

        float baseLine2 = outCircleRadius / 20f * 9 - 3 * (fontMetrics.bottom + fontMetrics.top) + centerPoint[1];
        canvas.drawText(String.valueOf(value), centerPoint[0], baseLine2, mPaint);


    }

    /**
     * 画指针以及他的背景
     *
     * @param progress
     * @param canvas
     */
    private void drawIndicator(int progress, Canvas canvas) {
        drawPointer(canvas);
        drawIndicatorBg(canvas);
    }

    /**
     * 指针的最远处的半径和刻度线的一样
     */
    private void drawPointer(Canvas canvas) {
        RectF rectF = new RectF(centerPoint[0] - (int) (outCircleRadius / 3f / 2 / 2),
                centerPoint[1] - (int) (outCircleRadius / 3f / 2 / 2), centerPoint[0] + (int) (outCircleRadius / 3f / 2 / 2), centerPoint[1] + (int) (outCircleRadius / 3f / 2 / 2));
        int angle = (int) ((allAngle) / (100 * 1f) * progress) + startAngle;
        //指针的定点坐标
        int[] peakPoint = getPointFromAngleAndRadius(angle, dialRadius);
        //顶点朝上，左侧的底部点的坐标
        int[] bottomLeft = getPointFromAngleAndRadius(angle - 90, (int) (outCircleRadius / 3f / 2 / 2));
        //顶点朝上，右侧的底部点的坐标
        int[] bottomRight = getPointFromAngleAndRadius(angle + 90, (int) (outCircleRadius / 3f / 2 / 2));
        Path path = new Path();
        mPaint.setColor(Color.parseColor(color_indicator_left));
        path.moveTo(centerPoint[0], centerPoint[1]);
        path.lineTo(peakPoint[0], peakPoint[1]);
        path.lineTo(bottomLeft[0], bottomLeft[1]);
        path.close();
        canvas.drawPath(path, mPaint);
        canvas.drawArc(rectF, angle - 180, 100, true, mPaint);



        mPaint.setColor(Color.parseColor(color_indicator_right));
        path.reset();
        path.moveTo(centerPoint[0], centerPoint[1]);
        path.lineTo(peakPoint[0], peakPoint[1]);
        path.lineTo(bottomRight[0], bottomRight[1]);
        path.close();
        canvas.drawPath(path, mPaint);

        canvas.drawArc(rectF, angle + 80, 100, true, mPaint);


    }

    private void drawIndicatorBg(Canvas canvas) {
        mPaint.setColor(Color.parseColor(color_smart_circle));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint[0], centerPoint[1], (outCircleRadius / 3f / 2 / 4), mPaint);
    }

    /**
     * 根据进度画进度条
     *
     * @param progress 最大进度为100.最小为0
     */
    private void drawProgress(int progress, Canvas canvas) {
        float ratio = progress / 100f;
        int angle = (int) (allAngle * ratio);
        drawCircleWithRound(startAngle, angle, inCircleWidth, inCircleRedius, color_progress, canvas);

    }

    public int getProgress() {
        return progress;
    }

    public void setValue(int value) {
        this.value=value;
        this.progress=(int)(value*100.0/figureMaxNumber);
//       this.progress = progress;
        invalidate();
    }


    public void setValue2(int value) {
        this.value=value;
        this.progress=(int)(value*100.0/1000/figureMaxNumber);
//       this.progress = progress;
        invalidate();
    }
    /**
     * 画一个两端为圆弧的圆形曲线
     *
     * @param startAngle 曲线开始的角度
     * @param allAngle   曲线走过的角度
     * @param radius     曲线的半径
     * @param width      曲线的厚度
     */
    private void drawCircleWithRound(int startAngle, int allAngle, int width, int radius, String color, Canvas canvas) {
        mPaint.setStrokeWidth(width);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(color));
        RectF rectF = new RectF(centerPoint[0] - radius, centerPoint[1] - radius, centerPoint[0] + radius, centerPoint[1] + radius);
        canvas.drawArc(rectF, startAngle, allAngle, false, mPaint);
        drawArcRoune(radius, startAngle, width, canvas);
        drawArcRoune(radius, startAngle + allAngle, width, canvas);
    }

    /**
     * 绘制圆弧两端的圆
     *
     * @param radius 圆弧的半径
     * @param angle  所处于圆弧的多少度的位置
     * @param width  圆弧的宽度
     */
    private void drawArcRoune(int radius, int angle, int width, Canvas canvas) {
        int[] point = getPointFromAngleAndRadius(angle, radius);
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(point[0], point[1], width / 2, mPaint);
    }

    /**
     * 根据角度和半径，求一个点的坐标
     *
     * @param angle
     * @param radius
     * @return
     */
    private int[] getPointFromAngleAndRadius(int angle, int radius) {
        double x = radius * Math.cos(angle * Math.PI / 180) + centerPoint[0];
        double y = radius * Math.sin(angle * Math.PI / 180) + centerPoint[1];
        return new int[]{(int) x, (int) y};
    }

}
