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

public class EditCarInputView extends RelativeLayout {

   private EditText editText;
    private TextView selectText;
    private Boolean isButton;

    public EditCarInputView(Context context) {
        super(context);
    }

    public String getText() {
        if (isButton){
            return selectText.getText().toString();
        }else {
            return editText.getText().toString();
        }

    }
    public void setText(String text) {
        if (isButton){
            selectText.setText(text);

        }else {
            editText.setText(text);
        }
    }
    public EditCarInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public EditCarInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditCarInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.EditCarInputView);
        String  title=typedArray.getString(R.styleable.EditCarInputView_EditCar_title);
        String  description=typedArray.getString(R.styleable.EditCarInputView_EditCar_title_description);
        String  hint=typedArray.getString(R.styleable.EditCarInputView_EditCar_hint);
        Boolean canEdit=typedArray.getBoolean(R.styleable.EditCarInputView_EditCar_canEdit,true);
        isButton=typedArray.getBoolean(R.styleable.EditCarInputView_EditCar_isButton,false);

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_edit_car_input,null);

        TextView textView=(TextView)view.findViewById(R.id.title);
        textView.setText(title);


        if (description!=null) {
            TextView title_description = (TextView) view.findViewById(R.id.title_description);
            title_description.setText("(" + description + ")");
        }

        editText = (EditText) view.findViewById(R.id.editText);

        if (isButton){
            selectText=(TextView)view.findViewById(R.id.selectText);
            selectText.setText(hint);
            selectText.setVisibility(VISIBLE);
            editText.setVisibility(GONE);
        }else {

            editText.setHint(hint);
            editText.setEnabled(canEdit);

        }




        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view,param);



    }


}
