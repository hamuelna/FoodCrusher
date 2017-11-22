package me.hamuel.newcrusher.event;

import me.hamuel.newcrusher.model.Coordinate;

/**
 * This class is when the frontend send the swap cell to the backend
 */
public class MoveCellEvent {
    private Coordinate cellA;
    private Coordinate cellB;

    public MoveCellEvent(Coordinate cellA, Coordinate cellB) {
        this.cellA = cellA;
        this.cellB = cellB;
    }

    public Coordinate getCellA() {
        return cellA;
    }

    public Coordinate getCellB() {
        return cellB;
    }
}
