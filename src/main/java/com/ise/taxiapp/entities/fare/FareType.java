package com.ise.taxiapp.entities.fare;

public enum FareType {
    STANDARDFARE(5, 4.5),
    EXTRALARGEFARE(6, 6),
    EXPRESSFARE(10, 4);

    final double initial;
    final double meter;

    FareType(double initial, double meter) {
        this.initial = initial;
        this.meter = meter;
    }
}
