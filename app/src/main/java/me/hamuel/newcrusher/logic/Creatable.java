package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.List;

public interface Creatable {

    /**
     * Fill the board according to our strategy for example can
     * be use to initialize the board or fill in the empty Cell with a proper cell
     * @param board
     */
    List<Cell> fillBoard(Board board);
}
