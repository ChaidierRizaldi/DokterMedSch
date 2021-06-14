package com.example.dokterandroidmedsch.utils;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class BaseApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(BaseApp.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
    }
}
