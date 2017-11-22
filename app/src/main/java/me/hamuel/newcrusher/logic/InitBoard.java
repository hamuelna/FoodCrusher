package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitBoard implements Creatable {
    private Random random;

    public InitBoard() {
        random = new Random();
    }

    @Override
    public List<Cell> fillBoard(Board board) {
        List<Cell> cells = new ArrayList<>();
        int vert = board.VERTICAL_OFFSET;
        int horz = board.HORIZONTAL_OFFSET;
        int dim = board.getDim();
        Cell[][] board_ = board.getBoard();
        cells = randomBoard(board);
        while (board.isDestroyable()){
            cells = randomBoard(board);
            System.out.println("Still randoming board");
        }
        return cells;
    }

    private List<Cell> randomBoard(Board board){
        List<Cell> cells = new ArrayList<>();
        int vert = board.VERTICAL_OFFSET;
        int horz = board.HORIZONTAL_OFFSET;
        int dim = board.getDim();
        Cell[][] board_ = board.getBoard();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board_[i][j] = new Cell(
                        i,
                        j,
                        new Coordinate(horz, vert, horz + board.SIDE_LENGTH, vert + board.SIDE_LENGTH),
                        randomType()
                );
                cells.add(board_[i][j]);
                horz += board.SIDE_LENGTH + board.GAP;
            }
            vert+= board.SIDE_LENGTH + board.GAP;
        }
        return cells;
    }

    private CellType randomType(){
        return CellType.values()[random.nextInt(CellType.values().length)];
    }
}
