package com.ise.taxiapp.entities.fare;

public class Fare {
    public double initial;
    public double meter;
    public Fare(FareType fareType){
        this.initial = fareType.initial;
        this.meter = fareType.meter;
    }

    public double calculateFare(double distance) {
        return (distance*this.meter)+this.initial;
    }
}