package com.ise.taxiapp.entities;

public enum Fare {
    STANDARD_FARE(5, 4.5),
    EXTRA_LARGE_FARE(6, 6),
    EXPRESS_FARE(10, 4);

    final double initial;
    final double meter;

    Fare(double initial, double meter) {
        this.initial = initial;
        this.meter = meter;
    }

    public double calculateFare(double distance) {
        return (distance * meter) + initial;
    }
}
