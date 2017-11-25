package me.hamuel.newcrusher.factory;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MyAnimatorUtils {
    public static AnimatorSet animateRect(final View view, List<CellPair> cellPairs, List<CellView> boardView){
        List<Animator> animations = new ArrayList<>();
        for(CellPair cellPair: cellPairs){
            CellView from = findCellView(cellPair.getFrom().getCoordinate(), boardView);
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

    private static CellView findCellView(Coordinate coordinate, List<CellView> cellViews){
        for(CellView cellView: cellViews){
            if(coordinate.equalRectF(cellView.getCoordinate())){
                return cellView;
            }
        }
        return null;
    }
}
