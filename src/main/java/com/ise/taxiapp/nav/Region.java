package com.ise.taxiapp.nav;

import com.ise.taxiapp.dataStructures.LinkedList;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;

/**
 * Class representing a geographical area where the taxi company operates.
 * Example implementations may include a grid or a graph.
 */
public abstract class Region {
    /**
     * List of all taxis in the region, available or otherwise.
     */
    protected LinkedList<Taxi> taxiList;

    /**
     * stores the taxi within a region in a linked list
     */
    protected Region() {
        taxiList = new LinkedList<>();
    }

    /**
     * returns the linked list of Taxis
     *
     * @return Taxi linked list
     */
    public LinkedList<Taxi> getTaxiList() {
        return taxiList;
    }

    /**
     * Inserts taxi into linked list if the list does not already contain that taxi
     *
     * @param taxi The taxi to insert
     * @return If the taxi was successfully added
     */
    public boolean insertTaxi(Taxi taxi) {
        if (taxiList.contains(taxi)) {
            return false;
        }
        taxiList.add(taxi);
        return true;
    }

    /**
     * removes taxi from taxi linked list
     *
     * @param taxi The taxi to remove
     * @return If the taxi was successfully removed
     */
    public boolean removeTaxi(Taxi taxi) {
        return taxiList.remove(taxi);
    }

    /**
     * returns the amount of taxis within the region
     *
     * @return amount of taxis
     */
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
