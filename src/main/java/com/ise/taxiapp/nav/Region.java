package com.ise.taxiapp.nav;

import com.ise.taxiapp.dataStructures.LinkedList;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;

public abstract class Region {
    protected LinkedList<Taxi> taxiList;

    protected Region() {
        taxiList = new LinkedList<>();
    }

    public boolean insertTaxi(Taxi taxi) {
        if (taxiList.contains(taxi)) {
            return false;
        }
        taxiList.add(taxi);
        return true;
    }

    public boolean removeTaxi(Taxi taxi) {
        return taxiList.remove(taxi);
    }

    public int taxiCount() {
        return taxiList.size();
    }

    /**
     * Required to make Salah's testing interface work.
     *
     * @param reg The registration plate of the taxi
     * @return The taxi with the given reg, or null if it's not on the map
     */
    public Taxi fromReg(String reg) {
        return taxiList.stream()
                .filter(taxi -> taxi.getReg().equals(reg))
                .findAny()
                .orElse(null);
    }

    /**
     * Finds the nearest taxi to the given location for the given fare.
     * If no taxi is currently available, this will place the user in a queue.
     *
     * @param location The location where the taxi must travel to
     * @param fare     The fare to filter by when finding a taxi
     * @param radiusKm The radius around the location where a taxi is marked as valid.
     * @return The nearest taxi to the location, or null if no taxi is available
     */
    public abstract Taxi callTaxi(Location location, Fare fare, int radiusKm);
}
