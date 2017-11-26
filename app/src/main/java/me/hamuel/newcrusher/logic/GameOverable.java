package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;

public interface GameOverable {
    /**
     * Check if the game have ended meaning there is no possible move left
     *
     * @param board
     * @return whether the game is over or not yet
     */
    boolean isGameOver(Board board);
}
