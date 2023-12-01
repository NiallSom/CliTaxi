package com.ise.taxiapp.nav;

import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Taxi;

import java.util.LinkedList;
import java.util.List;

public abstract class Region {
    protected List<Taxi> taxiList;

    Region() {
        taxiList = new LinkedList<>();
    }

    public void insertTaxi(Taxi taxi) {
        taxiList.add(taxi);
    }

    public void removeTaxi(Taxi taxi) {
        taxiList.remove(taxi);
    }

    public abstract Taxi findNearestTaxi(Location other, Fare fare);
}
