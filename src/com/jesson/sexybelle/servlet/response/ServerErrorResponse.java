package com.jesson.sexybelle.servlet.response;

/**
 * Created by zhangdi on 14-3-7.
 */
public class ServerErrorResponse {

    public static final int PARAM_ERROR = 1;
    public static final int DATABASE_ERROR = 2;
    public static final int VERIFY_ERROR = 3;

    private int code;

    private String msg;

    public ServerErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ServerErrorResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
