package com.welson.artcleeveryday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.welson.artcleeveryday.entity.MainData;

import java.util.ArrayList;

public class DatabaseUtil {

    private SQLiteDatabase sqLiteDatabase;
    private static final String DB_NAME = "article.db";
    public static final String TABLE_NAME = "article";
    public static final String CURR_DATE = "curr_date";
    public static final String PREV_DATE = "prev_date";
    public static final String NEXT_DATE = "next_date";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String DIGEST = "digest";
    public static final String CONTENT = "content";
    public static final String WC = "wc";

    public DatabaseUtil(Context context){
        MyDBHelper helper = MyDBHelper.getInstance(context,DB_NAME);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void insertData(MainData mainData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CURR_DATE,mainData.getDate().getCurr());
        contentValues.put(PREV_DATE,mainData.getDate().getPrev());
        contentValues.put(NEXT_DATE,mainData.getDate().getNext());
        contentValues.put(AUTHOR,mainData.getAuthor());
        contentValues.put(TITLE,mainData.getTitle());
        contentValues.put(DIGEST,mainData.getDigest());
        contentValues.put(CONTENT,mainData.getContent());
        contentValues.put(WC,mainData.getWc());
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }

    public MainData getData(int currentDate){
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,"CURR_DATE=?",
                new String[]{String.valueOf(currentDate)},null,null,null);
        MainData mainData = new MainData();
        while (cursor.moveToNext()){
            MainData.Date date = mainData.new Date();
            date.setCurr(cursor.getInt(cursor.getColumnIndex(CURR_DATE)));
            date.setPrev(cursor.getInt(cursor.getColumnIndex(PREV_DATE)));
            date.setNext(cursor.getInt(cursor.getColumnIndex(NEXT_DATE)));
            mainData.setDate(date);
            mainData.setAuthor(cursor.getString(cursor.getColumnIndex(AUTHOR)));
            mainData.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            mainData.setDigest(cursor.getString(cursor.getColumnIndex(DIGEST)));
            mainData.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
            mainData.setWc(cursor.getInt(cursor.getColumnIndex(WC)));
        }
        return mainData;
    }

    public ArrayList<MainData> getAllData(){
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,null,
                null,null,null,null);
        ArrayList<MainData> mainDatas = new ArrayList<>();
        while (cursor.moveToNext()){
            MainData mainData = new MainData();
            MainData.Date date = mainData.new Date();
            date.setCurr(cursor.getInt(cursor.getColumnIndex(CURR_DATE)));
            date.setPrev(cursor.getInt(cursor.getColumnIndex(PREV_DATE)));
            date.setNext(cursor.getInt(cursor.getColumnIndex(NEXT_DATE)));
            mainData.setDate(date);
            mainData.setAuthor(cursor.getString(cursor.getColumnIndex(AUTHOR)));
            mainData.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            mainData.setDigest(cursor.getString(cursor.getColumnIndex(DIGEST)));
            mainData.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
            mainData.setWc(cursor.getInt(cursor.getColumnIndex(WC)));
            mainDatas.add(mainData);
        }
        return mainDatas;
    }

    public void deleteData(int currentDate){
        sqLiteDatabase.delete(TABLE_NAME,CURR_DATE+"=?"
                ,new String[]{String.valueOf(currentDate)});
    }

    public void closeDatabase(){
        sqLiteDatabase.close();
    }
}
