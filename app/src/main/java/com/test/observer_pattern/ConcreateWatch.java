package com.test.observer_pattern;

import com.test.LogUtil;

/**
 * Created by xingle on 2018/1/23.
 */

public class ConcreateWatch implements Watch {

    @Override
    public void update(String data) {
        LogUtil.d("%s",data);
    }
}
