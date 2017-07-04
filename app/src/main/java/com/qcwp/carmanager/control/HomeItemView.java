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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;

import org.w3c.dom.Text;


/**
 * Created by qyh on 2016/11/30.
 */

public class HomeItemView extends RelativeLayout {

   private TextView value1;
   private TextView value2;
   private TextView value3;


    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
   private Context context;
    public void setTitle1(String title) {
        this.textView1.setText(title);
    }

    public void setTitle2(String title) {
        this.textView2.setText(title);
    }

    public void setTitle3(String title) {
        this.textView3.setText(title);
    }

    public String getValue1() {
        return value1.getText().toString();
    }

    public String getValue2() {
        return value2.getText().toString();
    }

    public String getValue3() {
        return value3.getText().toString();
    }


    public void setValue1(String value1) {
        this.value1.setText(value1);
    }

    public void setValue2(String value2) {
        this.value2.setText(value2);
    }

    public void setValue3(String value3) {
        this.value3.setText(value3);
    }

    public void setValue3Color(int colorId) {
        int color=ContextCompat.getColor(context, colorId);
        this.value3.setTextColor(color);
    }


    public HomeItemView(Context context) {
        super(context);
    }

    public HomeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public HomeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HomeItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HomeItemView);
        String  title1=typedArray.getString(R.styleable.HomeItemView_title1);
        String  title2=typedArray.getString(R.styleable.HomeItemView_title2);
        String  title3=typedArray.getString(R.styleable.HomeItemView_title3);
        int image=typedArray.getResourceId(R.styleable.HomeItemView_image,0);


        Boolean hiddenTitle2=typedArray.getBoolean(R.styleable.HomeItemView_hiddenTitle2,false);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_home_list_item,null);

         textView1=(TextView)view.findViewById(R.id.title1);
        textView1.setText(title1);

         textView2=(TextView)view.findViewById(R.id.title2);
        textView2.setText(title2);

         textView3=(TextView)view.findViewById(R.id.title3);
        textView3.setText(title3);

        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(image);

        value1=(TextView)view.findViewById(R.id.value1);
        value2=(TextView)view.findViewById(R.id.value2);
        value3=(TextView)view.findViewById(R.id.value3);



        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view,param);

        LinearLayout  underLine=(LinearLayout)view.findViewById(R.id.line);
        if (!hiddenTitle2) {
            textView2.setVisibility(VISIBLE);
            value2.setVisibility(VISIBLE);
        }else {
            textView2.setVisibility(GONE);
            value2.setVisibility(GONE);
        }



    }


}
