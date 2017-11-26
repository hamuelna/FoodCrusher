package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;

import java.util.List;

public class DefaultGameOver implements GameOverable {
    private List<Swappable> swappables;

    public DefaultGameOver(List<Swappable> swappables) {
        this.swappables = swappables;
    }

    @Override
    public boolean isGameOver(Board board) {
        Cell[][] board_ = board.getBoard();
        for (Swappable swappable: swappables){
            //check horizontal
            for (int row = 0; row < board.getDim(); row++) {
                for (int col = 1; col < board.getDim(); col++) {
                    Cell a = board_[row][col];
                    Cell b = board_[row][col-1];
                    if(swappable.isSwappable(a,b, board)){
                        return false;
                    }
                }
            }
            //check vertical
            for (int row = 1; row < board.getDim(); row++) {
                for (int col = 0; col < board.getDim(); col++) {
                    Cell a = board_[row][col];
                    Cell b = board_[row-1][col];
                    if(swappable.isSwappable(a,b, board)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
