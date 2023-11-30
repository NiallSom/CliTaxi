package com.ise.taxiapp.nav;

public class Point implements Location{
    private final int x;
    private final int y;
    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public Region getRegion() {
        return null;
    }

    @Override
    public double distanceTo(Location userLocation) {
        if (!(userLocation instanceof Point temp)){
            throw new IllegalArgumentException("Argument must be of type Point");
        }
        return Math.sqrt(Math.pow((temp.getX() - this.x),2) + Math.pow((temp.getY() - this.y),2));
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
