package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

public class Taxi {
    private final String reg;
    private final Driver driver;
    private Fare fare;
    private Location currentLocation;
    private User user;
    private Location destination;
    private double distanceTravelled;
    private boolean isAvailable;

    public Taxi(String reg, Driver driver, Fare fare) {
        this.reg = reg;
        this.driver = driver;
        this.fare = fare;
        isAvailable = true;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void driveToDestination() {
        distanceTravelled = currentLocation.distanceTo(destination);
        this.currentLocation = destination;
    }

    public long timeToDestination() {
        return (long) currentLocation.distanceTo(destination);
    }

    public double calculateCharge() {
        return fare.calculateCharge(distanceTravelled);
    }

    public Location getLocation() {
        return currentLocation;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getReg() {
        return reg;
    }

    public void markAsAvailable() {
        distanceTravelled = 0;
        user = null;
        isAvailable = true;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
