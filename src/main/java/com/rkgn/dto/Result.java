package com.rkgn.dto;

public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;

    public Result(Boolean success, String errorMsg, Object data) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
