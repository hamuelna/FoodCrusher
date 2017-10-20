package me.hamuel.newcrusher.model;

public class CellPair {
    private Cell from;
    private Cell to;

    public CellPair(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }
}
