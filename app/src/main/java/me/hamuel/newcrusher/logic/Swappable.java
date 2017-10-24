package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;

import java.util.List;

public interface Swappable {
    /**
     * Check whether the two cell is swappable
     * @param a
     * @param b
     * @return true if the two cell can be swap
     */
    boolean isSwappable(Cell a, Cell b, Board board);

    /**
     * Swap the cell if it is swappable however if you temporary swap the cell
     * you need to swap the cell back if it is not swappable
     * @param a
     * @param b
     * @return list of cell pair if it is swappable if not return null
     */
    List<CellPair> swap(Cell a, Cell b, Board board);
}
