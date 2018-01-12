package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by chenzhenxing on 17/12/19.
 */

public class E extends Activity implements View.OnClickListener {

    public static final String TAG = E.class.getSimpleName();

    ProgressBar progressBar;

    /*
    Handler myHandler = new Handler();
    int i = 0;
    Runnable run = new Runnable() {
        @Override
        public void run() {
            i += 1;
            Log.d(TAG,"---------"+i);
            myHandler.postDelayed(run, 3000);
        }
    };*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        findViewById(R.id.btn_thread).setOnClickListener(this);
        findViewById(R.id.btn_end).setOnClickListener(this);
        findViewById(R.id.btn_open_f).setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_thread:
                Log.d(TAG,"start runable");
//                myHandler.post(run);
                updateHandler.post(updateRunnable);
                break;
            case R.id.btn_end:
                Log.d(TAG,"stop runable");
//                myHandler.removeCallbacks(run);
                updateHandler.removeCallbacks(updateRunnable);
                break;
            case R.id.btn_open_f:
                startActivity(new Intent(this, F.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        myHandler.removeCallbacks(run);
    }


    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
            if (msg.arg1 == 100) {
                Log.d(TAG,"is stop");
                updateHandler.removeCallbacks(updateRunnable);
            } else updateHandler.post(updateRunnable);
        }
    };

    Runnable updateRunnable = new Runnable() {
        int i;
        @Override
        public void run() {
            Log.d(TAG,"start thread-->"+i);
            i += 10;
            Message msg = updateHandler.obtainMessage();
            msg.arg1 = i;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            updateHandler.sendMessage(msg);
        }
    };
}
