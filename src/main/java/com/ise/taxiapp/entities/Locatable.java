package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

public interface Locatable {
    Location getLocation();

    void setLocation(Location location);
}
