package com.ise.taxiapp.nav.grid;

import com.ise.taxiapp.nav.Region;

public class Grid extends Region {

    private final int width;
    private final int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("%d x %d Grid", width, height);
    }
}
