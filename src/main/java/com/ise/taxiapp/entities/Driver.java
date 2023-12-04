package com.ise.taxiapp.entities;

import java.util.LinkedList;
import java.util.List;

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

    public void rate(int rating) {
        this.ratings.add(rating);
    }

    public double getRating() {
        return ratings.stream().mapToDouble(a -> a).average().orElse(0.0);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String[] getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String[] contactDetails) {
        this.contactDetails = contactDetails;
    }
}
