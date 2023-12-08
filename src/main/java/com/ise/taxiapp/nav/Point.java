package com.ise.taxiapp.nav;

import com.ise.taxiapp.dataStructures.LinkedList;

import java.util.function.Predicate;

public class Point implements Location {
    private final int x;
    private final int y;
    LinkedList<Object> objects;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        objects = new LinkedList<>();
    }

    public static Point fromIndex(int index, int gridWidth) {
        return new Point(index % gridWidth, index / gridWidth);
    }

    public LinkedList<Object> getObjects() {
        return objects;
    }

    @Override
    public double distanceTo(Location other) {
        // This also accounts for other being null
        if (!(other instanceof Point point)) {
            throw new IllegalArgumentException("Argument must be of type Point");
        }
        if (this.equals(other)) return 0;
        // Pythagoras
        return Math.sqrt(Math.pow((point.x() - this.x), 2)
                + Math.pow((point.y() - this.y), 2));
    }

    public Object findMatch(Predicate<Object> p) {
        return objects.stream().filter(p).findAny().orElse(null);
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
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public String toString() {
        return "Point(%d, %d)".formatted(x, y);
    }

}
