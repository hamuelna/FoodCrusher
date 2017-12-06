package me.hamuel.newcrusher.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;

/**
 * Created by hamuel on 07/12/2017.
 */

public class BBQFiller implements Creatable {
    private final int CONSECUTIVE_MIN = 4;
    @Override
    public List<Cell> fillBoard(Board board) {
        List<Cell> cells = new ArrayList<>();
        for(Cell cell: detectConsecutiveBlank(board)){
            cell.setType(CellType.SP_BBQ);
            cells.add(cell);
        }
        return cells;
    }

    private Set<Cell> detectConsecutiveBlank(Board board_){
        Set<Cell> combineDetected = detectHorizontal(board_);
        combineDetected.addAll(detectHorizontal(board_));
        return combineDetected;

    }

    private Set<Cell> detectHorizontal(Board board_){
        Cell[][] board = board_.getBoard();
        Set<Cell> detectedCell = new HashSet<>();
        for (int row = 0; row < board_.getDim(); row++) {
            Cell startingCell = null;
            int consecutiveCellCounter = 0;
            for(int col = 0; col < board_.getDim(); col++){
                Cell currentCell = board[row][col];
                if(currentCell.getType() == CellType.BLANK){
                    if(startingCell == null){
                        startingCell = currentCell;
                    }
                    consecutiveCellCounter++;
                }else{
                    if(consecutiveCellCounter >= CONSECUTIVE_MIN){
                        detectedCell.add(startingCell);
                    }
                    consecutiveCellCounter = 0;
                    startingCell = null;
                }
            }
            if(consecutiveCellCounter >= CONSECUTIVE_MIN){
                detectedCell.add(startingCell);
            }
        }

        return detectedCell;
    }

    private Set<Cell> detectVertical(Board board_){
        Cell[][] board = board_.getBoard();
        Set<Cell> detectedCell = new HashSet<>();
        for (int col = 0; col < board_.getDim(); col++) {
            Cell startingCell = null;
            int consecutiveCellCounter = 0;
            for (int row = 0; row < board_.getDim(); row++) {
                Cell currentCell = board[row][col];
                if(currentCell.getType() == CellType.BLANK){
                    if(startingCell == null){
                        startingCell = currentCell;
                    }
                    consecutiveCellCounter++;
                }else{
                    if(consecutiveCellCounter >= CONSECUTIVE_MIN){
                        detectedCell.add(startingCell);
                    }
                    consecutiveCellCounter = 0;
                    startingCell = null;
                }
            }
            if(consecutiveCellCounter >= CONSECUTIVE_MIN){
                detectedCell.add(startingCell);
            }
        }
        return detectedCell;
    }
}
