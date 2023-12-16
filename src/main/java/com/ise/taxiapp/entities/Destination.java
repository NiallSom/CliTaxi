package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

/**
 * Class representing the destination that the user wishes to travel to.
 * Allows destinations to be printed on the grid.
 */
public class Destination implements Locatable {

    /**
     * Where the destination is located.
     */
    Location location;

    /**
     * Creates a new destination at the given Location.
     *
     * @param location The location to place the destination at
     */
    public Destination(Location location) {
        this.location = location;
    }

    /**
     * Returns the location of the object on the grid.
     *
     * @return The object's current location
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the object on the grid.
     *
     * @param location The new location of the object
     */
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
