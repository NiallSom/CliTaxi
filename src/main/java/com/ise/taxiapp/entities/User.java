package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

import java.util.LinkedList;
import java.util.List;

public class User {
    private final String username;
    private final List<Ride> rideHistory;
    private Location currentLocation;
    private double balance;

    public User(String username) {
        this.username = username;
        rideHistory = new LinkedList<>();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void charge(double charge) {
        balance -= charge;
    }

    public String getUsername() {
        return username;
    }

    public List<Ride> getRideHistory() {
        return rideHistory;
    }
}
