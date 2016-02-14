package com.frankzheng.app.omelette.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public class ThreadUtil {
    public static void runOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
