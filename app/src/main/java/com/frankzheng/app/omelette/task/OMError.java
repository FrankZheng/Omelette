package com.frankzheng.app.omelette.task;

import android.text.TextUtils;

/**
 * Created by zhengxiaoqiang on 16/2/7.
 */
public class OMError {
    private int code;
    private String message;
    private Throwable throwable;
    private String status;

    public OMError() {

    }

    public OMError(String status) {
        this.status = status;
    }

    public OMError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public OMError(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getMessage() {
        if (!TextUtils.isEmpty(message)) {
            return String.format("%s[%d]", message, code);
        } else if (!TextUtils.isEmpty(status)) {
            return status;
        } else if (throwable != null) {
            return throwable.getLocalizedMessage();
        }
        return "";
    }
}
