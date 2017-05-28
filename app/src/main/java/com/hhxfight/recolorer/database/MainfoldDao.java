package com.hhxfight.recolorer.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HHX on 2017/5/29.
 */

public class MainfoldDao {

    private DBHelper dbHelper;
    public MainfoldDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public void insert (String path, String sid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into Mainfold(path, sid) values(?,?)", new Object[]{path, sid});
        db.close();
    }

    public String getSid(String path) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select sid from Mainfold where path = ?", new String[]{path});
        String tempSid = null;
        while (c.moveToNext()) {
            tempSid = c.getString(0);
        }
        c.close();
        db.close();
        return tempSid;
    }
}
