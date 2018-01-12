package com.test;

import android.app.Application;

/**
 * Created by xingle on 2018/1/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true);
    }
}
