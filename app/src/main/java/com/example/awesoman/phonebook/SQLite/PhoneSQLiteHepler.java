package com.example.awesoman.phonebook.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Awesome on 2016/9/29.
 */
public class PhoneSQLiteHepler extends SQLiteOpenHelper {

    public static String DATABASE_NAME="phone.db";
    public static int DATA_VERSION = 1;

    public PhoneSQLiteHepler(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL(TypeEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除原表
        db.execSQL(TypeEntry.SQL_DELETE_TABLE);
        //建表
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
