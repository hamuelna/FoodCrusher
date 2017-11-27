package me.hamuel.newcrusher.event;

import android.content.res.Resources;

import me.hamuel.newcrusher.frontlogic.CellViewFactory;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellView;

import java.util.ArrayList;
import java.util.List;

public class FillCellEvent {
    public FillCellEvent(List<Cell> board) {
        this.board = board;
    }

    private List<Cell> board;


    public List<CellView> getCellView(Resources resources) {
        List<CellView> boardView = new ArrayList<>();
        for(Cell cell: board){
            boardView.add(cellConverter(resources, cell));
        }
        return boardView;
    }

    private CellView cellConverter(Resources resources, Cell cell) {
        return CellViewFactory.createCellView(resources, cell);
    }
}
