package com.liuquan.liwushuo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by PC on 2016/3/29.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Don't do this! This is just so cold launches take some time
//        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
