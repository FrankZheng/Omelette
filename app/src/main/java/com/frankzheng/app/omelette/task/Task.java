package com.frankzheng.app.omelette.task;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/4.
 */
public abstract class Task<T> implements Runnable {
    private static final String TAG = "Task";

    public abstract static class TaskListener<T> {
        public void onSuccess(Task task, T data) {

        }

        public void onError(OMError error) {

        }

        public void onComplete(Task task) {

        }
    }

    protected List<TaskListener<T>> listeners = new ArrayList<>();

    public void addTaskListener(TaskListener<T> listener) {
        listeners.add(listener);
    }

    public void removeTaskListener(TaskListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        Log.d(TAG, "run...");
        try {
            T data = doInBackground();
            onSuccess(data);
        } catch (OMError error) {
            Log.d(TAG, "onError");
            onError(error);
        } catch (Throwable t) {
            Log.d(TAG, "catch throwable");
            onError(new OMError(t));
        } finally {
            Log.d(TAG, "onComplete");
            onComplete();
        }
    }

    abstract protected T doInBackground() throws Throwable;

    protected void onSuccess(T data) {
        for (TaskListener<T> listener : listeners) {
            listener.onSuccess(this, data);
        }
    }

    private void onError(OMError error) {
        for (TaskListener listener : listeners) {
            listener.onError(error);
        }
    }

    protected void onComplete() {
        for (TaskListener listener : listeners) {
            listener.onComplete(this);
        }
    }

}