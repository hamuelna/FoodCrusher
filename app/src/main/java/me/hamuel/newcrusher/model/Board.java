package me.hamuel.newcrusher.model;

import me.hamuel.newcrusher.logic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private Cell[][] board;
    private int dim;
    private final int VERTICAL_OFFSET = 40;
    private final int HORIZONTAL_OFFSET = 100;
    private final int SIDE_LENGTH = 50;
    private final int GAP = 10;
    private Creatable creator;
    private List<Destroyable> destroyables;
    private List<Fallable> fallables;
    private List<Swappable> swappables;

    public Board(int dim) {
        this.dim = dim;
        board = initBoard();
        destroyables.addAll(Arrays.asList(
                new DefaultDestroyer()
        ));
        fallables.addAll(
                Arrays.asList(
                        new DefaultFaller()
                )
        );
        swappables.addAll(Arrays.asList(
                new DefaultSwapper()
        ));
        creator = new InitCreator();
    }

    private Cell[][] initBoard(){
        int vert = VERTICAL_OFFSET;
        int horz = HORIZONTAL_OFFSET;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = new Cell(
                        i,
                        j,
                        new Coordinate(horz, vert, horz + SIDE_LENGTH, vert + SIDE_LENGTH),
                        CellType.BLANK
                );
                horz += SIDE_LENGTH + GAP;
            }
            vert+= SIDE_LENGTH + GAP;
        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = creator.generateCell(i,j,
                        new Coordinate(horz, vert, horz + SIDE_LENGTH, vert + SIDE_LENGTH),
                        this);
            }
        }
        return board;
    }


    public Cell[][] getBoard() {
        return board;
    }

    public List<CellPair> swap(Cell a, Cell b){
        List<CellPair> movedPostion = new ArrayList<>();
        for(Swappable swapper: swappables){
            if (swapper.isSwappable(a,b,this)){
                movedPostion.addAll(swapper.swap(a,b,this));
            }
        }
        return movedPostion;

    }

    public boolean isSwappable(Cell a, Cell b){
        for(Swappable swapper: swappables){
            if (swapper.isSwappable(a,b,this)){
                return true;
            }
        }
        return false;
    }

    public List<Cell> destroy(){
        List<Cell> destroyedCell = new ArrayList<>();
        for(Destroyable destroyer: destroyables){
            if(destroyer.isDestroyable(this)){
                destroyedCell.addAll(destroyer.destroy(this));
            }
        }
        return destroyedCell;
    }

    public List<CellPair> collaspe(){
        List<CellPair> movedPostion = new ArrayList<>();
        for(Fallable faller: fallables){
            movedPostion.addAll(faller.collapse(this));
        }
        return movedPostion;
    }


}
