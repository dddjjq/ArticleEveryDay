package com.welson.artcleeveryday.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.welson.artcleeveryday.constants.Constants;

import java.lang.ref.WeakReference;

public class SharedPreferenceUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context){
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context mContext = weakReference.get();
        sharedPreferences = mContext.getSharedPreferences(Constants.DATE_SP,Context.MODE_PRIVATE);
    }

    public synchronized String getBeforeDate(){
        return sharedPreferences.getString(Constants.BEFORE_DATE,"");
    }

    public synchronized void setBeforeDate(String date){
        editor = sharedPreferences.edit();
        editor.putString(Constants.BEFORE_DATE,date);
        editor.apply();
    }

    public synchronized String getNextDate(){
        return sharedPreferences.getString(Constants.NEXT_DATE,"");
    }

    public synchronized void setNextDate(String date){
        editor = sharedPreferences.edit();
        editor.putString(Constants.NEXT_DATE,date);
        editor.apply();
    }

    public synchronized void setTodayDate(String todayDate){
        editor = sharedPreferences.edit();
        editor.putString(Constants.TODAY_DATE,todayDate);
        editor.apply();
    }

    public synchronized String getTodayDate(){
        return sharedPreferences.getString(Constants.TODAY_DATE,"");
    }

    public synchronized String getCurrentDate(){
        return sharedPreferences.getString(Constants.CURRENT_DATE,"");
    }

    public synchronized void setCurrentDate(String currentDate){
        editor = sharedPreferences.edit();
        editor.putString(Constants.CURRENT_DATE,currentDate);
        editor.apply();
    }

    public synchronized void setTextSizeIdx(int idx){
        editor = sharedPreferences.edit();
        editor.putInt(Constants.TEXT_SIZE,idx);
        editor.apply();
    }

    public synchronized int getTextSizeIdx(){
        return sharedPreferences.getInt(Constants.TEXT_SIZE,1);
    }

    public synchronized void setBackColor(int idx){
        editor = sharedPreferences.edit();
        editor.putInt(Constants.BACK_COLOR,idx);
        editor.apply();
    }

    public synchronized int getBackColor(){
        return sharedPreferences.getInt(Constants.BACK_COLOR,0);
    }

    public synchronized void setEnableNightMode(boolean b){
        editor = sharedPreferences.edit();
        editor.putBoolean(Constants.NIGHT_MODE,b);
        editor.apply();
    }

    public synchronized boolean getEnableNightMode(){
        return sharedPreferences.getBoolean(Constants.NIGHT_MODE,false);
    }
}
