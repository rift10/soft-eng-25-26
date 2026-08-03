package com.rift10.weather;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {

    @Override
    public int compareTo(Coordinate other) {
        return this.x() - other.x();
    }
}
