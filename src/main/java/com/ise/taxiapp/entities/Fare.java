package com.ise.taxiapp.entities;

public enum Fare {
    STANDARD_FARE(5, 4.5),
    EXTRA_LARGE_FARE(6, 6),
    EXPRESS_FARE(10, 4);

    final private double initial;
    final private double meter;

    Fare(double initial, double meter) {
        this.initial = initial;
        this.meter = meter;
    }

    public double calculateCharge(double distance) {
        return (distance * meter) + initial;
    }

}
