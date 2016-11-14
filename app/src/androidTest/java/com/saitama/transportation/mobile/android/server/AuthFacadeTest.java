package com.saitama.transportation.mobile.android.server;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.TestConstants;
import com.saitama.transportation.mobile.android.core.PreferenceTokenKeeperImpl;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.FacadeException;
import com.saitama.transportation.mobile.android.server.facade.impl.AuthFacade;

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
public class AuthFacadeTest {
    @Mock
    TokenKeeper mTokenKeeper;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testSuccessLogin() throws Exception {
        when(mTokenKeeper.getToken()).thenReturn("");
        AuthFacade authFacade = new AuthFacade(Constants.SERVER_ADDRESS, mTokenKeeper);
        authFacade.login(TestConstants.TEST_EMAIL, TestConstants.TEST_PASSWORD, new FacadeCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse response) {
                assertTrue(!TextUtils.isEmpty(response.getAccessToken()));
            }

            @Override
            public void onError(Throwable th) {
                fail();
            }
        });
    }

    @Test
    public void testSuccessRegister() throws Exception {
        when(mTokenKeeper.getToken()).thenReturn("");
        AuthFacade authFacade = new AuthFacade(Constants.SERVER_ADDRESS, mTokenKeeper);
        authFacade.register(TestConstants.TEST_EMAIL, TestConstants.TEST_PASSWORD, new FacadeCallback<AuthResponse>() {
            @Override
            public void onSuccess(AuthResponse response) {
                assertTrue(!TextUtils.isEmpty(response.getAccessToken()));
            }

            @Override
            public void onError(Throwable th) {
                fail();
            }
        });
    }
}
