package com.welson.artcleeveryday;

import android.app.Application;

import com.welson.artcleeveryday.db.DatabaseUtil;

public class MyApplication extends Application {

    public static DatabaseUtil databaseUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseUtil = new DatabaseUtil(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        databaseUtil.closeDatabase();
    }
}
