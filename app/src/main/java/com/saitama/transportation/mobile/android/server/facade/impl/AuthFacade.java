package com.saitama.transportation.mobile.android.server.facade.impl;

import com.saitama.transportation.mobile.android.server.facade.BaseFacade;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;

import java.util.Map;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class AuthFacade extends BaseFacade {

    public AuthFacade(String baseUrl, TokenKeeper tokenKeeper) {
        super(baseUrl, tokenKeeper);
    }

    public void login(String email, String password, FacadeCallback<AuthResponse> callback) {
        Map<String, String> params = new ParamsBuilder("email", email)
                .addValue("password", password)
                .build();
        apiFormPost("auth", params, AuthResponse.class, callback, true);
    }

    public void register(String email, String password, FacadeCallback<AuthResponse> callback) {
        Map<String, String> params = new ParamsBuilder("email", email)
                .addValue("password", password)
                .build();
        apiFormPost("register", params, AuthResponse.class, callback, true);
    }
}
