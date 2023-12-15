package com.ise.taxiapp.nav;

import com.ise.taxiapp.dataStructures.LinkedList;
import com.ise.taxiapp.entities.Locatable;

import java.util.function.Predicate;

/**
 * A point represents a location in a 2-dimensional coordinate system.
 * Each point keeps track of all objects contained within it.
 */
public class Point implements Location {
    private final int x;
    private final int y;

    /**
     * List of items at the current point in space.
     */
    LinkedList<Locatable> objects;

    /**
     * Constructs a Point at the specified (x, y) location.
     *
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        objects = new LinkedList<>();
    }

    /**
     * Factory method that creates a Point using an index value.
     * It assumes the point is inside a grid of a specified width.
     *
     * @param index     The index within the grid.
     * @param gridWidth The width of the grid.
     * @return The corresponding Point object.
     */
    public static Point fromIndex(int index, int gridWidth) {
        return new Point(index % gridWidth, index / gridWidth);
    }

    /**
     * Getter method for retrieving the objects linked to the Point.
     *
     * @return The LinkedList of objects associated with the Point.
     */
    public LinkedList<Locatable> getObjects() {
        return objects;
    }

    /**
     * Calculates and returns the Manhattan distance from this Point to another Location.
     * It throws an IllegalArgumentException if the other Location isn't a Point instance.
     *
     * @param other The Point to measure distance to
     * @return The distance between this Point and the other Location.
     */
    @Override
    public double distanceTo(Location other) {
        if (!(other instanceof Point point)) {
            throw new IllegalArgumentException("Argument must be of type Point");
        }
        if (this.equals(other)) return 0;
        // Uses Manhattan distance
        return Math.abs(x - point.x()) + Math.abs(y - point.y());
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point) obj;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Returns a string representation of this Point object in the format "Point(x, y)".
     *
     * @return The string representation of this Point object.
     */
    @Override
    public String toString() {
        return "Point(%d, %d)".formatted(x, y);
    }
}