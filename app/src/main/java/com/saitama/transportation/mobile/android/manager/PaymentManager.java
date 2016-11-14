package com.saitama.transportation.mobile.android.manager;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.event.AuthEvent;
import com.saitama.transportation.mobile.android.event.PaymentEvent;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;
import com.saitama.transportation.mobile.android.server.entity.PaymentResponse;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.impl.AuthFacade;
import com.saitama.transportation.mobile.android.server.facade.impl.PaymentFacade;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sharezzorama on 10/26/16.
 * The manager contains methods for rent payment
 */

public class PaymentManager extends BaseManager {
    private PaymentFacade mPaymentFacade;

    public PaymentManager(TokenKeeper tokenKeeper) {
        super(tokenKeeper);
        mPaymentFacade = new PaymentFacade(Constants.SERVER_ADDRESS, tokenKeeper);
    }

    /**
     * Sends the rest payment request to server
     * @param name - name on credit card
     * @param number - number of credit card
     * @param expiration - credit card expiration date
     * @param code - credit card validation code
     */
    public void rent(String name, String number, String expiration, String code) {
        mPaymentFacade.rent(name, number, expiration, code, new FacadeCallback<PaymentResponse>() {
            @Override
            public void onSuccess(PaymentResponse response) {
                EventBus.getDefault().post(new PaymentEvent(response.getMessage()));
            }

            @Override
            public void onError(Throwable th) {
                EventBus.getDefault().post(new PaymentEvent((Exception) th));
            }
        });
    }
}
