package com.saitama.transportation.mobile.android.event;

/**
 * Created by sharezzorama on 10/26/16.
 * Posts when payment success or fail
 */

public class PaymentEvent extends BaseEvent<String> {
    public PaymentEvent(String data) {
        super(data);
    }

    public PaymentEvent(Exception exception) {
        super(exception);
    }
}
