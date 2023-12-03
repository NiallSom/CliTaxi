package com.ise.taxiapp.nav;

public class Point implements Location {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromIndex(int index, int gridWidth) {
        return new Point(index % gridWidth, index / gridWidth);
    }

    @Override
    public double distanceTo(Location other) {
        if (!(other instanceof Point point)) {
            throw new IllegalArgumentException("Argument must be of type Point");
        }
        // Pythagoras
        return Math.sqrt(Math.pow((point.getX() - this.x), 2)
                + Math.pow((point.getY() - this.y), 2));
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
