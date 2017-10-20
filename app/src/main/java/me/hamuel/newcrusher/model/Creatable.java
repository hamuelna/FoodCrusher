package me.hamuel.newcrusher.model;

public interface Creatable {
    /**
     * Generate the cell according according to the strategy
     * @param row
     * @param col
     * @param coordinate
     * @param type
     * @return
     */
    Cell generateCell (int row, int col, Coordinate coordinate, CellType type);
}
