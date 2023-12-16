package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

import java.util.Objects;

/**
 * Class representing the passenger of a taxi.
 */
public class User implements Locatable {
    private final String username;
    private Location currentLocation;
    private double balance;

    /**
     * Create a user with the given username.
     *
     * @param username The username of the user
     */
    public User(String username) {
        this.username = username;
        this.balance = 20.0;
    }

    /**
     * charges the cost of the journey from the users account
     *
     * @param charge the cost of the journey
     */
    public void charge(double charge) {
        balance -= charge;
    }

    /**
     * returns the users username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the users balance
     *
     * @return account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the location of the object on the grid.
     *
     * @return The object's current location
     */
    @Override
    public Location getLocation() {
        return currentLocation;
    }

    /**
     * Sets the location of the object on the grid.
     *
     * @param location The new location of the object
     */
    @Override
    public void setLocation(Location location) {
        this.currentLocation = location;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        User user = (User) other;
        return Objects.equals(username, user.username);
    }
}
