package com.saitama.transportation.mobile.android.server.facade;

/**
 * Created by Max on 19/01/16.
 */
public interface FacadeCallback<T> {
    void onSuccess(T response);

    void onError(Throwable th);
}
