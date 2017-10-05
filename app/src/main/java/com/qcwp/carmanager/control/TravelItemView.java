package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;


/**
 * Created by qyh on 2016/11/30.
 */

public class TravelItemView extends RelativeLayout {


    private TextView contentTextView;
    private TextView titleTextView;

    public TravelItemView(Context context) {
        super(context);
        init(context, null);
    }

    public TravelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public TravelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TravelItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setTitleTextViewText(String text) {

        titleTextView.setText(text);
    }

    public void setContentTextViewText(String text) {

        contentTextView.setText(text);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TravelItemView);
        String title = typedArray.getString(R.styleable.TravelItemView_TravelItemView_title);


        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_travel_item, null);

        titleTextView = (TextView) view.findViewById(R.id.title);
        titleTextView.setText(title);

        contentTextView = (TextView) view.findViewById(R.id.content);


        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(view, param);


    }


}
