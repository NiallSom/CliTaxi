package com.ise.taxiapp.nav;

import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;

import java.util.Comparator;

public class Grid extends Region {

    private final int width;
    private final int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Finds the nearest taxi to the given location for the given fare.
     * May return null if no taxis are available.
     *
     * @param location The location where the taxi must travel to
     * @param fare     The fare to filter by when finding a taxi
     * @return The nearest taxi to the location, or null if no taxi is available
     */
    @Override
    public Taxi findNearestTaxi(Location location, Fare fare) {
        return taxiList
                .stream()
                .filter(taxi -> taxi.getFare().equals(fare))
                .min(Comparator.comparing(taxi -> taxi.getLocation().distanceTo(location)))
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("%d x %d Grid", width, height);
    }
}
