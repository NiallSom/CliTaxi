package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

import java.util.Objects;

public class User implements Locatable {
    private final String username;
    private Location currentLocation;
    private double balance;

    public User(String username) {
        this.username = username;
        this.balance = 20.0;
    }

    public void charge(double charge) {
        balance -= charge;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

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
