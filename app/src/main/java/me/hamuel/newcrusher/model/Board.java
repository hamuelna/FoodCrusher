package me.hamuel.newcrusher.model;

public class Board {
    private Cell[][] board;
    private int dim;
    private final int VERTICAL_OFFSET = 40;
    private final int HORIZONTAL_OFFSET = 100;
    private final int SIDE_LENGTH = 50;
    private final int GAP = 10;

    public Board(int dim) {
        this.dim = dim;
        board = initBoard();
    }

    private Cell[][] initBoard(){
        int id = 0;
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
                id++;
                horz += SIDE_LENGTH + GAP;
            }
            vert+= SIDE_LENGTH + GAP;
        }
        return board;
    }

//    public Cell getCell(int row, int col) { return board[col][row];}
//
//    public void setCell(int row, int col, Cell cell){ board[col][row] = cell;}


    public Cell[][] getBoard() {
        return board;
    }

    public void swap(Cell a, Cell b){

    }

    public void collaspe(){

    }


}
