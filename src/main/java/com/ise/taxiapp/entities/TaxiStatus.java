package com.ise.taxiapp.entities;

/**
 * Enum describing the status of a taxi.
 * Only available taxis should be considered when searching for a lift.
 */
public enum TaxiStatus {
    /**
     * The taxi is available to pick up passengers.
     */
    AVAILABLE(),
    /**
     * The taxi is on the way to pick up a passenger.
     */
    EN_ROUTE(),
    /**
     * The taxi is currently transporting a passenger to their destination.
     */
    BUSY()
}
