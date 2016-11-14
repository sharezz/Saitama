package com.saitama.transportation.mobile.android.server.facade.impl;

import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;
import com.saitama.transportation.mobile.android.server.entity.PaymentResponse;
import com.saitama.transportation.mobile.android.server.facade.BaseFacade;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;

import java.util.Map;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class PaymentFacade extends BaseFacade {

    public PaymentFacade(String baseUrl, TokenKeeper tokenKeeper) {
        super(baseUrl, tokenKeeper);
    }

    public void rent(String name, String number, String expiration, String code, FacadeCallback<PaymentResponse> callback) {
        Map<String, String> params = new ParamsBuilder("name", name)
                .addValue("number", number)
                .addValue("expiration", expiration)
                .addValue("code", code)
                .build();
        apiFormPost("rent", params, PaymentResponse.class, callback, true);
    }
}
