package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.utils.MyActivityManager;
import com.qcwp.carmanager.utils.Print;


/**
 * Created by qyh on 2016/11/30.
 */

public class RegisterInputView extends RelativeLayout {

   private EditText editText;

    public String getEditTextToString() {
        return editText.getText().toString();
    }

    public RegisterInputView(Context context) {
        super(context);
    }

    public RegisterInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public RegisterInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegisterInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.RegisterInputView);
        String  title=typedArray.getString(R.styleable.RegisterInputView_name);
        String  description=typedArray.getString(R.styleable.RegisterInputView_description);
        Boolean hiddenBackButton=typedArray.getBoolean(R.styleable.RegisterInputView_hiddenLine,false);
        Boolean isPassword=typedArray.getBoolean(R.styleable.RegisterInputView_isPassword,false);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_register_input,null);

        TextView textView=(TextView)view.findViewById(R.id.title);
        textView.setText(title);


        editText=(EditText) view.findViewById(R.id.description);
        editText.setHint(description);



        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view,param);

        LinearLayout  underLine=(LinearLayout)view.findViewById(R.id.line);
        if (!hiddenBackButton) {
            underLine.setVisibility(VISIBLE);
        }else {
            underLine.setVisibility(GONE);
        }

        if (isPassword){
            editText.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }


}
