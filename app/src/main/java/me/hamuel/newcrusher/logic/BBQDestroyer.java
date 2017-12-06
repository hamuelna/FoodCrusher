package me.hamuel.newcrusher.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;

/**
 * Created by hamuel on 06/12/2017.
 */

public class BBQDestroyer implements Destroyable {
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

    @Override
    public boolean isDestroyable(Board board) {
        return markDestroyCell(board).size() > 0;
    }

    @Override
    public int increaseScore(Board board) {
        return markDestroyCell(board).size() * 2; //times two because it is BBQ more sauce!!!
    }

    private Set<Cell> markDestroyCell(Board board){
        Cell[][] currentBoard = board.getBoard();
        Set<Cell> detectedBBQ = detectBBQ(board);
        Set<Cell> combineNeedToBeDestroy = markLeft(detectedBBQ, currentBoard);
        combineNeedToBeDestroy.addAll(markRight(detectedBBQ, currentBoard));
        combineNeedToBeDestroy.addAll(markUp(detectedBBQ, currentBoard));
        combineNeedToBeDestroy.addAll(markDown(detectedBBQ, currentBoard));
        return combineNeedToBeDestroy;

    }

    private Set<Cell> detectBBQ(Board board_) {
        Cell[][] board = board_.getBoard();
        Set<Cell> bbqCells = new HashSet<>();
        for(Cell[] row: board){
            for(Cell cell: row){
                if(cell.getType() == CellType.SP_BBQ){
                    bbqCells.add(cell);
                }
            }
        }
        return bbqCells;
    }

    private Set<Cell> markLeft(Set<Cell> bbqCells, Cell[][] board){
        Set<Cell> markCells = new HashSet<>();
        for(Cell cell: bbqCells){
            for (int i = cell.getCol()-1; i >= 0 ; i--) {
                markCells.add(board[cell.getRow()][i]);
            }
        }
        return markCells;
    }

    private Set<Cell> markRight(Set<Cell> bbqCells, Cell[][] board){
        Set<Cell> markCells = new HashSet<>();
        for(Cell cell: bbqCells){
            for (int i = cell.getCol()+1; i < board.length ; i++) {
                markCells.add(board[cell.getRow()][i]);
            }
        }
        return markCells;
    }

    private Set<Cell> markDown(Set<Cell> bbqCells, Cell[][] board){
        Set<Cell> markCells = new HashSet<>();
        for(Cell cell: bbqCells){
            for (int i = cell.getRow()-1; i >= 0 ; i--) {
                markCells.add(board[i][cell.getCol()]);
            }
        }
        return markCells;
    }

    private Set<Cell> markUp(Set<Cell> bbqCells, Cell[][] board){
        Set<Cell> markCells = new HashSet<>();
        for(Cell cell: bbqCells){
            for (int i = cell.getRow()+1; i < board.length ; i++) {
                markCells.add(board[i][cell.getCol()]);
            }
        }
        return markCells;
    }
}
