package com.ise.taxiapp.entities;

import com.ise.taxiapp.UI;
import com.ise.taxiapp.entities.fare.Fare;
import com.ise.taxiapp.nav.Location;

public class Taxi{
    private String reg;
    private Fare fare;
    private Location currentLocation;
    private Driver driver;
    private User user;
    private UI ui;

    public Taxi(String reg, Driver driver,Fare fare){
        this.reg = reg;
        this.driver = driver;
        this.fare = fare;
    }
    public void driveTo(Location location){
        ui.informOnRoute();
    }
    public void driveTo(Location location, Fare fare){
        return;//implement 2nd drive to method
    }
    public void pickup(User user){
        return; // implement pickup method
    }
    public void task(User user, Location destination){
        driveTo(user.currentLocation);
        pickup(user);
        driveTo(destination, this.fare);
        user.charge(this.fare, user.currentLocation.distanceTo(destination));
    }
    public Location getLocation(){
        return currentLocation;
    }

}
