package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 指令重排：涉及的知识点
 */

public class A extends Activity {

    public static final String TAG = A.class.getSimpleName();
    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        ButterKnife.bind(this);
        LogUtil.d("onCreate: %s", this.getTaskId() + "");
        tvText.setText("指令重排：涉及到的知识点，在本线程内操作有序的；在线程外操作是无序的。" +
                "工作区内存和主内存，主内存到工作区内存read,load,工作区到主内存store,write" +
                "涉及到的关键字：volatile,synchronized,final");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @OnClick({R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_start_thread, R.id.btn_stop_thread})
    public void onClickListener(View view) {
        VolatileThread thread = new VolatileThread();
        switch (view.getId()) {
            case R.id.btn_a:
                startActivity(new Intent(this, A.class));
                break;
            case R.id.btn_b:
                Intent intent = new Intent(this, B.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.btn_c:
                startActivity(new Intent(this, C.class));
                break;
            case R.id.btn_d:
                startActivity(new Intent(this, D.class));
                break;
            case R.id.btn_start_thread:
                LogUtil.d("a :%s",Thread.currentThread().getName()+","+Thread.currentThread().getId());
                thread.start();
                break;
            case R.id.btn_stop_thread:
                thread.stopSelf();
                break;
        }
    }
    private volatile boolean stop = false;
    public class VolatileThread extends Thread{

        @Override
        public void run() {
            super.run();
            int i = 0;
            synchronized (VolatileThread.class) {
                while (!stop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    LogUtil.d("run thread: %s",i+","+Thread.currentThread().getName()+","+stop);
                }
            }

            LogUtil.d("stop thread");
        }

        public void stopSelf(){
            stop = true;
        }
    }
}
