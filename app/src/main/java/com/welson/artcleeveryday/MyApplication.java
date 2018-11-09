package com.welson.artcleeveryday;

import android.app.Application;
import android.util.Log;

import com.welson.artcleeveryday.db.DatabaseUtil;

public class MyApplication extends Application {

    public static DatabaseUtil databaseUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseUtil = new DatabaseUtil(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_COMPLETE){
            Log.d("dingyl","onTrimMemory");
            //databaseUtil.closeDatabase();
        }
    }
}
