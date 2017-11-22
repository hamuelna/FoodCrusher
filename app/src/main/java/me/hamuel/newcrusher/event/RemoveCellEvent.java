package me.hamuel.newcrusher.event;

import me.hamuel.newcrusher.model.Coordinate;

import java.util.List;

/**
 * The backend tell the frontend which cell need to remove from the view
 */
public class RemoveCellEvent {
    List<Coordinate> cellToBeRemove;

    public RemoveCellEvent(List<Coordinate> cellToBeRemove) {
        this.cellToBeRemove = cellToBeRemove;
    }

    public List<Coordinate> getCellToBeRemove() {
        return cellToBeRemove;
    }
}
