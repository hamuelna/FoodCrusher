package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;

import java.util.ArrayList;
import java.util.List;

public class DefaultSwapper implements Swappable {
    private int MINIMUM_CONSECUTIVE_CELL = 3;
    @Override
    public boolean isSwappable(Cell a, Cell b, Board board) {
        return checkHorizontal(a.getRow(), board.getBoard()) || checkHorizontal(b.getRow(), board.getBoard())
                || checkVertical(a.getCol(), board.getBoard()) || checkVertical(b.getCol(), board.getBoard());
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<CellPair> swap(Cell a, Cell b, Board board) {
        Cell[][] currentBoard = board.getBoard();
        List<CellPair> cellPairs = new ArrayList<>();
        //temporary swap the cell
        logicSwap(a, b, currentBoard);
        CellPair a_ = new CellPair(a.clone(), null);
        CellPair b_ = new CellPair(b.clone(), null);
        if(!isSwappable(a,b, board)){
            //if it is not swappable swap the state back
            logicSwap(a,b, currentBoard);
            return null;
        }
        a_.setTo(b);
        b_.setTo(a);
        cellPairs.add(a_);
        cellPairs.add(b_);
        return cellPairs;

    }

    @SuppressWarnings("Duplicates")
    private boolean checkVertical(int col, Cell[][] currentBoard){
        int consecutiveCounter = 1;
        Cell currentCell = null;
        for (int i = 0; i < currentBoard.length; i++) {
            if(currentCell == null){
                currentCell = currentBoard[i][col];
            }else if(currentBoard[i][col].getType() == currentCell.getType()){
                consecutiveCounter++;
            }else{
                currentCell = currentBoard[i][col];
            }
            if(consecutiveCounter >= MINIMUM_CONSECUTIVE_CELL) {
                return true;
            }
        }
        return false;

    }

    @SuppressWarnings("Duplicates")
    private boolean checkHorizontal(int row, Cell[][] currentBoard){
        int consecutiveCounter = 1;
        Cell currentCell = null;
        for (int i = 0; i < currentBoard.length; i++) {
            if(currentCell == null){
                currentCell = currentBoard[row][i];
            }else if(currentBoard[row][i].getType() == currentCell.getType()){
                consecutiveCounter++;
            }else{
                currentCell = currentBoard[row][i];
            }
            if(consecutiveCounter >= MINIMUM_CONSECUTIVE_CELL) {
                return true;
            }
        }
        return false;

    }

    private void logicSwap(Cell a, Cell b, Cell[][] board){
        int aRow = a.getRow();
        int aCol = a.getCol();
        int bRow = b.getRow();
        int bCol = b.getCol();

        a.setRow(bRow);
        a.setCol(bCol);
        b.setRow(aRow);
        b.setCol(aCol);

        board[aCol][aRow] = b;
        board[bCol][bRow] = a;
    }


}
