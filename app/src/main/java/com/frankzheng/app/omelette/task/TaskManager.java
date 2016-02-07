package com.frankzheng.app.omelette.task;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class TaskManager {
    private static TaskManager sInstance = new TaskManager();

    public static TaskManager getInstance() {
        return sInstance;
    }

    public void init() {

    }

    public void execute(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
