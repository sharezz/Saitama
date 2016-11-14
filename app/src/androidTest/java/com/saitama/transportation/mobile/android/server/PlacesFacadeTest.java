package com.saitama.transportation.mobile.android.server;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.TestConstants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.PlacesResponse;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.impl.PlacesFacade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Created by sharezzorama on 10/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class PlacesFacadeTest {

    @Mock
    TokenKeeper mTokenKeeper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSuccessLoadPlaces() throws Exception {
        when(mTokenKeeper.getToken()).thenReturn(TestConstants.TEST_TOKEN);

        PlacesFacade placesFacade = new PlacesFacade(Constants.SERVER_ADDRESS, mTokenKeeper);
        placesFacade.places(new FacadeCallback<PlacesResponse>() {
            @Override
            public void onSuccess(PlacesResponse response) {
                assert (true);
            }

            @Override
            public void onError(Throwable th) {
                fail();
            }
        });
    }
}
