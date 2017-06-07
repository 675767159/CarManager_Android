package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;


/**
 * Created by qyh on 2016/11/30.
 */

public class MenuItemView extends RelativeLayout {


    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.MenuItemView);
        String  text=typedArray.getString(R.styleable.MenuItemView_text);
        int image=typedArray.getResourceId(R.styleable.MenuItemView_HomeItemView_image,0);



        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_menu_list_item,null);

        TextView textView=(TextView)view.findViewById(R.id.textView);
        textView.setText(text);


        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(image);


        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view,param);




    }


}
