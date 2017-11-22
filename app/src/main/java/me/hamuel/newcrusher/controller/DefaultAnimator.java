package me.hamuel.newcrusher.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.View;
import me.hamuel.newcrusher.model.*;

import java.util.Iterator;
import java.util.List;

public class DefaultAnimator implements Animatable {
    private List<CellView> boardView;
    private View view;

    public DefaultAnimator(View view , List<CellView> boardView) {
        this.boardView = boardView;
        this.view = view;
    }

    @Override
    public void animateMoveTo(List<CellPair> cellPairs) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(500);
        final View view_ = view;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view_.invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void animateDestroy(List<Cell> cells) {
        for(Iterator<CellView> cellIterator = boardView.listIterator(); cellIterator.hasNext();){
            CellView currentCellView = cellIterator.next();
            for(Cell cell: cells){
                if(isEqual(cell, currentCellView)){
                    boardView.remove(currentCellView);
                }
            }
        }
        view.invalidate();
    }

    private boolean isEqual(Cell cell, CellView cellView){
        Coordinate coordinate = cell.getCoordinate();
        RectF viewCoordinate = cellView.getCoordinate();

        return coordinate.getBottom() == viewCoordinate.bottom && coordinate.getLeft() == viewCoordinate.left &&
                coordinate.getRight() == viewCoordinate.right && coordinate.getTop() == viewCoordinate.top;
    }

}
