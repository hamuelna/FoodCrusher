package me.hamuel.newcrusher.controller;

import android.content.res.Resources;
import android.graphics.*;
import me.hamuel.newcrusher.R;
import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter {
    private Resources resources;

    public BoardAdapter(Resources resources) {
        this.resources = resources;
    }

    public List<CellView> getViewBoard(Board board) {
        Cell[][] board_ = board.getBoard();
        List<CellView> cellViews = new ArrayList<>();
        for(Cell[] cellRows: board_){
            for(Cell cell: cellRows){
                if(cell != null){
                    cellViews.add(cellConverter(cell));
                }
            }
        }
        return cellViews;
    }

    private CellView cellConverter(Cell cell) {
        Coordinate cord = cell.getCoordinate();
        Paint paint = null;
        Bitmap type = null;
        switch (cell.getType()){
            case CAKE: {
                paint = new Paint(Color.DKGRAY);
                type = BitmapFactory.decodeResource(resources, R.drawable.cakeslice);
                break;
            }
            case APPLE: {
                paint = new Paint(Color.RED);
                type = BitmapFactory.decodeResource(resources, R.drawable.apple);
                break;
            }
            case DRINK: {
                paint = new Paint(Color.BLUE);
                type = BitmapFactory.decodeResource(resources, R.drawable.drink);
                break;
            }
            case PIZZA: {
                paint = new Paint(Color.MAGENTA);
                type = BitmapFactory.decodeResource(resources, R.drawable.pizza);
                break;
            }
            case BURGER: {
                paint = new Paint(Color.YELLOW);
                type = BitmapFactory.decodeResource(resources, R.drawable.burger);
                break;
            }
        }
        return new CellView(
                new RectF(cord.getLeft(), cord.getTop(), cord.getRight(), cord.getBottom()),
                paint,
                type);
    }


}
