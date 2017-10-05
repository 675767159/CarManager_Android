package com.qcwp.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.qcwp.carmanager.R;
import com.qcwp.carmanager.enumeration.ExamnationStatusEnum;
import com.qcwp.carmanager.enumeration.KeyEnum;
import com.qcwp.carmanager.utils.Print;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qyh on 2017/9/4.
 *
 * @email:675767159@qq.com
 */

public class SetExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private JSONArray mList;

    public SetExpandableListAdapter(Context context, JSONArray mList){
        this.mList=mList;
        this.mContext=context;
    }
    //  获得父项的数量
    @Override
    public int getGroupCount() {

        return mList.length();
    }


    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {

        JSONObject jsonObject=mList.optJSONObject(groupPosition);
        JSONArray  list=jsonObject.optJSONArray("list");
        return list.length();

    }

    //  获得某个父项
    @Override
    public String getGroup(int groupPosition) {


        JSONObject jsonObject=mList.optJSONObject(groupPosition);
        String  title=jsonObject.optString("title");

        return title;
    }

    //  获得某个父项的某个子项
    @Override
    public JSONObject getChild(int groupPosition, int childPosition) {

        JSONObject jsonObject=mList.optJSONObject(groupPosition);
        JSONArray  list=jsonObject.optJSONArray("list");

        JSONObject jsonObject1=list.optJSONObject(childPosition);



        return jsonObject1;
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

        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_set_item, null);
        }

        convertView.findViewById(R.id.background).setBackgroundResource(R.color.backgroundColor);
        convertView.findViewById(R.id.ceil).setVisibility(View.GONE);

        TextView textView=(TextView)convertView.findViewById(R.id.title);
        textView.setText(getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String title=getChild(groupPosition,childPosition).optString("title");

        CellViewHolder cellViewHolder;
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_set_item, null);
            cellViewHolder=new CellViewHolder();
            cellViewHolder.title=(TextView) convertView.findViewById(R.id.title);
            cellViewHolder.aSwitch=(Switch) convertView.findViewById(R.id.aSwitch);
            convertView.setTag(cellViewHolder);
        }
        else {
            cellViewHolder=(CellViewHolder)convertView.getTag();
        }

        cellViewHolder.title.setText(title);

        Boolean isSwitch=getChild(groupPosition,childPosition).optBoolean("isSwitch");

        if (isSwitch){
            cellViewHolder.aSwitch.setVisibility(View.VISIBLE);
        }else {
            cellViewHolder.aSwitch.setVisibility(View.GONE);
        }


        return convertView;

    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }





    private class CellViewHolder{

        public TextView title;
        public Switch aSwitch;

    }

}
