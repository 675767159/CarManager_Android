package com.qcwp.carmanager.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;

import java.util.Locale;


/**
 * Created by qyh on 2016/11/30.
 */

public class ProfessionalTestDisplayView extends RelativeLayout {


    private TextView bestTextView;
    private TextView officialTextView;
    private TextView myCarTextView;

    public ProfessionalTestDisplayView(Context context) {
        super(context);
    }

    public ProfessionalTestDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public ProfessionalTestDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProfessionalTestDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.ProfessionalTestDisplayView);
        String  itemName=typedArray.getString(R.styleable.ProfessionalTestDisplayView_ProfessionalTest_itemName);

        int backgroundColor=typedArray.getColor(R.styleable.ProfessionalTestDisplayView_ProfessionalTest_itemBackgrondcolor, ContextCompat.getColor(context, R.color.whiteColor));

        LayoutInflater layoutInflater=(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_professional_test_display,null);

        TextView itemNameTextView=(TextView) view.findViewById(R.id.testItemName);
        itemNameTextView.setText(itemName);

        RelativeLayout titleBackground=(RelativeLayout)view.findViewById(R.id.titleBackground);
        titleBackground.setBackgroundColor(backgroundColor);


        bestTextView=(TextView)view.findViewById(R.id.bestRecord);
        officialTextView=(TextView)view.findViewById(R.id.officalRecord);
        myCarTextView=(TextView)view.findViewById(R.id.myCarRecord);


        view.findViewById(R.id.testHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null)
                onClickListener.onClickHelp(ProfessionalTestDisplayView.this);
            }
        });

        view.findViewById(R.id.testRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null)
                onClickListener.onClickRecord(ProfessionalTestDisplayView.this);
            }
        });


        view.findViewById(R.id.challenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null)
                onClickListener.onClickChallenge(ProfessionalTestDisplayView.this);
            }
        });



        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view,param);


    }

    public void setBestRecord(float record){
        bestTextView.setText(String.valueOf(record));
    }

    public void setOfficalRecord(float record){
        officialTextView.setText(String.valueOf(record));
    }


    public void setMyCarRecord(String record){
        myCarTextView.setText(record);
    }


    public interface OnDisplayViewClickListener{
        void onClickHelp(ProfessionalTestDisplayView v);
        void onClickChallenge(ProfessionalTestDisplayView v);
        void onClickRecord(ProfessionalTestDisplayView v);
    }

    private OnDisplayViewClickListener onClickListener;
    public void setOnDisplayViewClickListener(OnDisplayViewClickListener onClickListener){
        this.onClickListener=onClickListener;
    }


}
