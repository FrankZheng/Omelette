package com.frankzheng.app.omelette.task;

import java.util.WeakHashMap;

/**
 * Created by zhengxiaoqiang on 16/2/4.
 */
public abstract class Task<T> implements Runnable {

    public abstract static class TaskListener<T> {
        public void onSuccess(Task task, T data) {

        }

        public void onError(OMError error) {

        }

        public void onComplete(Task task) {

        }
    }

    protected WeakHashMap<TaskListener<T>, Boolean> listeners = new WeakHashMap<>();

    public void addTaskListener(TaskListener<T> listener) {
        listeners.put(listener, true);
    }

    public void removeTaskListener(TaskListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        try {
            T data = doInBackground();
            onSuccess(data);
        } catch (Throwable t) {
            onError(new OMError(t));
        } finally {
            onComplete();
        }
    }

    abstract protected T doInBackground() throws Exception;

    protected void onSuccess(T data) {
        for (TaskListener<T> listener : listeners.keySet()) {
            listener.onSuccess(this, data);
        }
    }

    protected void onError(OMError error) {
        for (TaskListener listener : listeners.keySet()) {
            listener.onError(error);
        }
    }

    protected void onComplete() {
        for (TaskListener listener : listeners.keySet()) {
            listener.onComplete(this);
        }
    }

}