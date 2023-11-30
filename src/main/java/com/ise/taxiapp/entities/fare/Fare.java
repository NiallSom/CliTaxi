package com.ise.taxiapp.entities.fare;

public class Fare {
    public double initial;
    public double meter;
    public Fare(FareType type){
        switch (type){
            case EXPRESSFARE -> {
                this.initial = 5.0;
                this.meter = 5.0;
            }
            case STANDARDFARE -> {
                this.initial = 3.0;
                this.meter = 3.5;
            }
            case EXTRALARGEFARE -> {
                this.initial = 4.0;
                this.meter = 4.5;
            }
        }
    }

    public double calculateFare(double distance) {
        return (distance*this.meter)+this.initial;
    }
}