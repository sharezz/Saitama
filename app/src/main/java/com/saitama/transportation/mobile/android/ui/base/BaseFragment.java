package com.saitama.transportation.mobile.android.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.saitama.transportation.mobile.android.SaitamaApplication;
import com.saitama.transportation.mobile.android.core.CoreService;

/**
 * Created by sharezzorama on 10/26/16.
 *
 * The base application fragment
 */

public class BaseFragment extends Fragment {
    private CoreService mCoreService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoreService = ((SaitamaApplication) getActivity().getApplication()).getCoreService();
    }

    protected CoreService getCoreService() {
        return mCoreService;
    }
}
