package com.frankzheng.app.omelette.log;

import android.util.Log;
import android.util.SparseArray;

/**
 * Created by zhengxiaoqiang on 16/4/6.
 */
public final class LogLevel {
    public static final SparseArray<String> LEVEL_NAMES = new SparseArray<>();

    static {
        LEVEL_NAMES.append(Log.VERBOSE, "VERBOSE");
        LEVEL_NAMES.append(Log.DEBUG, "DEBUG");
        LEVEL_NAMES.append(Log.INFO, "INFO");
        LEVEL_NAMES.append(Log.WARN, "WARN");
        LEVEL_NAMES.append(Log.ERROR, "ERROR");
        LEVEL_NAMES.append(Log.ASSERT, "ASSERT");
    }

}
