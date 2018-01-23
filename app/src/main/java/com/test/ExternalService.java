package com.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ExternalService extends Service {

    private String data ="默认外服服务";
    private boolean running = false;

    public ExternalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return new IMyAidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

           @Override
           public void setData(String data) throws RemoteException {
                ExternalService.this.data = data;
           }
       };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate 外部服务");
        running = true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(running) {
                    LogUtil.d("log-->: %s",data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy 外部服务");
        running = false;
    }
}
