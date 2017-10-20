package logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultDestroyer implements Destroyable {
    private int MINIMUM_CONSECUTIVE_CELL = 3;

    @Override
    public Set<Cell> destroy(Board board) {
        Cell[][] currentBoard = board.getBoard();
        Set<Cell> combineNeedToBeDestroy = scanHorizontal(currentBoard);
        combineNeedToBeDestroy.addAll(scanVertical(currentBoard));
        //actually remove the cell in the board
        for(Cell destroyCell: combineNeedToBeDestroy){
            currentBoard[destroyCell.getRow()][destroyCell.getCol()] = null;
        }
        return combineNeedToBeDestroy;
    }

    @Override
    public boolean isDestroyable(Board board) {
        return destroy(board).size() > 0;
    }

    @SuppressWarnings("Duplicates")
    private Set<Cell> scanHorizontal(Cell[][] board){
        List<Cell> rowKeeper = new ArrayList<>();
        Set<Cell> needToBeDestroy = new HashSet<>();
        Cell currentCell = null;
        for (int irow = 0; irow < board.length; irow++) {
            for (int icol = 0; icol < board.length; icol++) {
                Cell currentIteratingCell = board[irow][icol];
                if (currentCell == null) {
                    currentCell = currentIteratingCell;
                    rowKeeper.add(currentIteratingCell);
                }else if(currentCell.getType() == currentIteratingCell.getType()){
                    rowKeeper.add(currentIteratingCell);
                }else{
                    if(rowKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                        needToBeDestroy.addAll(rowKeeper);
                    }
                    rowKeeper.clear();
                    rowKeeper.add(currentIteratingCell);
                }
            }
            //reset the keeper when moving to the next row
            rowKeeper.clear();
            currentCell = null;
        }
        return needToBeDestroy;
    }

    @SuppressWarnings("Duplicates")
    private Set<Cell> scanVertical(Cell[][] board){
        List<Cell> colKeeper = new ArrayList<>();
        Set<Cell> needToBeDestroy = new HashSet<>();
        Cell currentCell = null;
        for (int icol = 0; icol < board.length; icol++) {
            for (int irow = 0; irow < board.length; irow++) {
                Cell currentIteratingCell = board[irow][icol];
                if (currentCell == null) {
                    currentCell = currentIteratingCell;
                    colKeeper.add(currentIteratingCell);
                }else if(currentCell.getType() == currentIteratingCell.getType()){
                    colKeeper.add(currentIteratingCell);
                }else{
                    if(colKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                        needToBeDestroy.addAll(colKeeper);
                    }
                    colKeeper.clear();
                    colKeeper.add(currentIteratingCell);
                }
            }
            //reset the keeper when moving to the next column
            colKeeper.clear();
            currentCell = null;
        }
        return needToBeDestroy;
    }
}
