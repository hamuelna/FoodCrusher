package me.hamuel.newcrusher.logic;

import android.util.Log;
import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.*;

public class InitBoard implements Creatable {
    private Random random;
    public InitBoard() {

        random = new Random();
    }

    @Override
    public List<Cell> fillBoard(Board board) {
        List<Cell> cells;
        int vert = board.VERTICAL_OFFSET;
        int horz = board.HORIZONTAL_OFFSET;
        int dim = board.getDim();
        Cell[][] board_ = board.getBoard();
        cells = randomBoard(board);
//        Log.d("isBoardDestroyable?", Boolean.toString(board.isDestroyable()));
        return cells;
    }

    private List<Cell> randomBoard(Board board){
        List<Cell> cells = new ArrayList<>();
        int vert = board.VERTICAL_OFFSET;
        int horz = board.HORIZONTAL_OFFSET;
        int dim = board.getDim();
        Cell[][] board_ = board.getBoard();
        for (int i = 0; i < dim; i++) {
            int startHorz = horz;
            for (int j = 0; j < dim; j++) {
                board_[i][j] = new Cell(
                        i,
                        j,
                        new Coordinate(horz, vert, horz + board.SIDE_LENGTH, vert + board.SIDE_LENGTH),
                        CellType.BLANK
                );
                horz += board.SIDE_LENGTH + board.GAP;
            }
            horz = startHorz;
            vert+= board.SIDE_LENGTH + board.GAP;
        }

        vert = board.VERTICAL_OFFSET;
        horz = board.HORIZONTAL_OFFSET;

        for (int i = 0; i < dim; i++) {
            int startHorz = horz;
            for (int j = 0; j < dim; j++) {
                board_[i][j] = new Cell(
                        i,
                        j,
                        new Coordinate(horz, vert, horz + board.SIDE_LENGTH, vert + board.SIDE_LENGTH),
                        randomType(board, i, j)
                );
                cells.add(board_[i][j]);
                horz += board.SIDE_LENGTH + board.GAP;
            }
            horz = startHorz;
            vert+= board.SIDE_LENGTH + board.GAP;
        }
        return cells;
    }

    private CellType randomType(Board board, int i, int j){
        //get all type except blank
        List<CellType> types = new ArrayList<>();
        Set<CellType> forbiddenTypes = new HashSet<>();
        forbiddenTypes.add(CellType.BLANK);
        forbiddenTypes.add(scanLeft(board, i, j));
        forbiddenTypes.add(scanDown(board, i, j));
        forbiddenTypes.add(scanRight(board, i, j));
        forbiddenTypes.add(scanTop(board,i, j));
        for(CellType cellType: CellType.values()){
            if(!forbiddenTypes.contains(cellType)){
                types.add(cellType);
            }
        }
        return types.get(random.nextInt(types.size()));
    }

    private boolean checkType(CellType a, CellType b){
        return (a == b) && (a != CellType.BLANK);
    }

    /**
     * Scan whether the cell is consecutive or not i is x
     * and j is y
     * @param i
     * @param j
     * @return a cell that will make it consecutive so avoid that type if can use any cell
     * return BLANK
     */
    private CellType scanLeft(Board board, int i, int j){
        Cell[][] board_ = board.getBoard();
        if(i-2 >= 0){
            Cell a = board_[i-1][j];
            Cell b = board_[i-2][j];
            if(checkType(a.getType(), b.getType())){
                return a.getType();
            }
        }

        return CellType.BLANK;
    }

    private CellType scanRight(Board board, int i, int j){
        int dim = board.getDim();
        if(i+2 < dim){
            Cell[][] board_ = board.getBoard();
            Cell a = board_[i+1][j];
            Cell b = board_[i+2][j];

            if(checkType(a.getType(), b.getType())){
                return a.getType();
            }
        }
        return CellType.BLANK;

    }

    private CellType scanTop(Board board, int i, int j){
        int dim = board.getDim();
        if(j+2 < dim){
            Cell[][] board_ = board.getBoard();
            Cell a = board_[i][j+1];
            Cell b = board_[i][j+2];
            if(checkType(a.getType(), b.getType())){
                return a.getType();
            }
        }
        return CellType.BLANK;

    }

    private CellType scanDown(Board board, int i, int j){
        Cell[][] board_ = board.getBoard();
        if(j-2 >= 0){
            Cell a = board_[i][j-1];
            Cell b = board_[i][j-2];
            if(checkType(a.getType(), b.getType())){
                return a.getType();
            }
        }
        return CellType.BLANK;

    }

}
