package com.welson.artcleeveryday.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();

    public static String getDateBefore(String currentDay){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat.parse(currentDay);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.before(currentDate);
        Log.d(TAG,"before time : " + simpleDateFormat.format(currentDate));
        return simpleDateFormat.format(currentDate);
    }

    public static String getDateNext(String currentDay){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat.parse(currentDay);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.after(currentDate);
        Log.d(TAG,"next time : " + simpleDateFormat.format(currentDate));
        return simpleDateFormat.format(currentDate);
    }
}

