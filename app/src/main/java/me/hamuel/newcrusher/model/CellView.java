package me.hamuel.newcrusher.model;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class CellView {
    private AnimatableRectF coordinate;
    private Paint paint;
    private Bitmap type;

    public CellView(AnimatableRectF coordinate, Paint paint, Bitmap type) {
        this.coordinate = coordinate;
        this.paint = paint;
        this.type = type;
    }

    public AnimatableRectF getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(AnimatableRectF coordinate) {
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
