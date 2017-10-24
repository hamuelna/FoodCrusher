package me.hamuel.newcrusher.controller;

import android.graphics.RectF;
import me.hamuel.newcrusher.model.*;

import java.util.List;

public class Touch implements Executable {
    private CellView selectedCell = null;
    private boolean clickedFlag = false;
    @Override
    public void execute(String[] args, Board board, BoardAdapter boardAdapter) {
        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);
        List<CellView> cellViewList = boardAdapter.getViewBoard(board);
        for(CellView cellView: cellViewList){
            if (cellView.getCoordinate().contains(x, y) && !clickedFlag){
                selectedCell = cellView;
                clickedFlag = true;
            }else if(cellView.getCoordinate().contains(x,y) && clickedFlag){
                Cell cellA = boardSearch(selectedCell, board);
                Cell cellB = boardSearch(cellView, board);
                if(board.isSwappable(cellA, cellB)){
                    List<CellPair> toBeMoved = board.swap(cellA, cellB);
                    //animate the swap after finishing the animation destroy the destroyable
                    //and animate the rest
                }
                selectedCell = null;
                clickedFlag = false;
            }
        }
    }

    private Cell boardSearch(CellView cellView, Board board){
        Cell[][] board_ = board.getBoard();
        RectF rectF = cellView.getCoordinate();
        float left = rectF.left;
        float top = rectF.top;
        float right = rectF.right;
        float bottom = rectF.bottom;
        for(Cell[] cellRows: board_){
            for(Cell cell: cellRows){
                Coordinate coordinate = cell.getCoordinate();
                float left_ = coordinate.getLeft();
                float top_ = coordinate.getTop();
                float right_ = coordinate.getRight();
                float bottom_ = coordinate.getBottom();
                if(left == left_ && top == top_ && right == right_ && bottom == bottom_){
                    return cell;
                }
            }
        }
        return null;
    }
}
