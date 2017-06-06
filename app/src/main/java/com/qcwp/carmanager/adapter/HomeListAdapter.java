package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.utils.Print;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DL on 16/9/26.
 */
public class HomeListAdapter extends BaseAdapter {



    private List<Map> mList;
    private Context mContext;
    private int viewHeight;
    public HomeListAdapter(Context context,int viewHeight){
        mContext=context;
        this.mList=new ArrayList<>();
        this.viewHeight=viewHeight;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Map getItem(int i) {

        return mList.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_home_list_item, null);
            viewHolder=new ViewHolder();
            viewHolder.container=convertView.findViewById(R.id.container);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        ViewGroup.LayoutParams layoutParam = viewHolder.container.getLayoutParams();
        layoutParam.height=viewHeight;
        viewHolder.container.setLayoutParams(layoutParam);

        Print.d("layoutParam",layoutParam+"-----"+viewHolder.imageView);



        return convertView;
    }





    private class ViewHolder{
        public TextView textView_title;
        public TextView textView_source;
        public TextView textView_time;
        public TextView textView_content;
        public ImageView imageView;
        public View container;


    }
    public  void setData(List list){
        this.mList=list;
        this.notifyDataSetChanged();

    }
}
