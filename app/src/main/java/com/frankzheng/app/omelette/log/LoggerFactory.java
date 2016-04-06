package com.frankzheng.app.omelette.log;

/**
 * Created by zhengxiaoqiang on 16/4/5.
 */
public class LoggerFactory {
    private static volatile LoggerFactory instance = null;

    private ILogger logger;

    public static LoggerFactory getInstance() {
        if (instance == null) {
            synchronized (LoggerFactory.class) {
                if (instance == null) {
                    instance = new LoggerFactory();
                }
            }
        }
        return instance;
    }


    public ILogger getLogger(String tag) {
        return new DBLogger(tag);
    }
}
