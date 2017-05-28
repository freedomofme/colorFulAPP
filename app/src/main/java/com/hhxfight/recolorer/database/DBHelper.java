package com.hhxfight.recolorer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HHX on 2017/5/29.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String name = "recolor"; //数据库名称

    private static final int version = 1; //数据库版本

    private static DBHelper helper;

    private DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Mainfold ("
                + "id integer primary key autoincrement, "
                + "path text, "
                + "sid text, "
                + "mid text DEFAULT \"\")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized  DBHelper getInstance (Context context) {
        if(helper == null){
            helper = new DBHelper(context);
        }
        return  helper;
    }
}
