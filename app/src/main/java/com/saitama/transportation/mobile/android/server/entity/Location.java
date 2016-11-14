package com.saitama.transportation.mobile.android.server.entity;

import java.io.Serializable;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class Location implements Serializable {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
