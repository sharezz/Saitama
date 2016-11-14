package com.saitama.transportation.mobile.android.server;

import android.support.test.runner.AndroidJUnit4;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.TestConstants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.PaymentResponse;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.impl.PaymentFacade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by sharezzorama on 10/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class PaymentFacadeTest {
    @Mock
    TokenKeeper mTokenKeeper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSuccessPayment() throws Exception {
        when(mTokenKeeper.getToken()).thenReturn(TestConstants.TEST_TOKEN);

        PaymentFacade placesFacade = new PaymentFacade(Constants.SERVER_ADDRESS, mTokenKeeper);
        String name = "Name";
        String number = "1234567812345678";
        String code = "123";
        String expiration = "12/13";
        placesFacade.rent(name, number, expiration, code, new FacadeCallback<PaymentResponse>() {
            @Override
            public void onSuccess(PaymentResponse response) {
                assertEquals(response.getMessage().toLowerCase(), "Rent operation has been completed".toLowerCase());
            }

            @Override
            public void onError(Throwable th) {
                fail();
            }
        });
    }

}
