package com.test.rxjava;


import com.test.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by xingle on 2018/1/23.
 */

public class RxJavaUtil {
    private static final String TAG = RxJavaUtil.class.getSimpleName();

    public static void create(){
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                LogUtil.d("订阅关系：%s",subscriber.isUnsubscribed());
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("hello");
                    subscriber.onNext("rxjava");
                    subscriber.onCompleted();
                }
            }
        });

        Subscriber<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("onError :%s",e.getMessage());
            }

            @Override
            public void onNext(String s) {
                LogUtil.d("%s",s);
            }
        };

        observable.subscribe(observer);
    }

    public static void createPrint(){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<10;i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("error: %s",e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                LogUtil.d("%s",i+"");
            }
        });
    }

    /**
     * 使用在被观察者模，适用于数据类型
     */
    public static void from(){
        Integer[] items = {1,2,3,4,5,6,7,8};
        Observable observable = Observable.from(items);

        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                LogUtil.d("%s",o.toString());
            }
        });
    }

    public static void interval(){
        Integer[] times = {1,2,3,4};
        Observable observable = Observable.interval(1,1, TimeUnit.SECONDS);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                LogUtil.d("%s",o.toString());
            }
        });
    }

    /**
     * 处理数组集合
     */
    public static void just(){
        Integer[] item1 = {1,2,3,4,5,6,7};
        Integer[] item2 = {3,4,5,6,7};

        Observable observable = Observable.just(item1,item2);

        observable.subscribe(new Observer<Integer[]>() {
            @Override
            public void onCompleted() {
                LogUtil.d("onComplete");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("%s",e.getMessage());
            }

            @Override
            public void onNext(Integer[] o) {
                for (Integer i: o) {
                    LogUtil.d("%s",i);
                }
            }
        });
    }
}
