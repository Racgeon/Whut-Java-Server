package com.rkgn.web;

public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;

    public Result(Boolean success, String errorMsg, Object data, Long total) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
        this.total = total;
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

    public Long getTotal() {
        return total;
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

    public void setTotal(Long total) {
        this.total = total;
    }
}
