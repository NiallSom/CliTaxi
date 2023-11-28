package com.ise.taxiapp.entities;

import com.ise.taxiapp.UI;
import com.ise.taxiapp.entities.fare.Fare;
import com.ise.taxiapp.nav.Location;

public class Taxi{
    String reg;
    Fare fare;
    Location currentLocation;
    Driver driver;
    User user;
    UI ui;

    public Taxi(String reg, Driver driver,Fare fare){
        this.reg = reg;
        this.driver = driver;
        this.fare = fare;
    }
    public void driveTo(Location location){
        ui.informOnRoute();
    }
}
