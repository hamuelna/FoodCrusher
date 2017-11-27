package me.hamuel.newcrusher.event;

import android.content.res.Resources;
import me.hamuel.newcrusher.frontlogic.CellViewFactory;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.CellView;

import java.util.ArrayList;
import java.util.List;

/**
 * add the new cell that were destroy however the coordinates are set at the top
 * instead of its real position to make the animation happen
 */
public class PartialFillCellEvent {
    private List<CellPair> animatedCells;
    private List<Cell> newCells;

    public PartialFillCellEvent(List<CellPair> animatedCells, List<Cell> newCells) {
        this.animatedCells = animatedCells;
        this.newCells = newCells;
    }

    public List<CellPair> getAnimatedCells() {
        return animatedCells;
    }

    public List<CellView> getNewCells(Resources resources) {
        List<CellView> cellViews = new ArrayList<>();
        for(Cell cell: newCells){
            cellViews.add(CellViewFactory.createCellView(resources, cell));
        }
        return cellViews;
    }

}
