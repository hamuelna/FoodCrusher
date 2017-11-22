package me.hamuel.newcrusher.model;

import android.graphics.RectF;

public class Coordinate {
    private float left;
    private float top;
    private float right;
    private float bottom;

    public Coordinate(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public boolean equalRectF(RectF rectF){
        return (top == rectF.top) && (left == rectF.left) && (right == rectF.right) && (bottom == rectF.bottom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Float.compare(that.left, left) != 0) return false;
        if (Float.compare(that.top, top) != 0) return false;
        if (Float.compare(that.right, right) != 0) return false;
        return Float.compare(that.bottom, bottom) == 0;
    }

    @Override
    public int hashCode() {
        int result = (left != +0.0f ? Float.floatToIntBits(left) : 0);
        result = 31 * result + (top != +0.0f ? Float.floatToIntBits(top) : 0);
        result = 31 * result + (right != +0.0f ? Float.floatToIntBits(right) : 0);
        result = 31 * result + (bottom != +0.0f ? Float.floatToIntBits(bottom) : 0);
        return result;
    }
}
