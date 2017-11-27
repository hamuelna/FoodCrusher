package me.hamuel.newcrusher.logic;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.hamuel.newcrusher.event.ScoringEvent;
import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.utils.BoardUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * mark and destroy consecutive cell however can only be call if there is no blank cell
 * in the board otherwise there will be bug for sure (it will keep destroying non stop)
 */
public class DefaultDestroyer implements Destroyable {
    private int MINIMUM_CONSECUTIVE_CELL = 3;

    @Override
    public Set<Cell> destroy(Board board) {
        Cell[][] currentBoard = board.getBoard();
        Set<Cell> markedCell = markDestroyCell(board);
        //actually remove the cell in the board by setting it to blank
        for(Cell destroyCell: markedCell){
            currentBoard[destroyCell.getRow()][destroyCell.getCol()].setType(CellType.BLANK);
        }
        return markedCell;
    }

    private Set<Cell> markDestroyCell(Board board){
        Cell[][] currentBoard = board.getBoard();
        Set<Cell> combineNeedToBeDestroy = scanHorizontal(currentBoard);
        combineNeedToBeDestroy.addAll(scanVertical(currentBoard));
        return combineNeedToBeDestroy;

    }

    @Override
    public int increaseScore(Board board) {
        return markDestroyCell(board).size();
    }

    @Override
    public boolean isDestroyable(Board board) {
        return markDestroyCell(board).size() > 0;
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
                }else if(currentCell.getType() == currentIteratingCell.getType()){
                    rowKeeper.add(currentIteratingCell);
                    rowKeeper.add(currentCell);
                }else{
                    currentCell = currentIteratingCell;
                    if(rowKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                        needToBeDestroy.addAll(rowKeeper);
                        currentCell = null;
                    }
                    rowKeeper.clear();
                }
            }
            //reset the keeper when moving to the next row
            if (rowKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                needToBeDestroy.addAll(rowKeeper);
            }
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
                }else if(currentCell.getType() == currentIteratingCell.getType()){
                    colKeeper.add(currentIteratingCell);
                    colKeeper.add(currentCell);
                }else{
                    currentCell = currentIteratingCell;
                    if(colKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                        needToBeDestroy.addAll(colKeeper);
                    }
                    colKeeper.clear();
                }
            }
            //reset the keeper when moving to the next column
            if (colKeeper.size() >= MINIMUM_CONSECUTIVE_CELL){
                needToBeDestroy.addAll(colKeeper);
            }
            colKeeper.clear();
            currentCell = null;
        }
        return needToBeDestroy;
    }
}
