package com.test.rxjava;

import com.test.LogUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by xingle on 2018/1/23.
 */

public class SimpleObserver implements Observer {

    public SimpleObserver(SimpleObservable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        LogUtil.d("%s", ((SimpleObservable)observable).getData());
    }
}
