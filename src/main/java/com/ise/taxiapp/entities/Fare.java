package com.ise.taxiapp.entities;

import static com.ise.taxiapp.cli.AsciiColours.*;

public enum Fare {
    STANDARD_FARE(5, 4.5, CYAN),
    EXTRA_LARGE_FARE(6, 6, YELLOW),
    EXPRESS_FARE(10, 4, GREEN);


    final private double initial;
    final private double meter;
    final private String colour;

    Fare(double initial, double meter, String colour) {
        this.initial = initial;
        this.meter = meter;
        this.colour = colour;
    }

    public double calculateCharge(double distance) {
        return (distance * meter) + initial;
    }

    public String getColour() {
        return colour;
    }

}
