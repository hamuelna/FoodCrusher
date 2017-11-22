package me.hamuel.newcrusher.model;

public class Point {
    private Coordinate a;
    private Coordinate b;

    public Point(Coordinate a, Coordinate b) {
        this.a = a;
        this.b = b;
    }

    public Coordinate getA() {
        return a;
    }

    public Coordinate getB() {
        return b;
    }
}
