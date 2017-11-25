package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.Coordinate;
import me.hamuel.newcrusher.utils.BoardUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultSwapper implements Swappable {
    private int MINIMUM_CONSECUTIVE_CELL = 3;
    private List<Destroyable> destroyer;

    public DefaultSwapper(List<Destroyable> destroyer) {
        this.destroyer = destroyer;
    }

    @Override
    public boolean isSwappable(Cell a, Cell b, Board board) {
        //check if we can swap or not and swap it back even though it
        //is swappable
        logicSwap(a,b, board.getBoard());
        for (Destroyable destroyable: destroyer){
            if (destroyable.isDestroyable(board)){
                logicSwap(a,b, board.getBoard());
                return true;
            }
        }
        logicSwap(a,b, board.getBoard());
        return false;
    }

    @Override
    public List<CellPair> swap(Cell a, Cell b, Board board) {
        List<CellPair> cellPairs = new ArrayList<>();
        CellPair a_ = new CellPair(a.clone(), null);
        CellPair b_ = new CellPair(b.clone(), null);
        if(isSwappable(a,b, board)){
            //swap for real when it is swappable
            logicSwap(a,b, board.getBoard());
        }else{
            return null;
        }
        a_.setTo(a);
        b_.setTo(b);
        cellPairs.add(a_);
        cellPairs.add(b_);
        return cellPairs;
    }


    private void logicSwap(Cell a, Cell b, Cell[][] board){

        int aRow = a.getRow();
        int aCol = a.getCol();
        Coordinate aCoordinate = a.getCoordinate();
        int bRow = b.getRow();
        int bCol = b.getCol();
        Coordinate bCoordinate = b.getCoordinate();

        a.setRow(bRow);
        a.setCol(bCol);
        a.setCoordinate(bCoordinate);

        b.setRow(aRow);
        b.setCol(aCol);
        b.setCoordinate(aCoordinate);

        board[aRow][aCol] = b;
        board[bRow][bCol] = a;



    }


}
