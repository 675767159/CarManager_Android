package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.utils.Print;


/**
 * Created by Kofi Gyan on 11/27/2015.
 */


public class Thermometer extends View  {


    public void initTemperatureRange(int minimum,int maximum){
        TEMPERATURE_OFFSET=minimum;
        DEFAULT_UPPERMOST_TEMPERATURE_READING=maximum;


    }
    public void setTemperatureC(int temperatureC){
        this.mTemperatureC=temperatureC;
        this.invalidate();
    }
    private Canvas canvas;
    //thermometer circles paints
    private Paint mInnerCirclePaint;
    private Paint mOuterCirclePaint;
    private Paint mFirstOuterCirclePaint;

    //thermometer arc paint
    private Paint mFirstOuterArcPaint;


    //thermometer lines paints
    private Paint mInnerLinePaint;
    private Paint mOuterLinePaint;
    private Paint mFirstOuterLinePaint;


    //thermometer radii
    private int mOuterRadius;
    private int mInnerRadius;
    private int mFirstOuterRadius;

    //thermometer colors
    private int mThermometerColor = Color.rgb(200, 115, 205);

    //thermometer circles and lines variables
    private float mLastCellWidth;
    private int mStageHeight;
    private float mCellWidth;
    private float mStartCenterY; //center of first cell
    private float mEndCenterY; //center of last cell
    private float mStageCenterX;
    private float mXOffset;
    private float mYOffset;

    // I   1st Cell     I  2nd Cell       I  3rd Cell  I
    private static final int NUMBER_OF_CELLS = 3; //three cells in all  ie.stageHeight divided into 3 equal cells

    //thermometer animation variables
    private float mIncrementalTempValue;
    private boolean mIsAnimating;

    private Handler handler;


    //temperature measured
    private float mTemperatureC;
    //temperature range -30 <= temperature <= 50
    private static  float DEFAULT_UPPERMOST_TEMPERATURE_READING =215;
    private static  float TEMPERATURE_OFFSET = 40; //takes care of distance btn -30 degree celcius and 0 degree celcius

    private float mMaxDistance; //distance to measure to => difference between startingDistanceReading and currentDistanceReading


    public Thermometer(Context context) {
        this(context, null);
    }

    public Thermometer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Thermometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (attrs != null) {

            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Thermometer, defStyle, 0);

            mThermometerColor = a.getColor(R.styleable.Thermometer_therm_color, mThermometerColor);

            a.recycle();
        }

        init();
    }

    private void init() {

        handler = new Handler();

        mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint.setColor(mThermometerColor);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);
        mInnerCirclePaint.setStrokeWidth(17f);


        mOuterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterCirclePaint.setColor(Color.WHITE);
        mOuterCirclePaint.setStyle(Paint.Style.FILL);
        mOuterCirclePaint.setStrokeWidth(32f);


        mFirstOuterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstOuterCirclePaint.setColor(mThermometerColor);
        mFirstOuterCirclePaint.setStyle(Paint.Style.FILL);
        mFirstOuterCirclePaint.setStrokeWidth(60f);


        mFirstOuterArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstOuterArcPaint.setColor(mThermometerColor);
        mFirstOuterArcPaint.setStyle(Paint.Style.STROKE);
        mFirstOuterArcPaint.setStrokeWidth(30f);


        mInnerLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerLinePaint.setColor(mThermometerColor);
        mInnerLinePaint.setStyle(Paint.Style.FILL);
        mInnerLinePaint.setStrokeWidth(17f);


        mOuterLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterLinePaint.setColor(Color.WHITE);
        mOuterLinePaint.setStyle(Paint.Style.FILL);


        mFirstOuterLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstOuterLinePaint.setColor(mThermometerColor);
        mFirstOuterLinePaint.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStageCenterX = getWidth() / 2;

        mStageHeight = getHeight();

        mCellWidth = mStageHeight / NUMBER_OF_CELLS;

        //center of first cell
        mStartCenterY = mCellWidth / 2;


        //move to 3rd cell
        mLastCellWidth = (NUMBER_OF_CELLS * mCellWidth);

        //center of last(3rd) cell
        mEndCenterY = mLastCellWidth - (mCellWidth / 2);


        // mOuterRadius is 1/4 of mCellWidth
        mOuterRadius = (int) (0.25 * mCellWidth);

        mInnerRadius = (int) (0.656 * mOuterRadius);

        mFirstOuterRadius = (int) (1.344 * mOuterRadius);

        mFirstOuterLinePaint.setStrokeWidth(mFirstOuterRadius);

        mOuterLinePaint.setStrokeWidth(mFirstOuterRadius / 2);

        mFirstOuterArcPaint.setStrokeWidth(mFirstOuterRadius / 4);

        mXOffset = mFirstOuterRadius / 4;
        mXOffset = mXOffset / 2;

        //get the difference btn firstOuterLine and innerAnimatedline
        mYOffset = (mStartCenterY + (float) 0.875 * mOuterRadius) - (mStartCenterY + mInnerRadius);
        mYOffset = mYOffset / 2;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas=canvas;
        drawFirstOuterCircle(canvas);

        drawOuterCircle(canvas);

        drawInnerCircle(canvas);

        drawFirstOuterLine(canvas);

        drawOuterLine(canvas);

        animateInnerLine(canvas);

        drawFirstOuterCornerArc(canvas);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //takes care of paddingTop and paddingBottom
        int paddingY = getPaddingBottom() + getPaddingTop();

        //get height and width
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        height += paddingY;

        setMeasuredDimension(width, height);
    }


    private void drawInnerCircle(Canvas canvas) {
        drawCircle(canvas, mInnerRadius, mInnerCirclePaint);
    }

    private void drawOuterCircle(Canvas canvas) {
        drawCircle(canvas, mOuterRadius, mOuterCirclePaint);
    }


    private void drawFirstOuterCircle(Canvas canvas) {
        drawCircle(canvas, mFirstOuterRadius, mFirstOuterCirclePaint);
    }


    private void drawCircle(Canvas canvas, float radius, Paint paint) {
        canvas.drawCircle(mStageCenterX, mEndCenterY, radius, paint);
    }


    private void drawOuterLine(Canvas canvas) {

        float startY = mEndCenterY - (float) (0.875 * mOuterRadius);
        float stopY = mStartCenterY + (float) (0.875 * mOuterRadius);

        drawLine(canvas, startY, stopY, mOuterLinePaint);

    }


    private void drawFirstOuterLine(Canvas canvas) {

        float startY = mEndCenterY - (float) (0.875 * mFirstOuterRadius);
        float stopY = mStartCenterY + (float) (0.875 * mOuterRadius);

        drawLine(canvas, startY, stopY, mFirstOuterLinePaint);
    }


    private void drawLine(Canvas canvas, float startY, float stopY, Paint paint) {
        canvas.drawLine(mStageCenterX, startY, mStageCenterX, stopY, paint);
    }


    private void drawFirstOuterCornerArc(Canvas canvas) {

        float y = mStartCenterY - (float) (0.875 * mFirstOuterRadius);

        RectF rectF = new RectF(mStageCenterX - mFirstOuterRadius / 2 + mXOffset, y + mFirstOuterRadius, mStageCenterX + mFirstOuterRadius / 2 - mXOffset, y + (2 * mFirstOuterRadius) + mYOffset);

        canvas.drawArc(rectF, -180, 180, false, mFirstOuterArcPaint);

    }


    private void animateInnerLine(Canvas canvas) {


//
//        if (!mIsAnimating) {
//
//            //this local variables help to get the distance to measure to
            float uppermostDistanceReading;
            float currentDistanceReading; //  distance equivalence of temperature reading
            float startingDistanceReading; //base distance or distance at which reading starts from ie.reading distance does not start from 0
            float startingUppermostDiff; //difference between startingDistanceReading and uppermostDistanceReading

            float maxTemperature; //maximum temperature NB. makes provision for TEMPERATURE_OFFSET
            float currentTemperatureReading; // current temperature reading NB. makes provision for TEMPERATURE_OFFSET


            currentTemperatureReading = mTemperatureC + TEMPERATURE_OFFSET;
            maxTemperature = DEFAULT_UPPERMOST_TEMPERATURE_READING + TEMPERATURE_OFFSET;

            uppermostDistanceReading = mStartCenterY + mInnerRadius;
            startingDistanceReading = mEndCenterY + (float) (0.875 * mInnerRadius);

            startingUppermostDiff = startingDistanceReading - uppermostDistanceReading;

            //convert temperature reading to its distance equivalence
            currentDistanceReading = (currentTemperatureReading / maxTemperature) * (startingUppermostDiff);

            mMaxDistance = startingDistanceReading - currentDistanceReading;

//            mIncrementalTempValue = mEndCenterY + (float) (0.875 * mInnerRadius);
//
//            mIsAnimating = true;
//
//
//        } else {
//
//
//
//        }

        mIncrementalTempValue = mEndCenterY + (float) (0.875 * mInnerRadius);
        if (mIncrementalTempValue < mMaxDistance) {

            float startY = mEndCenterY + (float) (0.875 * mInnerRadius);
            drawLine(canvas, startY, mIncrementalTempValue, mInnerCirclePaint);

        } else {

            float startY = mEndCenterY + (float) (0.875 * mInnerRadius);
            drawLine(canvas, startY, mMaxDistance, mInnerCirclePaint);

            mIsAnimating = false;
        }

    }


    public void setThermometerColor(int thermometerColor) {
        this.mThermometerColor = thermometerColor;

        mInnerCirclePaint.setColor(mThermometerColor);

        mFirstOuterCirclePaint.setColor(mThermometerColor);

        mFirstOuterArcPaint.setColor(mThermometerColor);

        mInnerLinePaint.setColor(mThermometerColor);

        mFirstOuterLinePaint.setColor(mThermometerColor);

        invalidate();
    }














    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {




        super.onDetachedFromWindow();

    }




}
