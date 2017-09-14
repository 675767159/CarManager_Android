package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.enumeration.ExamnationStatusEnum;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.utils.Print;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qyh on 2017/9/4.
 *
 * @email:675767159@qq.com
 */

public class CarExamnationExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Map> mList;
    private final int faultCode=0,other=1;


    public CarExamnationExpandableListAdapter(Context context){
        mContext=context;
    }
    //  获得父项的数量
    @Override
    public int getGroupCount() {

        return mList==null?0:mList.size();
    }


    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mList!=null&&mList.size()>0) {
            Map map = mList.get(groupPosition);
            if (map!=null) {
                List list = (List) map.get(KeyEnum.childKey);
                return list==null?0:list.size();
            }
        }

        return 0;

    }

    //  获得某个父项
    @Override
    public Map getGroup(int groupPosition) {

        Map map=mList.get(groupPosition);

        return (Map) map.get(KeyEnum.groupKey);
    }

    //  获得某个父项的某个子项
    @Override
    public Map getChild(int groupPosition, int childPosition) {
        Map map=mList.get(groupPosition);
        List list=(List)map.get(KeyEnum.childKey);

        return (Map)list.get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        int type=getGroupType(groupPosition);
        Map map=this.getGroup(groupPosition);
        switch (type){
            case faultCode:{


                CellViewHolder cellViewHolder;

                if (convertView==null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_examnation_expand_cell, null);
                    cellViewHolder=new CellViewHolder();
                    cellViewHolder.content=(TextView) convertView.findViewById(R.id.content);
                    cellViewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
                    convertView.setTag(cellViewHolder);
                }
                else {
                    cellViewHolder=(CellViewHolder)convertView.getTag();
                }

                ExamnationStatusEnum statusEnum=(ExamnationStatusEnum)map.get(KeyEnum.statusKey);
                if (statusEnum==ExamnationStatusEnum.NORMAL) {
                    cellViewHolder.imageView.setImageResource(R.mipmap.car_examination_good);
                }else {
                    cellViewHolder.imageView.setImageResource(R.mipmap.car_examination_bad);
                }
                cellViewHolder.content.setText((String)map.get(KeyEnum.nameKey));


            }
                break;
            case other: {

                HeadViewHolder headViewHolder;

                if (convertView==null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_examnation_expand_head, null);
                    headViewHolder=new HeadViewHolder();
                    headViewHolder.title=(TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(headViewHolder);
                }
                else {
                    headViewHolder=(HeadViewHolder)convertView.getTag();
                }


                headViewHolder.title.setText((String)map.get(KeyEnum.nameKey));

            }
                break;

        }




        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        int type=getChildType(groupPosition,childPosition);
        Map map=this.getChild(groupPosition,childPosition);
        switch (type){
            case faultCode:{


                HeadViewHolder faultViewHolder;

                if (convertView==null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_examnation_expand_fault_cell, null);
                    faultViewHolder=new HeadViewHolder();
                    faultViewHolder.title=(TextView) convertView.findViewById(R.id.content);
                    convertView.setTag(faultViewHolder);
                }
                else {
                    faultViewHolder=(HeadViewHolder)convertView.getTag();
                }



                faultViewHolder.title.setText((String)map.get(KeyEnum.nameKey));


            }
            break;
            case other: {

                CellViewHolder cellViewHolder;

                if (convertView==null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.view_car_examnation_expand_cell, null);
                    cellViewHolder=new CellViewHolder();
                    cellViewHolder.content=(TextView) convertView.findViewById(R.id.content);
                    cellViewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
                    convertView.setTag(cellViewHolder);
                }
                else {
                    cellViewHolder=(CellViewHolder)convertView.getTag();
                }



                ExamnationStatusEnum statusEnum=(ExamnationStatusEnum)map.get(KeyEnum.statusKey);
                if (statusEnum==ExamnationStatusEnum.NORMAL) {
                    cellViewHolder.imageView.setImageResource(R.mipmap.car_examination_good);
                }else {
                    cellViewHolder.imageView.setImageResource(R.mipmap.car_examination_bad);
                }
                cellViewHolder.content.setText((String)map.get(KeyEnum.nameKey));

            }
            break;

        }


        return convertView;

    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public void invalidData(){
        mList=null;
        this.notifyDataSetInvalidated();


    }

    public void updateData(List<Map> list){
        mList=list;
        this.notifyDataSetChanged();


    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        Map map=this.getGroup(groupPosition);
        ExamnationStatusEnum status= (ExamnationStatusEnum)map.get(KeyEnum.statusKey);
        if (status==ExamnationStatusEnum.Unknown){
            return other;
        }
            return faultCode;

    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        Map map=this.getChild(groupPosition,childPosition);
        ExamnationStatusEnum status= (ExamnationStatusEnum)map.get(KeyEnum.statusKey);
        if (status==ExamnationStatusEnum.Unknown){
            return faultCode;
        }
        return other;
    }



    private class HeadViewHolder{
        public TextView title;

    }

    private class CellViewHolder{
        public ImageView imageView;
        public TextView content;

    }

}
