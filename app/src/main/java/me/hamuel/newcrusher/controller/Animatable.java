package me.hamuel.newcrusher.controller;

import android.view.View;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;

import java.util.List;

public interface Animatable {

    /**
     * move all the cell pair from their original position
     * to the place they need to move to
     * @param cellPairs
     * @param view
     */
    void animateMoveTo (List<CellPair> cellPairs, View view);

    /**
     * animating the destruction of the cell
     * @param cells
     * @param view
     */
    void animateDestroy(List<Cell> cells, View view);

}
