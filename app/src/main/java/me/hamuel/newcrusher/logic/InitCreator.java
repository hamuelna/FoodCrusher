package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitCreator implements Creatable {

    @Override
    public Cell generateCell(int row, int col, Coordinate coordinate, Board board) {
        Cell[][] board1 = board.getBoard();
        CellType allTypes[] = CellType.values();
        ArrayList<CellType> forbidTypes = checkAll(row, col, board1);
        CellType randType = randomType(allTypes);
        while(forbidTypes.contains(randType)){
            randType = randomType(allTypes);
        }
        return new Cell(row, col, coordinate, randType);
    }


    private CellType randomType(CellType types[]){
        Random random = new Random();
        int index = random.nextInt(types.length);
        return types[index];
    }

    private ArrayList<CellType> checkAll(int row, int col, Cell[][] board){

        ArrayList<CellType> forbidType = new ArrayList<>();

        CellType blank = CellType.BLANK;
        CellType left = checkLeft(row, col, board);
        CellType right = checkRight(row, col, board);
        CellType leftRight = checkLeftRight(row, col, board);
        CellType up = checkUp(row, col, board);
        CellType down = checkDown(row, col, board);
        CellType upDown = checkUpDown(row, col, board);

        if (left != blank){
            forbidType.add(left);
        } else if (right != blank){
            forbidType.add(right);
        } else if (leftRight != blank){
            forbidType.add(leftRight);
        } else if (up != blank){
            forbidType.add(up);
        } else if (down != blank){
            forbidType.add(down);
        } else if (upDown != blank){
            forbidType.add(upDown);
        }

        return forbidType;
    }

    /**
     * return true if left and right of current cell
     * has the same type.
     * @param row
     * @param col
     * @param board
     * @return
     */
    private CellType checkLeftRight(int row, int col, Cell[][] board){
        try {
            CellType left = board[row][col-1].getType();
            CellType right = board[row][col+1].getType();
            if (left == right && left != CellType.BLANK){
                return left;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }

    private CellType checkLeft(int row, int col, Cell[][] board){
        try {
            CellType left1 = board[row][col-1].getType();
            CellType left2 = board[row][col-2].getType();
            if (left1 == left2 && left1 != CellType.BLANK){
                return left1;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }

    private CellType checkRight(int row, int col, Cell[][] board){

        try {
            CellType right1 = board[row][col+1].getType();
            CellType right2 = board[row][col+2].getType();
            if (right1 == right2 && right1 != CellType.BLANK){
                return right1;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }

    private CellType checkUpDown(int row, int col, Cell[][] board){
        try {
            CellType up = board[row-1][col].getType();
            CellType down = board[row+1][col].getType();
            if (up == down && up != CellType.BLANK){
                return up;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }

    private CellType checkUp(int row, int col, Cell[][] board){
        try {
            CellType up1 = board[row-1][col].getType();
            CellType up2 = board[row-2][col].getType();
            if (up1 == up2 && up1 != CellType.BLANK){
                return up1;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }

    private CellType checkDown(int row, int col, Cell[][] board){
        try {
            CellType down1 = board[row+1][col].getType();
            CellType down2 = board[row+2][col].getType();
            if (down1 == down2 && down1 != CellType.BLANK){
                return down1;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return CellType.BLANK;
        }
        return CellType.BLANK;
    }
}
