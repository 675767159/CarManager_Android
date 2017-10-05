package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.utils.CommonUtils;

import java.util.List;
import java.util.Locale;


public class TravelRecordAdapter extends BaseAdapter {


    private List<TravelSummaryModel> mList;
    private Context mContext;
    private Locale locale = Locale.getDefault();

    public TravelRecordAdapter(Context context, List<TravelSummaryModel> list) {
        mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {

        return mList == null ? 0 : mList.size();
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

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_travel_record_cell, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.createDate = (TitleContentView) convertView.findViewById(R.id.createDate);
            viewHolder.distance = (TitleContentView) convertView.findViewById(R.id.distance);
            viewHolder.averageSpeed = (TitleContentView) convertView.findViewById(R.id.averageSpeed);
            viewHolder.accelerateCount = (TitleContentView) convertView.findViewById(R.id.accelerateCount);
            viewHolder.driveTime = (TitleContentView) convertView.findViewById(R.id.driveTime);
            viewHolder.carCheckScore = (TitleContentView) convertView.findViewById(R.id.carCheckScore);
            viewHolder.averageOilResume = (TitleContentView) convertView.findViewById(R.id.averageOilConsume);
            viewHolder.brakeCount = (TitleContentView) convertView.findViewById(R.id.brakeCount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelSummaryModel travelSummaryModel = this.getItem(position);
        viewHolder.createDate.setContentTextViewText(travelSummaryModel.getStartDate());
        viewHolder.distance.setContentTextViewText(String.format(locale, "%.1f km", travelSummaryModel.getMileage()));
        viewHolder.averageSpeed.setContentTextViewText(String.format(locale, "%.1f km/h", travelSummaryModel.getAverageSpeed()));
        viewHolder.accelerateCount.setContentTextViewText(String.format(locale, "%d 次", travelSummaryModel.getAccelerCount()));
        viewHolder.driveTime.setContentTextViewText(CommonUtils.longTimeToStr(travelSummaryModel.getDriveTime()));
        viewHolder.carCheckScore.setContentTextViewText(String.format(locale, "%d分", travelSummaryModel.getCarCheckUpScore()));
        viewHolder.averageOilResume.setContentTextViewText(String.format(locale, "%.1f L/100km", travelSummaryModel.getAverageOilConsume()));
        viewHolder.brakeCount.setContentTextViewText(String.format(locale, "%d 次", travelSummaryModel.getDecelerCount()));

        return convertView;
    }


    private class ViewHolder {
        private TitleContentView createDate;
        private TitleContentView distance;
        private TitleContentView averageSpeed;
        private TitleContentView accelerateCount;
        private TitleContentView driveTime;
        private TitleContentView carCheckScore;
        private TitleContentView averageOilResume;
        private TitleContentView brakeCount;

    }

}
