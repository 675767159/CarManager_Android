package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.ui.CarEditActivity;

import java.util.List;
import java.util.Locale;

/**
 * Created by DL on 16/9/26.
 */
public class TrackRecordAdapter extends BaseAdapter {



    private List<TravelSummaryModel> mList;
    private Context mContext;
    public TrackRecordAdapter(Context context, List<TravelSummaryModel> list){
         mContext=context;
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
    public TravelSummaryModel getItem(int i) {

        return mList.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_track_record_cell, null);
            viewHolder=new ViewHolder();
            viewHolder.createDate=(TitleContentView) convertView.findViewById(R.id.createDate);
            viewHolder.timeSpan=(TitleContentView) convertView.findViewById(R.id.timeSpan);
            viewHolder.distance=(TitleContentView) convertView.findViewById(R.id.distance);
            viewHolder.carNumber=(TitleContentView) convertView.findViewById(R.id.carNumber);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        TravelSummaryModel travelSummaryModel=this.getItem(position);
        viewHolder.createDate.setContentTextViewText(travelSummaryModel.getStartDate().substring(0,10));
        viewHolder.timeSpan.setContentTextViewText(travelSummaryModel.getStartDate().substring(11,16)+"~"+travelSummaryModel.getEndDate().substring(10,16));
        viewHolder.distance.setContentTextViewText(String.format(Locale.getDefault(),"%.1f km",travelSummaryModel.getMileage()));
        viewHolder.carNumber.setContentTextViewText(travelSummaryModel.getCarNumber());

        return convertView;
    }





    private class ViewHolder{
        public TitleContentView createDate;
        public TitleContentView timeSpan;
        public TitleContentView distance;
        public TitleContentView carNumber;

    }

}
