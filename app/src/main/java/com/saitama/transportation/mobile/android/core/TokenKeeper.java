package com.saitama.transportation.mobile.android.core;

/**
 * Created by Max on 22/01/16.
 */
public interface TokenKeeper {
    String getToken();
    void setToken(String token);
    void clear();
}