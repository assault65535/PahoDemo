package com.tnecesoc.pahodemo.Util;

import com.tnecesoc.pahodemo.IMAdapter;

import java.util.TimerTask;

/**
 * Created by Tnecesoc on 2016/9/9.
 * 显示所有信息的 publishTime 5 秒后隐藏
 */
public class TogglePublishTimeTask extends TimerTask {

    private static boolean isTriggered = false;

    private IMAdapter adapter;

    public TogglePublishTimeTask(IMAdapter adapter) {
        this.adapter = adapter;
        isTriggered = true;
    }

    public static boolean isTriggered() {
        return isTriggered;
    }

    @Override
    public void run() {
        adapter.togglePublishTime();
        isTriggered = false;
    }
}
