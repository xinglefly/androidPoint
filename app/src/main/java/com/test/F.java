package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chenzhenxing on 17/12/20.
 */

public class F extends Activity {

    public static final String TAG = F.class.getSimpleName();
    TextView tvShow;
    MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myHandler.post(r);
        setContentView(R.layout.activity_f);
        tvShow = findViewById(R.id.tv_show);

        HandlerThread handlerThread = new HandlerThread("myThread");
        handlerThread.start();

        handler = new MyHandler(handlerThread.getLooper());
//        Message msg = handler.obtainMessage();
//        Bundle bundle = new Bundle();
//        bundle.putString("name","xingle");
//        bundle.putInt("age",28);
//        msg.setData(bundle);
//        msg.sendToTarget();
        handler.post(r);

        Log.d(TAG, "activity-->"+Thread.currentThread().getId()+"-->"+Thread.currentThread().getName());
    }

    Runnable r = new Runnable() {
        int i = 0;
        @Override
        public void run() {
            i += 10;
            Log.d(TAG,"---->"+i);
            handler.postDelayed(r, 3000);
        }
    };


    class MyHandler extends Handler {

        public MyHandler() {
        }

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "myhandler-->"+Thread.currentThread().getId()+"-->"+Thread.currentThread().getName());
//            Bundle data = msg.getData();
//            tvShow.setText(data.get("name")+"-->"+data.get("age"));
        }
    }

    /*Handler myHandler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "handler-->"+Thread.currentThread().getId()+"-->"+Thread.currentThread().getName());
//            myHandler.postDelayed(r, 10000);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };*/



}
