package com.ise.taxiapp.entities;

import java.util.LinkedList;
import java.util.List;


/**
 * Class for creating the diver
 */
public class Driver {
    private final String name;
    private final String id;
    private final List<Integer> ratings;
    private String[] contactDetails;

    public Driver(String name, String id) {
        this.name = name;
        this.id = id;
        this.ratings = new LinkedList<>();
    }

    /**
     * Allows the user to rate the driver
     * @param rating
     */
    public void rate(int rating) {
        this.ratings.add(rating);
    }

    /**
     * returns the average of all ratings on the driver
     * @return average rating
     */
    public double getRating() {
        return ratings.stream().mapToDouble(a -> a).average().orElse(0.0);
    }

    /**
     * returns the name of the driver
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the drivers id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return contact details
     */
    public String[] getContactDetails() {
        return contactDetails;
    }

    /**
     * sets contact details of driver
     * @param contactDetails
     */
    public void setContactDetails(String[] contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String toString() {
        return name;
    }
}
