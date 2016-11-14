package com.saitama.transportation.mobile.android.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sharezzorama on 10/26/16.
 * Hepler for getting SharedPreferences
 */
public class SettingsHelper {
    //Preference keys
    public static final String KEY_TOKEN = "token";
    //Preference default values
    public static final String DEFAULT_TOKEN = "";

    private static final String PREF_NAME = "pref_settings";
    private SharedPreferences mSharedPreferences;

    public SettingsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * The method returns SharedPreferences instance
     * @return - SharedPreferences instance
     */
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
