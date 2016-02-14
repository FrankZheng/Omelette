package com.frankzheng.app.omelette.task;

/**
 * Created by zhengxiaoqiang on 16/2/7.
 */
public class OMError extends Throwable {
    private int code;

    public OMError() {
        super();
    }

    public OMError(String message) {
        super(message);
    }

    public static OMError createWithStatus(String status) {
        String message = String.format("wrong status: %s", status);
        OMError error = new OMError(message);
        return error;
    }

    public OMError(int code, String message) {
        super(String.format("[%d]%s", code, message));
        this.code = code;
    }

    public OMError(Throwable cause) {
        super(cause);
    }


}
