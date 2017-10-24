package me.hamuel.newcrusher.controller;

import me.hamuel.newcrusher.model.Board;

public interface Executable {

    /**
     * Receive the command with argument to execute the command specify
     * help to reduce coupling
     * @param args
     */
    void execute(String[] args, Board board, BoardAdapter boardAdapter);
}
