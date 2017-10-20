package logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;

import java.util.List;

public class DefaultFaller implements Fallable {
    @Override
    public List<CellPair> collapse(Board board) {
        Cell[][] currentBoard = board.getBoard();

        return null;
    }
}
