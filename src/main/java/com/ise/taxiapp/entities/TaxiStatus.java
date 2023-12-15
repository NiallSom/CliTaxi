package com.ise.taxiapp.entities;

/**
 * Enum describing the status of a taxi.
 * Only available taxis should be considered when searching for a lift.
 */
public enum TaxiStatus {
    AVAILABLE(),
    EN_ROUTE(),
    BUSY();
}
