package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

public class Destination implements Locatable {

    Location location;

    public Destination(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
