package com.saitama.transportation.mobile.android.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.saitama.transportation.mobile.android.SaitamaApplication;
import com.saitama.transportation.mobile.android.core.CoreService;

/**
 * Created by sharezzorama on 10/26/16.
 * The base application activity
 */

public class BaseActivity extends AppCompatActivity {

    private CoreService mCoreService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoreService = ((SaitamaApplication) getApplication()).getCoreService();
    }

    protected CoreService getCoreService() {
        return mCoreService;
    }
}
