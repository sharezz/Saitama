package com.saitama.transportation.mobile.android.event;

import com.saitama.transportation.mobile.android.server.entity.RentalPlace;

import java.util.List;

/**
 * Created by sharezzorama on 10/26/16.
 * Posts when rental places was successfully loaded or failed
 */

public class PlacesLoadEvent extends BaseEvent<List<RentalPlace>> {

    public PlacesLoadEvent(List<RentalPlace> data) {
        super(data);
    }

    public PlacesLoadEvent(Exception exception) {
        super(exception);
    }
}
