package com.saitama.transportation.mobile.android.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.saitama.transportation.mobile.android.helper.SettingsHelper;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class PreferenceTokenKeeperImpl implements TokenKeeper {

    private SharedPreferences mPreferences;

    public PreferenceTokenKeeperImpl(Context context) {
        mPreferences = new SettingsHelper(context).getSharedPreferences();
    }

    @Override
    public String getToken() {
        return mPreferences.getString(SettingsHelper.KEY_TOKEN, SettingsHelper.DEFAULT_TOKEN);
    }

    @Override
    public void setToken(String token) {
        mPreferences.edit().putString(SettingsHelper.KEY_TOKEN, token).commit();
    }

    @Override
    public void clear() {
        mPreferences.edit().putString(SettingsHelper.KEY_TOKEN, SettingsHelper.DEFAULT_TOKEN).commit();
    }
}
