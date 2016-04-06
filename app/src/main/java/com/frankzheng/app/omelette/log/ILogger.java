package com.frankzheng.app.omelette.log;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public interface ILogger {
    void v(String tag, String msg);

    void d(String tag, String msg);
    void i(String tag, String msg);

    void w(String tag, String msg);
    void e(String tag, String msg);

    void v(String msg);

    void d(String msg);
    void i(String msg);

    void w(String msg);
    void e(String msg);

    boolean logsAreSaved();

    List<LogRecord> getAllRecords();
}
