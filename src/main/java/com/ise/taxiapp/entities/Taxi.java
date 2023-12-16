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

    /**
     * returns the user class
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * sets the passenger of the taxi
     * @param user passenger
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * returns the destination of the taxi
     * @return destination
     */
    public Location getDestination() {
        return destination;
    }

    /**
     * sets the destination of the taxi
     * @param destination destination of taxi
     */
    public void setDestination(Location destination) {
        this.destination = destination;
    }

    /**
     * returns the fare
     * @return fare
     */
    public Fare getFare() {
        return fare;
    }

    /**
     * sets the fare of the taxi
     * @param fare the type of taxi STANDARD , XL , EXPRESS
     */
    public void setFare(Fare fare) {
        this.fare = fare;
    }

    /**
     * Drives the taxi to a destination
     */
    public void driveToDestination() {
        distanceTravelled = currentLocation.distanceTo(destination);
        this.currentLocation = destination;
    }

    /**
     * returns the time to a destination
     * @return time to destination
     */
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

    /**
     * returns the taxis location
     * @return taxi current location
     */
    public Location getLocation() {
        return currentLocation;
    }

    /**
     * Sets the location of the taxi
     * @param currentLocation sets taxis current location
     */
    public void setLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * returns the driver of the taxi
     * @return driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * returns the registration number of the taxi
     * @return registration
     */
    public String getReg() {
        return reg;
    }
    
    public void markAsAvailable() {
        distanceTravelled = 0;
        user = null;
        status = TaxiStatus.AVAILABLE;
    }

    /**
     * returns the taxis current status
     * @return status
     */
    public TaxiStatus getStatus() {
        return status;
    }

    /**
     * sets the taxi status
     * @param status sets the taxis availability status
     */
    public void setStatus(TaxiStatus status) {
        this.status = status;
    }

    public String toString() {
        return String.format("Taxi[%s]", reg);
    }
}
