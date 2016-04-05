package com.frankzheng.app.omelette.log;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class SimpleLogger implements ILogger {
    private String tag;

    @Override
    public boolean logsAreSaved() {
        return false;
    }

    @Override
    public List<LogRecord> getAllRecords() {
        return null;
    }

    SimpleLogger(String tag) {
        this.tag = tag;
    }

    @Override
    public void i(String msg) {
        i(tag, msg);
    }

    @Override
    public void d(String msg) {
        d(tag, msg);
    }

    @Override
    public void e(String msg) {
        e(tag, msg);
    }

    @Override
    public void v(String msg) {
        v(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        checkTag(tag);
        Log.i(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        checkTag(tag);
        Log.d(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        checkTag(tag);
        Log.e(tag, msg);
    }

    @Override
    public void v(String tag, String msg) {
        checkTag(tag);
        Log.v(tag, msg);
    }

    protected void checkTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Must have a tag for log");
        }
    }


}
