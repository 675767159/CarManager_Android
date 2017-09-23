package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by DL on 16/9/26.
 */
public class TestRecordAdapter extends BaseAdapter {



    private List<TestSummaryModel> mList;
    private Context mContext;
    private ProfessionalTestEnum type;
    private Locale locale;
    public TestRecordAdapter(Context context, List<TestSummaryModel> list, ProfessionalTestEnum type){
        mContext=context;
        locale=Locale.getDefault();
        this.type=type;
        this.mList=list;
    }

    @Override
    public int getCount() {

        return mList==null?0:mList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public TestSummaryModel getItem(int i) {

        return mList.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_test_record_cell, null);
            viewHolder=new ViewHolder();
            viewHolder.createDate=(TitleContentView) convertView.findViewById(R.id.createDate);
            viewHolder.value3=(TitleContentView) convertView.findViewById(R.id.value3);
            viewHolder.value4=(TitleContentView) convertView.findViewById(R.id.value4);
            viewHolder.carNumber=(TitleContentView) convertView.findViewById(R.id.carNumber);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        TestSummaryModel testSummaryModel=this.getItem(position);
        viewHolder.createDate.setContentTextViewText(testSummaryModel.getCreateDate());
        viewHolder.carNumber.setContentTextViewText(testSummaryModel.getCarNumber());

        switch (type){
            case HectometerAccelerate:
                viewHolder.value3.setTitleTextViewText("加速时间：");
                viewHolder.value3.setContentTextViewText(String.format(locale,"%.1f s",testSummaryModel.getTestTime()));

                viewHolder.value4.setTitleTextViewText("最高时速：");
                viewHolder.value4.setContentTextViewText(String.format(locale,"%.1f km/h",testSummaryModel.getMaxSpeed()));
                break;
            case KilometersAccelerate:

                viewHolder.value3.setTitleTextViewText("加速时间：");
                viewHolder.value3.setContentTextViewText(String.format(locale,"%.1f s",testSummaryModel.getTestTime()));

                viewHolder.value4.setTitleTextViewText("行车距离：");
                viewHolder.value4.setContentTextViewText(String.format(locale,"%.1f m",testSummaryModel.getTestDist()));
                break;
            case KilometersBrake:
                viewHolder.value3.setTitleTextViewText("减速时间：");
                viewHolder.value3.setContentTextViewText(String.format(locale,"%.1f s",testSummaryModel.getTestTime()));

                viewHolder.value4.setTitleTextViewText("行车距离：");
                viewHolder.value4.setContentTextViewText(String.format(locale,"%.1f m",testSummaryModel.getTestDist()));
                break;
        }


        return convertView;
    }





    private class ViewHolder{
        public TitleContentView createDate;
        public TitleContentView carNumber;
        public TitleContentView value3;
        public TitleContentView value4;

    }

}
