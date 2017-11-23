package me.hamuel.newcrusher.model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class CellView {
    private AnimatableRectF coordinate;
    private Paint paint;
    private Bitmap type;
    private Paint borderPaint;

    public CellView(AnimatableRectF coordinate, Paint paint, Bitmap type) {
        this.coordinate = coordinate;
        this.paint = paint;
        this.type = type;
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(15);
        unClick();
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

    public Paint getBorderPaint() {
        return borderPaint;
    }

    public void setType(Bitmap type) {
        this.type = type;
    }

    public void click(){
        borderPaint.setColor(Color.BLACK);
    }

    public void unClick(){
        borderPaint.setColor(Color.TRANSPARENT);
    }

    @Override
    public String toString() {
        return "CellView{" +
                "coordinate=" + coordinate +
                ", paint=" + paint +
                ", type=" + type +
                '}';
    }
}
