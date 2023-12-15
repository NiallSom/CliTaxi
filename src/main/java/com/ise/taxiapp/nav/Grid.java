package com.ise.taxiapp.nav;

import com.ise.taxiapp.dataStructures.LinkedList;
import com.ise.taxiapp.dataStructures.List;
import com.ise.taxiapp.entities.Fare;
import com.ise.taxiapp.entities.Locatable;
import com.ise.taxiapp.entities.Taxi;
import com.ise.taxiapp.entities.TaxiStatus;

import java.util.Random;
import java.util.function.Predicate;

/**
 * Represents a grid of points in a two-dimensional space.
 * Each point in the grid has an x and y coordinate.
 * The grid can be used to find the nearest point to a given point within a certain radius.
 */
public class Grid extends Region {
    private final int width;
    private final int height;
    private final Point[] points;
    private final Random random;

    /**
     * Constructs a grid with the specified width and height.
     *
     * @param width  the width of the grid
     * @param height the height of the grid
     */
    public Grid(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        points = new Point[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                points[x + y * width] = new Point(x, y);
            }
        }
        random = new Random();
    }

    /**
     * Returns the width of the grid.
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the grid.
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the point at the specified coordinate in this grid.
     *
     * @param x the x coordinate of the point on the grid to retrieve
     * @param y the y coordinate of the point on the grid to retrieve
     * @return the point at the specified coordinate
     * @throws IndexOutOfBoundsException if the x or y coordinates are outside the range of the grid dimensions
     */
    public Point get(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IndexOutOfBoundsException();
        return points[x + height * y];
    }

    /**
     * Returns a string representation of the Grid object.
     *
     * @return a string consisting of the width and height of the grid
     */
    @Override
    public String toString() {
        return String.format("%d x %d Grid", width, height);
    }

    /**
     * Find and return the nearest object that satisfies the provided predicate within the radius.
     * Returns null if no acceptable object is found.
     *
     * @param origin    the starting point for the search
     * @param predicate the predicate to apply to each object as it's found
     * @param radius    the radius within which to search for an object
     * @return the first acceptable object found, or null if none were found within the radius
     */
    public Object findNearest(Point origin, Predicate<Locatable> predicate, double radius) {
        // PriorityQueue
        LinkedList<Point> queue = new LinkedList<>();
        // HashMap
        LinkedList<Point> visited = new LinkedList<>();
        queue.add(origin);
        while (!queue.isEmpty()) {
            queue.findFirst();
            Point current = queue.get();
            visited.add(current);
            queue.remove();
            for (Point neighbour : neighboursOf(current)) {
                if (visited.contains(neighbour)) continue;
                if (origin.distanceTo(neighbour) > radius) continue;
                for (Locatable item : neighbour.getObjects()) {
                    if (predicate.test(item)) return item;
                }
                queue.add(neighbour);
            }
        }
        // If no such Locatable is found within the radius
        return null;
    }

    /**
     * Returns a list of points that are neighbours to the specified point on the grid.
     * A neighbour is considered any point adjacent horizontally or vertically.
     *
     * @param point the point on the grid to find neighbours of
     * @return a list of neighbour points for the specified point
     */
    public LinkedList<Point> neighboursOf(Point point) {
        LinkedList<Point> list = new LinkedList<>();
        int x = point.x();
        int y = point.y();
        if (x > 0) list.add(get(x - 1, y));
        if (x < width - 1) list.add(get(x + 1, y));
        if (y > 0) list.add(get(x, y - 1));
        if (y < height - 1) list.add(get(x, y + 1));
        return list;
    }

    @Override
    public Taxi callTaxi(Location location, Fare fare, int radiusKm) {
        return (Taxi) findNearest((Point) location,
                o -> o instanceof Taxi taxi
                        && taxi.getFare().equals(fare)
                        && taxi.getStatus().equals(TaxiStatus.AVAILABLE),
                radiusKm);
    }

    public void moveTaxiRandomly(Taxi taxi) {
        // Busy taxis have a route already, they should not move randomly
        if (taxi.getStatus() != TaxiStatus.AVAILABLE) return;
        Point p = (Point) taxi.getLocation();
        // Move to a random adjacent point
        List<Point> neighbours = this.neighboursOf(p);
        int index = random.nextInt(neighbours.size());
        Point newPoint = neighbours.get(index);
        this.setLocation(taxi, newPoint.x() + newPoint.y() * width);
    }

    public void setLocation(Locatable l, int index) {
        setLocation(l, index % width, index / width);
    }

    public void setLocation(Locatable l, int x, int y) {
        Point oldPoint = (Point) l.getLocation();
        Point point = this.get(x, y);
        l.setLocation(point);
        point.getObjects().add(l);
        if (oldPoint != null) {
            try {
                oldPoint.getObjects().remove(l);
            } catch (UnsupportedOperationException ignored) {
            }
        }
    }
}
