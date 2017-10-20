package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;

import java.util.Set;

public interface Destroyable {
    /**
     * Destroy all the cell that need to be destroy after the swap
     * however you should check initially if there are destroyable cell
     * @param board
     * @return The set of cell that need to be destroy
     */
    Set<Cell> destroy(Board board);

    /**
     * Check whether there is still destroyable cell
     * @param board
     * @return true if there are destroyable cell
     */
    boolean isDestroyable(Board board);
}
