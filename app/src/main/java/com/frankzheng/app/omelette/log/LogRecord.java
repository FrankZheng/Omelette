package com.frankzheng.app.omelette.log;

import java.util.Date;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class LogRecord {
    private Date date;
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

    @Override
    public String toString() {
        return String.format("%d - %s - %s", date.getTime(), tag, log);
    }
}
