package com.qcwp.carmanager.implement;

import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by qyh on 2017/10/4.
 *
 * @email:675767159@qq.com
 */

public class GroupClickListener implements ExpandableListView.OnGroupClickListener {
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }
}
