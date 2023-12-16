package com.ise.taxiapp.entities;

import static com.ise.taxiapp.cli.AsciiColours.*;

/**
 * Enum representing different types of fare.
 * Each fare calculates the amount to charge the user differently.
 */
public enum Fare {
    /**
     * The default fare for a taxi. Standard pricing.
     */
    STANDARD_FARE(5, 4.5, YELLOW),
    /**
     * Extra large taxi. Large callout fee, cheaper meter.
     */
    EXPRESS_FARE(10, 4, GREEN),
    /**
     * Express taxi. Slightly larger fare, larger meter.
     */
    EXTRA_LARGE_FARE(6, 6, BLUE);


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
     * @param distance The distance travelled by the taxi, used in calculating the charge
     * @return The amount to charge the user
     */
    public double calculateCharge(double distance) {
        return (distance * meter) + initial;
    }

    /**
     * Returns the colour to display a taxi of this fare with.
     *
     * @return ASCII colour String
     */
    public String getColour() {
        return colour;
    }

}
