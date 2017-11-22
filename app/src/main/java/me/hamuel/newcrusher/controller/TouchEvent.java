package me.hamuel.newcrusher.controller;

import me.hamuel.newcrusher.model.Point;

public class TouchEvent {
    public final Point firstPoint;
    public final Point secondPoint;

    public TouchEvent(Point firstPoint, Point secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }
}
