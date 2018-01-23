package com.test;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * Created by xingle on 2018/1/11.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init(true);

        LogUtil.d("progress: %s",android.os.Process.myPid());
        getProcessName();
    }

    private void getProcessName() {
        ActivityManager manager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info :processes) {
            LogUtil.d("run--> :%s",info.processName);
        }
    }
}
