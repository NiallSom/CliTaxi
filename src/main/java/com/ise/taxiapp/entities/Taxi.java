package com.ise.taxiapp.entities;

import com.ise.taxiapp.nav.Location;

public class Taxi implements Locatable {
    private final String reg;
    private final Driver driver;
    private Fare fare;
    private Location currentLocation;
    private User user;
    private Location destination;
    private double distanceTravelled;
    private TaxiStatus status;

    public Taxi(String reg, Driver driver, Fare fare) {
        this.reg = reg;
        this.driver = driver;
        this.fare = fare;
        this.status = TaxiStatus.AVAILABLE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public void driveToDestination() {
        distanceTravelled = currentLocation.distanceTo(destination);
        this.currentLocation = destination;
    }

    public long timeToDestination() {
        return (long) currentLocation.distanceTo(destination);
    }

    /**
     * Calculates the charge for the user based on distance travelled and fare applied.
     *
     * @return The amount to charge the user
     */
    public double calculateCharge() {
        return fare.calculateCharge(distanceTravelled);
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
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
        status = TaxiStatus.AVAILABLE;
    }

    public TaxiStatus getStatus() {
        return status;
    }

    public void setStatus(TaxiStatus status) {
        this.status = status;
    }

    public String toString() {
        return String.format("Taxi[%s]", reg);
    }
}
