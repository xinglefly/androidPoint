package com.test.rxjava;


import java.util.Observable;

/**
 * Created by xingle on 2018/1/23.
 */

public class SimpleObservable extends Observable {

    private int data = 0;

    public int getData() {
        return data;
    }

    public void setData(int i) {
        if (this.data != i){
            this.data = i;
            setChanged();
            notifyObservers();
        }
    }
}
