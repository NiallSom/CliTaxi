package com.ise.taxiapp.entities;

import com.ise.taxiapp.entities.fare.Fare;
import com.ise.taxiapp.nav.Location;

import java.util.List;

public class User {
    String username;
    List<Ride> rideHistory;
    Location currentLocation;
    double balance;
    public User(String username){
        this.username = username;
    }
    public void callTaxi(Location destination, Fare fare) {
        Taxi taxi = destination.getRegion().findNearest(fare);
        taxi.task(this, destination);
    }
}
