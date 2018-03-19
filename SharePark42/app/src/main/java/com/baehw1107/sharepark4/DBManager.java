package com.baehw1107.sharepark4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baehw_000 on 2017-05-29.
 */

public class DBManager extends SQLiteOpenHelper{

    public DBManager(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table photos (p_result text, p_am_pm1 text, p_time_gap1 text, p_am_pm2 text, p_time_gap2 text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
