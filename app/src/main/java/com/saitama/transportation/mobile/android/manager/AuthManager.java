package com.saitama.transportation.mobile.android.manager;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.event.AuthEvent;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.impl.AuthFacade;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sharezzorama on 10/26/16.
 * The manager contains methods for user authentication
 */

public class AuthManager extends BaseManager {
    private AuthFacade mAuthFacade;

    public AuthManager(TokenKeeper tokenKeeper) {
        super(tokenKeeper);
        mAuthFacade = new AuthFacade(Constants.SERVER_ADDRESS, tokenKeeper);
    }

    /**
     * Sends user login request to server
     * @param email - user email
     * @param password - user password
     */
    public void login(String email, String password) {
        mAuthFacade.login(email, password, new FacadeCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse response) {
                String accessToken = response.getAccessToken();
                mTokenKeeper.setToken(accessToken);
                EventBus.getDefault().post(new AuthEvent(accessToken));
            }

            @Override
            public void onError(Throwable th) {
                EventBus.getDefault().post(new AuthEvent((Exception) th));
            }
        });
    }

    /**
     * Sends new user register request to server
     * @param email - user email
     * @param password - user password
     */
    public void register(String email, String password) {
        mAuthFacade.register(email, password, new FacadeCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse response) {
                String accessToken = response.getAccessToken();
                mTokenKeeper.setToken(accessToken);
                EventBus.getDefault().post(new AuthEvent(accessToken));
            }

            @Override
            public void onError(Throwable th) {
                EventBus.getDefault().post(new AuthEvent((Exception) th));
            }
        });
    }
}
