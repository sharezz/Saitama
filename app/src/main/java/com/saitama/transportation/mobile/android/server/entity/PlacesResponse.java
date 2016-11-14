package com.saitama.transportation.mobile.android.server.entity;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class PlacesResponse {
    private List<RentalPlace> results;

    public List<RentalPlace> getResults() {
        return results;
    }

    public void setResults(List<RentalPlace> results) {
        this.results = results;
    }
}
