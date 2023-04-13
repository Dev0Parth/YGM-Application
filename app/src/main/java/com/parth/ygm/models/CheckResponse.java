package com.parth.ygm.models;

public class CheckResponse {

    String errorCode, Message;

    public CheckResponse() {
    }

    public CheckResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        Message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
