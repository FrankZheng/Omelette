package com.frankzheng.app.omelette.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class LogRecord {
    private static final String DATE_FMT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private Date date;
    private int level;
    private String tag;
    private String log;

    public LogRecord() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FMT_PATTERN, Locale.US);
        return String.format("%s - %s - %s - %s",
                fmt.format(date),
                LogLevel.LEVEL_NAMES.get(level),
                tag,
                log);
    }
}
