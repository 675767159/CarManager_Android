package com.qcwp.carmanager.implement;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Trace;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qyh on 2017/6/16.
 */

public class GreenDaoContext extends ContextWrapper {

    private String currentUserId = "greendao";//一般用来针对一个用户一个数据库，以免数据混乱问题
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public GreenDaoContext(Context base) {
        super(base);
        this.mContext =base;
        Print.d("getDatabasePath","=====");
    }
    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param name
     */
    @Override
    public File getDatabasePath(String name) {

        String basePath=CommonUtils.getMyFileFolder(mContext)+File.separator+currentUserId+File.separator;

        if (FileUtils.isFileExists(basePath+name)){
            Print.d("getDatabasePath","存在");
            return new File(basePath+name) ;
        }else {

            try {
                FileUtils.createOrExistsDir(basePath);
                InputStream is = mContext.getAssets().open(name);
                FileOutputStream fos = new FileOutputStream(new File(basePath+name));
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
                Print.d("getDatabasePath", "成功");

            } catch (IOException e) {
                Print.d("getDatabasePath", "失败");
                e.printStackTrace();
                this.createDataBase(basePath,name);
            }
        }


        return  new File(basePath+name);
    }


    private File createDataBase(String basePath,String name){


                File baseFile = new File(basePath);
                // 目录不存在则自动创建目录
                if (!baseFile.exists()){
                    baseFile.mkdirs();
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append(baseFile.getPath());
                buffer.append(File.separator);
                buffer.append(currentUserId);
                basePath = buffer.toString();// 数据库所在目录
                buffer.append(File.separator);
//        buffer.append(dbName+"_"+currentUserId);//也可以采用此种方式，将用户id与表名联系到一块命名
                buffer.append(name);
                String dbPath = buffer.toString();// 数据库路径
                // 判断目录是否存在，不存在则创建该目录
                File dirFile = new File(basePath);
                if (!dirFile.exists()){
                    dirFile.mkdirs();
                }
                // 数据库文件是否创建成功
                boolean isFileCreateSuccess = false;
                // 判断文件是否存在，不存在则创建该文件
                File dbFile = new File(dbPath);
                if (!dbFile.exists()) {
                    try {
                        isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    isFileCreateSuccess = true;
                // 返回数据库文件对象
                if (isFileCreateSuccess)
                    return dbFile;
                else
                    return super.getDatabasePath(name);

    }
    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return sqLiteDatabase;
    }




    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {

         sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return sqLiteDatabase;
    }



}
