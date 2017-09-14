package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.model.sqLiteModel.CarCheckModel;

import java.util.List;

/**
 * Created by qyh on 2017/6/21.
 */

public class CarCheckRecordAdapter extends BaseExpandableListAdapter {

    private List<CarCheckModel> mList;
    private Context mContext;
    public CarCheckRecordAdapter(Context context,List<CarCheckModel> carCheckModels){
        this.mContext=context;
        this.mList=carCheckModels;
    }
    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_check_record_head, null);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_check_record_cell, null);
            viewHolder=new  ViewHolder();
            viewHolder.createDate=(TextView) convertView.findViewById(R.id.createDate);
            viewHolder.carSeries=(TextView) convertView.findViewById(R.id.carSeries);
            viewHolder.checkScore=(TextView) convertView.findViewById(R.id.checkScore);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        CarCheckModel carCheckModel=mList.get(childPosition);



        viewHolder.createDate.setText(carCheckModel.getCreateDate());
        viewHolder.checkScore.setText(carCheckModel.getScore()+"分");
        viewHolder.carSeries.setText(carCheckModel.getCarSeries());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolder{
        public TextView createDate;
        public TextView carSeries;
        public TextView checkScore;
    }

}
