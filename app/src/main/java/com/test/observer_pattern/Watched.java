package com.test.observer_pattern;

/**
 * Created by xingle on 2018/1/23.
 */

public interface Watched {
    void addWatch(Watch watch);

    void removeWatch(Watch watch);

    void notify(String data);
}
