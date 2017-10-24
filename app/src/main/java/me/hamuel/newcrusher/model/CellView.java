package me.hamuel.newcrusher.model;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;

public class CellView {
    private RectF coordinate;
    private Paint paint;
    private Bitmap type;

    public CellView(RectF coordinate, Paint paint, Bitmap type) {
        this.coordinate = coordinate;
        this.paint = paint;
        this.type = type;
    }

    public RectF getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(RectF coordinate) {
        this.coordinate = coordinate;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Bitmap getType() {
        return type;
    }

    public void setType(Bitmap type) {
        this.type = type;
    }
}
