package me.hamuel.newcrusher.event;

import android.content.res.Resources;
import android.graphics.*;
import me.hamuel.newcrusher.R;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class FillCellEvent {
    public FillCellEvent(List<Cell> board) {
        this.board = board;
    }

    List<Cell> board;

    public List<CellView> getCellView(Resources resources) {
        List<CellView> boardView = new ArrayList<>();
        for(Cell cell: board){
            boardView.add(cellConverter(resources, cell));
        }
        return boardView;
    }

    private CellView cellConverter(Resources resources, Cell cell) {
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
