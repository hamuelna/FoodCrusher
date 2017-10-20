package me.hamuel.newcrusher.model;

public class DefaultSwapper implements Swappable {
    private int MINIMUM_CONSECUTIVE_CELL = 3;
    @Override
    public boolean isSwappable(Cell a, Cell b, Board board) {
        return checkHorizontal(a.getRow(), board.getBoard()) || checkHorizontal(b.getRow(), board.getBoard())
                || checkVertical(a.getCol(), board.getBoard()) || checkVertical(b.getCol(), board.getBoard());
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void swap(Cell a, Cell b, Board board) {
        Cell[][] currentBoard = board.getBoard();
        //temporary swap the cell
        logicSwap(a, b, currentBoard);

        if(!isSwappable(a,b, board)){
            //if it is not swappable swap the state back
            logicSwap(a,b, currentBoard);
        }

    }

    @SuppressWarnings("Duplicates")
    private boolean checkVertical(int col, Cell[][] currentBoard){
        int consecutiveCounter = 1;
        Cell currentCell = null;
        for (int i = 0; i < currentBoard.length; i++) {
            if(currentCell == null){
                currentCell = currentBoard[i][col];
            }else if(currentBoard[i][col].getType() == currentCell.getType()){
                consecutiveCounter++;
            }else{
                currentCell = currentBoard[i][col];
            }
            if(consecutiveCounter >= MINIMUM_CONSECUTIVE_CELL) {
                return true;
            }
        }
        return false;

    }

    @SuppressWarnings("Duplicates")
    private boolean checkHorizontal(int row, Cell[][] currentBoard){
        int consecutiveCounter = 1;
        Cell currentCell = null;
        for (int i = 0; i < currentBoard.length; i++) {
            if(currentCell == null){
                currentCell = currentBoard[row][i];
            }else if(currentBoard[row][i].getType() == currentCell.getType()){
                consecutiveCounter++;
            }else{
                currentCell = currentBoard[row][i];
            }
            if(consecutiveCounter >= MINIMUM_CONSECUTIVE_CELL) {
                return true;
            }
        }
        return false;

    }

    private void logicSwap(Cell a, Cell b, Cell[][] board){
        int aRow = a.getRow();
        int aCol = a.getCol();
        int bRow = b.getRow();
        int bCol = b.getCol();

        a.setRow(bRow);
        a.setCol(bCol);
        b.setRow(aRow);
        b.setCol(aCol);

        board[aCol][aRow] = b;
        board[bCol][bRow] = a;
    }


}
