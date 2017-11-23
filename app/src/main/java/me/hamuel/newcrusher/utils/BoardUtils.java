package me.hamuel.newcrusher.utils;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;

public class BoardUtils {
    public static void printBoard(Board board){
        Cell[][] board_ = board.getBoard();
        for(Cell[] row: board_){
            for(Cell cell: row){
                System.out.printf(cell.getType() + " ");
            }
            System.out.println();
        }
    }

    public static void printBoard(Cell[][] board){
        for(Cell[] row: board){
            for(Cell cell: row){
                System.out.printf(cell.getType() + " ");
            }
            System.out.println();
        }
    }
}
