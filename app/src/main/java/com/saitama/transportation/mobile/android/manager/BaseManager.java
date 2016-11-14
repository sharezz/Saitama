package com.saitama.transportation.mobile.android.manager;

import com.saitama.transportation.mobile.android.core.TokenKeeper;

/**
 * Created by sharezzorama on 10/26/16.
 * The abstract manager
 */

public abstract class BaseManager {
    protected TokenKeeper mTokenKeeper;

    public BaseManager(TokenKeeper tokenKeeper) {
        mTokenKeeper = tokenKeeper;
    }
}
