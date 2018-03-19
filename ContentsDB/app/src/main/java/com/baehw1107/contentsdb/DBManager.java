package com.baehw1107.contentsdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baehw_000 on 2017-05-26.
 */

public class DBManager extends SQLiteOpenHelper{
    public DBManager(Context context){
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table photos(title text, orientation text, background text, path text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
