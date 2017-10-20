package logic;

import java.util.List;

public interface Fallable {
    /**
     * collapse the cell by shifting the cell down
     * to replace the existing cell that was destroyed
     * also change the state of the board by shifting the cell
     * in the board
     * @param board
     * @return a cell pair that specify where the cell was move to
     * make it easier for the frontend to animate the collapsing cell
     */
    List<CellPair> collapse(Board board);
}
