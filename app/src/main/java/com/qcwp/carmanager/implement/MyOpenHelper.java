package com.qcwp.carmanager.implement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qcwp.carmanager.greendao.gen.CarCheckModelDao;
import com.qcwp.carmanager.greendao.gen.DaoMaster;
import com.qcwp.carmanager.model.sqLiteModel.CarCheckModel;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qyh on 2017/9/29.
 *
 * @email:675767159@qq.com
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {


    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新 有几个表升级都可以传入到下面
        MigrationHelper.getInstance().migrate(db,CarCheckModelDao.class);
    }
}
