package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.control.TitleContentView;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.utils.CommonUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by DL on 16/9/26.
 */
public class VinCodeListAdapter extends BaseAdapter {



    private List<CarInfoModel> mList;
    private Context mContext;
    private Locale locale=Locale.getDefault();
    public VinCodeListAdapter(Context context, List<CarInfoModel> list){
         mContext=context;
        this.mList=list;
    }

    @Override
    public int getCount() {

        return mList==null?1:mList.size()+1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public CarInfoModel getItem(int i) {

        return mList.get(i);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_edit_select_item, null);
            viewHolder=new ViewHolder();
            viewHolder.name=(TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        if (position<mList.size()) {
            CarInfoModel carInfoModel = this.getItem(position);
            viewHolder.name.setText(carInfoModel.getVinCode());
        }else {
            viewHolder.name.setText("都没有，我要重新输入");
        }

        return convertView;
    }





    private class ViewHolder{
        public TextView name;


    }

}
