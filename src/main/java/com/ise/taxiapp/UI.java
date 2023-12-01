package com.ise.taxiapp;

import com.ise.taxiapp.entities.Driver;

public interface UI {

    void informOnRoute();

    void informArrived();

    void informCharge(double charge);

    void informTripComplete();

    void rateTaxi(Driver driver);
}
