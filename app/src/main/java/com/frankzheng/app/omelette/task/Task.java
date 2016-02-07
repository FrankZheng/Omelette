package com.frankzheng.app.omelette.task;

import java.util.WeakHashMap;

/**
 * Created by zhengxiaoqiang on 16/2/4.
 */
public class Task<T> {

    public interface TaskListener<T> {
        void onSuccess(Task task, T data);
        void onError(int code);
    }

    protected WeakHashMap<TaskListener, Boolean> listeners = new WeakHashMap<>();

    public void addTaskListener(TaskListener listener) {
        listeners.put(listener, true);
    }

    public void removeTaskListener(TaskListener listener) {
        listeners.remove(listener);
    }

    protected void onSuccess(T data) {
        for (TaskListener listener : listeners.keySet()) {
            listener.onSuccess(this, data);
        }
    }

    protected void onError(int code) {
        for (TaskListener listener : listeners.keySet()) {
            listener.onError(code);
        }
    }

}