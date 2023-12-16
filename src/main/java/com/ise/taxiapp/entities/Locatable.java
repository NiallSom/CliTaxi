package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

/**
 * Interface allowing an object to be placed and displayed on a grid.
 */
public interface Locatable {

    /**
     * Returns the location of the object on the grid.
     *
     * @return The object's current location
     */
    Location getLocation();

    /**
     * Sets the location of the object on the grid.
     *
     * @param location The new location of the object
     */
    void setLocation(Location location);
}
