package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.CellType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefaultFaller implements Fallable {
    @Override
    public List<CellPair> collapse(Board board) {
        Cell[][] currentBoard = board.getBoard();
        return null;
    }

    private Cell[] getVertical(Cell[][] board, int icol){
        Cell[] line = new Cell[board.length];
        for (int irow = 0; irow < board.length; irow++) {
            line[irow] = board[irow][icol];
        }
        return line;
    }

    private List<CellPair> logicalCollaspe(Cell[][] board, int icol){
        LinkedList<Cell> blankCells = new LinkedList<>();
        LinkedList<Cell> activeCells = new LinkedList<>();
        Cell[] originalLine = getVertical(board, icol);
        Cell[] collapseLine = new Cell[board.length];
        List<CellPair> cellPairs = new ArrayList<>();

        for(Cell cell: originalLine){
            if(cell.getType() == CellType.BLANK){
                blankCells.add(cell);
            }else{
                activeCells.add(cell);
            }
        }

        for (int irow = 0; irow < collapseLine.length; irow++) {
            if(blankCells.isEmpty()){
                Cell from = activeCells.poll().clone();
                Cell to = originalLine[irow].clone();
                to.setType(from.getType());
                cellPairs.add(new CellPair(from, to));
                collapseLine[irow] = to;
            }else{
                Cell from = blankCells.poll();
                Cell to = originalLine[irow].clone();
                to.setType(from.getType());
                collapseLine[irow] = to;
            }
        }

        return cellPairs;



    }
}
