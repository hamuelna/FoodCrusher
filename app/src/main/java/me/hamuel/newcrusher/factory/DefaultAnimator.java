package me.hamuel.newcrusher.factory;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.RectF;
import android.view.View;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class DefaultAnimator implements IAnimator{
    private List<CellView> boardView;
    private View view;

    public DefaultAnimator(List<CellView> boardView, View view) {
        this.boardView = boardView;
        this.view = view;
    }

    @Override
    public AnimatorSet animateRectTranslation(List<CellPair> cellPairs) {
        List<Animator> animations = new ArrayList<>();
        for(CellPair cellPair: cellPairs){
            CellView from = findCellView(cellPair.getFrom().getCoordinate());
            if(from != null){
                Coordinate toCoordinate = cellPair.getTo().getCoordinate();
                ObjectAnimator animateLeft = ObjectAnimator.ofFloat(from.getCoordinate(), "left",from.getCoordinate().left, toCoordinate.getLeft());
                ObjectAnimator animateRight = ObjectAnimator.ofFloat(from.getCoordinate(), "right", from.getCoordinate().right, toCoordinate.getRight());
                ObjectAnimator animateTop = ObjectAnimator.ofFloat(from.getCoordinate(), "top", from.getCoordinate().top, toCoordinate.getTop());
                ObjectAnimator animateBottom = ObjectAnimator.ofFloat(from.getCoordinate(), "bottom", from.getCoordinate().bottom, toCoordinate.getBottom());
                animateLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view.postInvalidate();
                    }
                });
                animateRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view.postInvalidate();
                    }
                });
                animateTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view.postInvalidate();
                    }
                });
                animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view.postInvalidate();
                    }
                });
                animations.add(animateLeft);
                animations.add(animateRight);
                animations.add(animateTop);
                animations.add(animateBottom);
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animations);
        return animatorSet;
    }

    @Override
    public AnimatorSet animateRectScale(List<Coordinate> cells) {
        List<Animator> animations = new ArrayList<>();
        for(Coordinate cell: cells){
            final CellView cellView = findCellView(cell);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1,0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {

                    scale(cellView.getCoordinate(), (float) valueAnimator.getAnimatedValue());
                    view.postInvalidate();
                }
            });
            animations.add(valueAnimator);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animations);
        return animatorSet;
    }

    private CellView findCellView(Coordinate coordinate){
        for(CellView cellView: boardView){
            if(coordinate.equalRectF(cellView.getCoordinate())){
                return cellView;
            }
        }
        return null;
    }

    private void scale(RectF rect, float factor){
        float diffHorizontal = (rect.right-rect.left) * Math.abs(factor-1f);
        float diffVertical = (rect.bottom-rect.top) * Math.abs(factor-1f);

        rect.top += diffVertical/2f;
        rect.bottom -= diffVertical/2f;
        rect.left += diffHorizontal/2f;
        rect.right -= diffHorizontal/2f;

    }
}
