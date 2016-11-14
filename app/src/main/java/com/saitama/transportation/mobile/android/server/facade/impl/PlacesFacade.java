package com.saitama.transportation.mobile.android.server.facade.impl;

import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.server.entity.PlacesResponse;
import com.saitama.transportation.mobile.android.server.entity.RentalPlace;
import com.saitama.transportation.mobile.android.server.facade.BaseFacade;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.entity.AuthResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class PlacesFacade extends BaseFacade {

    public PlacesFacade(String baseUrl, TokenKeeper tokenKeeper) {
        super(baseUrl, tokenKeeper);
    }

    public void places(FacadeCallback<PlacesResponse> callback) {
        apiGet("places", null, PlacesResponse.class, callback, true, true);
    }
}
