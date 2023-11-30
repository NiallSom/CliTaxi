package com.ise.taxiapp.nav;

public interface Location {
    Region getRegion();
    double distanceTo(Location user);
}
