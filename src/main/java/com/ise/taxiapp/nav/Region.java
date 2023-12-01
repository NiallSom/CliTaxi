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
     * May return null if no taxis are available.
     *
     * @param location The location where the taxi must travel to
     * @param fare     The fare to filter by when finding a taxi
     * @return The nearest taxi to the location, or null if no taxi is available
     */
    public Taxi findNearestTaxi(Location location, Fare fare) {
        return taxiList
                .stream()
                .filter(taxi -> taxi.getFare().equals(fare))
                .min(Comparator.comparing(taxi -> taxi.getLocation().distanceTo(location)))
                .orElse(null);
    }
}
