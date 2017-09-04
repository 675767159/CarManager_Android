package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;


/**
 * Created by qyh on 2016/11/30.
 */

public class TitleContentView extends RelativeLayout {



    public TitleContentView(Context context) {
        super(context);
        init(context,null);
    }

    public TitleContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public TitleContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    public void setContentTextViewText(String text){

        contentTextView.setText(text);
    }
    private TextView contentTextView;
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.TitleContentView);
        String  title=typedArray.getString(R.styleable.TitleContentView_TitleContent_title);
        String  content=typedArray.getString(R.styleable.TitleContentView_TitleContent_content);
        int titleColor=typedArray.getColor(R.styleable.TitleContentView_TitleContent_title_color, ContextCompat.getColor(context, R.color.whiteColor));
        int contentColor=typedArray.getColor(R.styleable.TitleContentView_TitleContent_title_color, ContextCompat.getColor(context, R.color.greenColor));




        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_title_content,null);

        TextView textView=(TextView)view.findViewById(R.id.title);
        textView.setText(title);
        textView.setTextColor(titleColor);

        contentTextView = (TextView) view.findViewById(R.id.content);
        contentTextView.setTextColor(contentColor);
        if (content!=null) {
            contentTextView.setText(content);

        }




        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(view,param);



    }


}
