package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;


/**
 * Created by qyh on 2016/11/30.
 */

public class NoiseTestItemView extends RelativeLayout {

   private EditText editText;

    public String getEditTextToString() {
        return editText.getText().toString();
    }

    public NoiseTestItemView(Context context) {
        super(context);
    }

    public NoiseTestItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public NoiseTestItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NoiseTestItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private TextView valueTextView;
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.NoiseTestItemView);
        String  title=typedArray.getString(R.styleable.NoiseTestItemView_NoiseTestItem_title);
        String  unit=typedArray.getString(R.styleable.NoiseTestItemView_unit);
        int valueColor=typedArray.getColor(R.styleable.NoiseTestItemView_valueColor,0);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_noise_test_item,null);

        TextView textView=(TextView)view.findViewById(R.id.title);
        textView.setText(title);


        valueTextView=(TextView) view.findViewById(R.id.value);
        valueTextView.setTextColor(valueColor);

        TextView unitTextView=(TextView) view.findViewById(R.id.unit);
        unitTextView.setText(unit);



        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view,param);


    }

    public void setValue(String text){
        valueTextView.setText(text);
    }

}
