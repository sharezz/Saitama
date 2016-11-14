package com.saitama.transportation.mobile.android.event;

/**
 * Created by sharezzorama on 10/26/16.
 * Posts when user authentication success or fail
 */

public class AuthEvent extends BaseEvent<String> {
    public AuthEvent(String data) {
        super(data);
    }

    public AuthEvent(Exception exception) {
        super(exception);
    }
}
