package me.hamuel.newcrusher.event;

import me.hamuel.newcrusher.model.CellPair;

import java.util.List;

/**
 * This class is when the backend send a message to the frontend
 * which cell that need to be animate such as a cell need to move
 * to where
 */
public class AnimateCellEvent {
    private List<CellPair> cellMoves;
    private String eventName;

    public AnimateCellEvent(List<CellPair> cellMoves, String eventName) {
        this.cellMoves = cellMoves;
        this.eventName = eventName;
    }

    public List<CellPair> getCellMoves() {
        return cellMoves;
    }

    public String getEventName() {
        return eventName;
    }
}
