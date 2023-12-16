package com.ise.taxiapp.entities;

import java.util.LinkedList;
import java.util.List;


/**
 * Class for representing the driver of a taxi.
 */
public class Driver {
    private final String name;
    private final String id;
    private final List<Integer> ratings;

    /**
     * Creates a new driver.
     * @param name The name of the driver
     * @param id The driving licence id
     */
    public Driver(String name, String id) {
        this.name = name;
        this.id = id;
        this.ratings = new LinkedList<>();
    }

    /**
     * Allows the user to rate the driver.
     *
     * @param rating Numeric rating from 0-5
     */
    public void rate(int rating) {
        this.ratings.add(rating);
    }

    /**
     * returns the average of all ratings on the driver
     *
     * @return Average rating of the driver
     */
    public double getRating() {
        return ratings.stream().mapToDouble(a -> a).average().orElse(0.0);
    }

    /**
     * Returns the name of the driver.
     *
     * @return The name of the driver
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the drivers id.
     *
     * @return The id of the driver
     */
    public String getId() {
        return id;
    }

    public String toString() {
        return name;
    }
}
