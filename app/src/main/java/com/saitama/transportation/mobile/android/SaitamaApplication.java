package com.saitama.transportation.mobile.android;

import android.app.Application;

import com.saitama.transportation.mobile.android.core.CoreService;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class SaitamaApplication extends Application {
    private CoreService mCoreService;

    @Override
    public void onCreate() {
        super.onCreate();
        mCoreService = new CoreService(this);
    }

    /**
     * Returns the CoreService instance
     * @return the CoreService instance
     */
    public CoreService getCoreService() {
        return mCoreService;
    }
}
