package com.basti.rxjavasample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by SHIBW-PC on 2016/1/22.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
