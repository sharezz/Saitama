package com.saitama.transportation.mobile.android.server.entity;

import java.io.Serializable;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class RentalPlace implements Serializable {
    private Location location;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
