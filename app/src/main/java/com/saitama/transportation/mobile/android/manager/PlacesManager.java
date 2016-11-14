package com.saitama.transportation.mobile.android.manager;

import com.saitama.transportation.mobile.android.Constants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.saitama.transportation.mobile.android.event.AuthEvent;
import com.saitama.transportation.mobile.android.event.PlacesLoadEvent;
import com.saitama.transportation.mobile.android.server.entity.PlacesResponse;
import com.saitama.transportation.mobile.android.server.facade.FacadeCallback;
import com.saitama.transportation.mobile.android.server.facade.impl.PlacesFacade;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sharezzorama on 10/26/16.
 * The manager contains methods for getting rental places
 */

public class PlacesManager extends BaseManager {

    private final PlacesFacade mPlacesFacade;

    public PlacesManager(TokenKeeper tokenKeeper) {
        super(tokenKeeper);
        mPlacesFacade = new PlacesFacade(Constants.SERVER_ADDRESS, tokenKeeper);
    }

    /**
     * Loads all available rental places from server
     */
    public void loadPlaces(){
        mPlacesFacade.places(new FacadeCallback<PlacesResponse>() {
            @Override
            public void onSuccess(PlacesResponse response) {
                EventBus.getDefault().post(new PlacesLoadEvent(response.getResults()));
            }

            @Override
            public void onError(Throwable th) {
                EventBus.getDefault().post(new PlacesLoadEvent((Exception) th));
            }
        });
    }
}
