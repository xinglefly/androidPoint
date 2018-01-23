package com.test;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.common.ToastUtil;
import com.test.observer_pattern.ConcreateWatch;
import com.test.observer_pattern.ConcreateWatched;
import com.test.observer_pattern.Watch;
import com.test.observer_pattern.Watched;
import com.test.rxjava.SimpleObservable;
import com.test.rxjava.SimpleObserver;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int backClickTime;
    MyService.Binder binder = null;
    NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, A.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind, R.id.btn_sync, R.id.btn_notify, R.id.btn_observer, R.id.btn_rxjava_observer})
    public void onClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        switch (view.getId()) {
            case R.id.btn_start:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.btn_stop:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.btn_bind:
                bindService(new Intent(this, MyService.class), this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(this);
                break;
            case R.id.btn_sync:
                if (binder != null) {
                    binder.setData(etName.getText().toString());
                }
                break;
            case R.id.btn_notify:
                NotificationCompat.Builder notify = (NotificationCompat.Builder) new NotificationCompat.Builder(this);
                notify.setSmallIcon(R.mipmap.ic_launcher);
                notify.setContentTitle("测试");
                notify.setContentText("都忘了呀");
                int max = 100;
                int progress = 10;
                boolean indeterminate = false;
                notify.setProgress(max, progress, true);

                int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                Intent inte = new Intent(this, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, flags);
                notify.setContentIntent(pi);


                manager.notify(0, notify.build());

                break;
            case R.id.btn_observer:
                Watched thief = new ConcreateWatched();

                Watch policeMan = new ConcreateWatch();
                Watch policeWomen = new ConcreateWatch();

                thief.addWatch(policeMan);
                thief.addWatch(policeWomen);

                thief.notify("小偷开始行动要去抢银行了！");
                break;
            case R.id.btn_rxjava_observer:
                SimpleObservable simpleObservable = new SimpleObservable();
                SimpleObserver observer = new SimpleObserver(simpleObservable);

                simpleObservable.setData(1);
                simpleObservable.setData(2);
                simpleObservable.setData(2);
                simpleObservable.setData(3);
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d(TAG, "onServiceConnected" + componentName + ",," + iBinder.toString());
        binder = (MyService.Binder) iBinder;
        binder.getService().setCallback(new MyService.Callback() {
            @Override
            public void onChangeData(final String data) {
                /*MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etName.setText(data);
                    }
                });*/
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG, "onServiceDisconnected");
        binder = null;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            etName.setText(msg.getData().getString("data"));
        }
    };

    /**
     * 处理返回键：只有在3秒内按返回2次，才退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (backClickTime == 1) {
                    Process.killProcess(Process.myPid());
                    backClickTime = 0;
                } else {
                    ToastUtil.showToast(R.string.back_confirm);
                    backClickTime++;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            backClickTime = 0;
                        }
                    }, 3000);
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
