package com.woshiku.urllibrary.domain;

/**
 * Created by Administrator on 2016/11/2.
 */
public class Result {
    private boolean success;
    private String msg;
    private String intent;

    public Result() {
    }

    public Result(boolean success, String msg, String intent) {
        this.success = success;
        this.msg = msg;
        this.intent = intent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "intent='" + intent + '\'' +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
