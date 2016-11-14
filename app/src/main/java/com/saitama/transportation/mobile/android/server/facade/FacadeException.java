package com.saitama.transportation.mobile.android.server.facade;

/**
 * Created by Max on 22/01/16.
 */
public class FacadeException extends Exception {
    private String code;
    private String message;
    private int responseCode;

    public FacadeException() {
    }

    public FacadeException(Throwable throwable) {
        super(throwable);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}