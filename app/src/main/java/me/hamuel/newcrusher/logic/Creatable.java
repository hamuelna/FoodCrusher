package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.model.Coordinate;

public interface Creatable {
    /**
     * Generate the cell according according to the strategy
     * @param row
     * @param col
     * @param coordinate
     * @return
     */
    Cell generateCell (int row, int col, Coordinate coordinate, Board board);
}
