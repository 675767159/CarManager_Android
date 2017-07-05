package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;
import com.qcwp.carmanager.ui.CarEditActivity;

import java.util.List;

/**
 * Created by DL on 16/9/26.
 */
public class CarEditSelectAdapter extends BaseAdapter {



    private List mList;
    private Context mContext;
    CarEditActivity.CarListType type;
    public CarEditSelectAdapter(Context context, CarEditActivity.CarListType type, List list){
         mContext=context;
        this.mList=list;
        this.type=type;
    }

    @Override
    public int getCount() {
        if (mList!=null)
        return mList.size();
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {

        return mList.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_edit_select_item, null);
            viewHolder=new ViewHolder();
            viewHolder.name=(TextView) convertView.findViewById(R.id.name);
            viewHolder.name_spell=(TextView) convertView.findViewById(R.id.name_spell);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }


        switch (type){
            case CarBrand:
                CarBrandModel carBrandModel=(CarBrandModel)mList.get(position);
                viewHolder.name.setText(carBrandModel.getBrandName());
                viewHolder.name_spell.setText(carBrandModel.getBrandNamePinYin());
                break;
            case CarCommonBrand:
                CommonBrandModel commonBrandModel=(CommonBrandModel)mList.get(position);
                viewHolder.name.setText(commonBrandModel.getCommonBrandName());
                break;
            case Carseries:
                CarSeriesModel carSeriesModel=(CarSeriesModel)mList.get(position);
                viewHolder.name.setText(carSeriesModel.getSeriesName());
                break;
            case CarType:
                CarTypeModel carTypeModel=(CarTypeModel)mList.get(position);
                viewHolder.name.setText(carTypeModel.getCarTypeName());
                break;
        }

        return convertView;
    }





    private class ViewHolder{
        public TextView name;
        public TextView name_spell;

    }
    public  void setData(List list){
        this.mList=list;
        this.notifyDataSetChanged();

    }
}
