package com.frankzheng.app.omelette.log;

import com.frankzheng.app.omelette.MainApplication;

import java.util.Date;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class DBLogger extends SimpleLogger {

    private final int LOG_LEVEL_VERBOSE = 0;
    private final int LOG_LEVEL_INFO = 1;
    private final int LOG_LEVEL_DEBUG = 2;
    private final int LOG_LEVEL_ERROR = 3;


    SQLiteHelper dbHelper;

    public DBLogger(String tag) {
        super(tag);
        dbHelper = new SQLiteHelper(MainApplication.context);
    }


    @Override
    public void i(String tag, String msg) {

    }

    @Override
    public void d(String tag, String msg) {

    }

    @Override
    public void e(String tag, String msg) {

    }

    @Override
    public void v(String tag, String msg) {

    }

    @Override
    public void i(String msg) {

    }

    @Override
    public void d(String msg) {

    }

    @Override
    public void e(String msg) {

    }

    @Override
    public void v(String msg) {

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
        return record;
    }

    private void insertLogToDB(String tag, int level, String log) {
        LogRecord record = createLog(tag, level, log);
        dbHelper.insertRecord(record);
    }
}
