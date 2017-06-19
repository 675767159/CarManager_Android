package com.qcwp.carmanager.implement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * Created by qyh on 2017/6/17.
 */

public class GreenDaoOpenHelper extends DatabaseOpenHelper {


    public GreenDaoOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
