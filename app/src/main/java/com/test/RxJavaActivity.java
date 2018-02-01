package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.test.rxjava.RxJavaUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xingle on 2018/1/23.
 */

public class RxJavaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_create, R.id.btn_print, R.id.btn_from})
    void onClickListener(View view){
        switch (view.getId()) {
            case R.id.btn_create:
                RxJavaUtil.create();
                break;
            case R.id.btn_print:
                RxJavaUtil.createPrint();
                break;
            case R.id.btn_from:
//                RxJavaUtil.from();
//                RxJavaUtil.interval();
                RxJavaUtil.just();
                break;
        }
    }
}
