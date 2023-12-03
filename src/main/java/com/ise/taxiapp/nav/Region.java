package com.ise.taxiapp.nav;

import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Region {
    protected List<Taxi> taxiList;

    Region() {
        taxiList = new LinkedList<>();
    }

    public void insertTaxi(Taxi taxi) {
        taxiList.add(taxi);
    }

    public void removeTaxi(Taxi taxi) {
        taxiList.remove(taxi);
    }

    /**
     * Finds the nearest taxi to the given location for the given fare.
     * Returns null if no taxis are available in the given radius.
     *
     * @param location The location where the taxi must travel to
     * @param fare     The fare to filter by when finding a taxi
     * @param radiusKm The radius around the location where a taxi is marked as valid.
     * @return The nearest taxi to the location, or null if no taxi is available
     */
    public Taxi callTaxi(Location location, Fare fare, int radiusKm) {
        return taxiList
                .stream()
                .filter(taxi -> taxi.getLocation().distanceTo(location) <= radiusKm)
                .filter(Taxi::isAvailable)
                .filter(taxi -> taxi.getFare().equals(fare))
                .min(Comparator.comparing(taxi -> taxi.getLocation().distanceTo(location)))
                .orElse(null);
    }
}
