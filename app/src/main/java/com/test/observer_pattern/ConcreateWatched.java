package com.test.observer_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingle on 2018/1/23.
 */

public class ConcreateWatched implements Watched {
    List<Watch> list = new ArrayList<>();

    @Override
    public void addWatch(Watch watch) {
        list.add(watch);
    }

    @Override
    public void removeWatch(Watch watch) {
        list.remove(watch);
    }

    @Override
    public void notify(String data) {
        for (Watch watch : list) {
            watch.update(data);
        }
    }
}
