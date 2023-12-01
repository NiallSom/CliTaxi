package com.ise.taxiapp.entities;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    String name;
    String id;
    String[] contactDetails;
    List<Integer> ratings;
    public Driver(String name) {
        this.name = name;
        this.ratings = new ArrayList<>();
    }
    public void rateTaxi(int rating){
        this.ratings.add(rating);
    }
    public double getRating(){
        double total = 0;
        for (int rating: ratings){
            total += rating;
        }
        return total / ratings.size();
    }
}
