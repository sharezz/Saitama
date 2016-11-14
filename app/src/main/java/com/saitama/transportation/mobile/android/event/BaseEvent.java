package com.saitama.transportation.mobile.android.event;

/**
 * Created by sharezzorama on 10/26/16.
 * The abstract event with base data set
 */

public abstract class BaseEvent<T> {
    private T data;
    private Exception exception;

    public BaseEvent(T data) {
        this.data = data;
    }

    public BaseEvent(Exception exception) {
        this.exception = exception;
    }

    public boolean isOk() {
        return exception == null;
    }

    public T getData() {
        return data;
    }

    public Exception getException() {
        return exception;
    }

}
