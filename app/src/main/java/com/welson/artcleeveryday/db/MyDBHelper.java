package com.welson.artcleeveryday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static MyDBHelper instance = null;

    public static MyDBHelper getInstance(Context context,String dbName){
        if (instance == null){
            instance = new MyDBHelper(context,dbName);
        }
        return instance;
    }

    private MyDBHelper(Context context,String dbName){
        super(context,dbName,null,1);
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table article(id integer primary key autoincrement," +
                "curr_date INTEGER," +
                "prev_date INTEGER," +
                "next_date INTEGER," +
                "author TEXT," +
                "title TEXT," +
                "digest TEXT," +
                "content TEXT," +
                "wc INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO for upgrade database
    }


}
