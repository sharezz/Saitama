package com.saitama.transportation.mobile.android.core;

import android.content.Context;

import com.saitama.transportation.mobile.android.manager.AuthManager;
import com.saitama.transportation.mobile.android.manager.BaseManager;
import com.saitama.transportation.mobile.android.manager.PaymentManager;
import com.saitama.transportation.mobile.android.manager.PlacesManager;

/**
 * Created by sharezzorama on 10/26/16.
 * The class for convenient accessing common application components and managers
 */

public class CoreService {
    private AuthManager mAuthManager;
    private PlacesManager mPlacesManager;
    private PaymentManager mPaymentManager;
    private Context mContext;
    private TokenKeeper mTokenKeeper;

    public CoreService(Context context) {
        mContext = context;
        mTokenKeeper = new PreferenceTokenKeeperImpl(context);
    }

    /**
     * Creates and return AuthManager instance
     * @return - AuthManager instance
     */
    public AuthManager getAuthManager() {
        if (mAuthManager == null) {
            mAuthManager = new AuthManager(mTokenKeeper);
        }
        return mAuthManager;
    }

    /**
     * Creates and return PlacesManager instance
     * @return - PlacesManager instance
     */
    public PlacesManager getPlaceManager() {
        if (mPlacesManager == null) {
            mPlacesManager = new PlacesManager(mTokenKeeper);
        }
        return mPlacesManager;
    }

    /**
     * Creates and return PaymentManager instance
     * @return - PaymentManager instance
     */
    public PaymentManager getPaymentManager() {
        if (mPaymentManager == null) {
            mPaymentManager = new PaymentManager(mTokenKeeper);
        }
        return mPaymentManager;
    }

    /**
     * Returns TokenKeeper instance
     * @return - TokenKeeper instance
     */
    public TokenKeeper getTokenKeeper() {
        return mTokenKeeper;
    }
}
