package com.ise.taxiapp.entities;

import static com.ise.taxiapp.cli.AsciiColours.*;

/**
 * Enum representing different types of fare.
 * Each fare calculates the amount to charge the user differently.
 */
public enum Fare {
    STANDARD_FARE(5, 4.5, YELLOW),
    EXTRA_LARGE_FARE(6, 6, BLUE),
    EXPRESS_FARE(10, 4, GREEN);


    final private double initial;
    final private double meter;
    final private String colour;

    Fare(double initial, double meter, String colour) {
        this.initial = initial;
        this.meter = meter;
        this.colour = colour;
    }

    /**
     * Calculates the charge for the user based on distance travelled and fare applied.
     *
     * @return The amount to charge the user
     */
    public double calculateCharge(double distance) {
        return (distance * meter) + initial;
    }

    public String getColour() {
        return colour;
    }

}
