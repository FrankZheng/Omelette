package com.frankzheng.app.omelette.log;

import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class DBLogger extends SimpleLogger {

    SQLiteHelper dbHelper = SQLiteHelper.getInstance();

    public DBLogger(String tag) {
        super(tag);
    }

    @Override
    public void i(String tag, String msg) {
        super.i(tag, msg);
        insertLogToDB(tag, Log.INFO, msg);
    }

    @Override
    public void d(String tag, String msg) {
        super.d(tag, msg);
        insertLogToDB(tag, Log.DEBUG, msg);
    }

    @Override
    public void e(String tag, String msg) {
        super.e(tag, msg);
        insertLogToDB(tag, Log.ERROR, msg);
    }

    @Override
    public void v(String tag, String msg) {
        super.v(tag, msg);
        insertLogToDB(tag, Log.VERBOSE, msg);

    }

    @Override
    public void w(String tag, String msg) {
        super.w(tag, msg);
        insertLogToDB(tag, Log.WARN, msg);
    }

    @Override
    public void w(String msg) {
        w(tag, msg);
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
    public boolean logsAreSaved() {
        return true;
    }

    @Override
    public List<LogRecord> getAllRecords() {
        return dbHelper.getAllRecords();
    }

    private LogRecord createLog(String tag, int level, String log) {
        LogRecord record = new LogRecord();
        record.setTag(tag);
        record.setDate(new Date());
        record.setLog(log);
        record.setLevel(level);
        return record;
    }

    private void insertLogToDB(String tag, int level, String log) {
        LogRecord record = createLog(tag, level, log);
        dbHelper.insertRecord(record);
    }
}
