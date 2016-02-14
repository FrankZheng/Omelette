package com.frankzheng.app.omelette.task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class TaskManager {
    private static TaskManager sInstance = new TaskManager();

    public static TaskManager getInstance() {
        return sInstance;
    }

    private Executor executor;

    private static ThreadFactory getMyThreadFactory() {
        return new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("task-thread-" + count);
                return thread;
            }
        };
    }

    private TaskManager() {
        executor = Executors.newCachedThreadPool(getMyThreadFactory());
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
