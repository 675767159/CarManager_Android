package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.enumeration.ProfessionalTestEnum;
import com.qcwp.carmanager.model.sqLiteModel.NoiseTestModel;
import com.qcwp.carmanager.model.sqLiteModel.TestSummaryModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by DL on 16/9/26.
 */
public class NoiseRecordAdapter extends BaseAdapter {



    private List<NoiseTestModel> mList;
    private Context mContext;
    private Locale locale;
    public NoiseRecordAdapter(Context context, List<NoiseTestModel> list){
        mContext=context;
        locale=Locale.getDefault();
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
    public NoiseTestModel getItem(int i) {

        return mList.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_noise_record_cell, null);
            viewHolder=new ViewHolder();
            viewHolder.createDate=(TitleContentView) convertView.findViewById(R.id.createDate);
            viewHolder.averageNoise=(TitleContentView) convertView.findViewById(R.id.averageNoise);
            viewHolder.averageSpeed=(TitleContentView) convertView.findViewById(R.id.averageSpeed);
            viewHolder.carNumber=(TitleContentView) convertView.findViewById(R.id.carNumber);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        NoiseTestModel noiseTestModel=this.getItem(position);
        viewHolder.createDate.setContentTextViewText(noiseTestModel.getCreateDate());
        viewHolder.carNumber.setContentTextViewText(noiseTestModel.getCarNumber());
        viewHolder.averageNoise.setContentTextViewText(String.format(locale,"%.1f dB",noiseTestModel.getAvgNoise()));
        viewHolder.averageSpeed.setContentTextViewText(String.format(locale,"%.1f km/h",noiseTestModel.getAvgSpeed()));


        return convertView;
    }





    private class ViewHolder{
        public TitleContentView createDate;
        public TitleContentView carNumber;
        public TitleContentView averageNoise;
        public TitleContentView averageSpeed;

    }

}
