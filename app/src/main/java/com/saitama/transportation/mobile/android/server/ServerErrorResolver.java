package com.saitama.transportation.mobile.android.server;

import com.saitama.transportation.mobile.android.server.facade.FacadeException;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class ServerErrorResolver {

    public static String resolveError(Exception e) {
        //TODO return handled message (localized for example)
        return e.getMessage();
    }
}
