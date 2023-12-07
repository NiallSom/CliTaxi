package com.ise.taxiapp.nav.grid;

import com.ise.taxiapp.nav.Location;

public record Point(int x, int y) implements Location {

    public static Point fromIndex(int index, int gridWidth) {
        return new Point(index % gridWidth, index / gridWidth);
    }

    @Override
    public double distanceTo(Location other) {
        // This also accounts for other being null
        if (!(other instanceof Point point)) {
            throw new IllegalArgumentException("Argument must be of type Point");
        }
        // Pythagoras
        return Math.sqrt(Math.pow((point.x() - this.x), 2)
                + Math.pow((point.y() - this.y), 2));
    }
}
