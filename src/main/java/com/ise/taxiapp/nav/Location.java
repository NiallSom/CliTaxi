package com.ise.taxiapp.nav;

/**
 * Interface representing a location in a Region.
 */
public interface Location {
    /**
     * Calculates the distance to the given Location.
     *
     * @param other The Location to calculate the distance to
     * @return The distance to other
     */
    double distanceTo(Location other);
}
