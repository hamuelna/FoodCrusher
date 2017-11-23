package me.hamuel.newcrusher.view;

import android.animation.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import me.hamuel.newcrusher.event.*;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View{

    public GameView(Context context) {
        super(context);
    }
    List<CellView> boardView;
    private Coordinate firstCellCoordinate;
    private CellView firstCell;
    private boolean isClickOnce = false;
    private boolean isProcessing = false;


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if(boardView != null){
            for(CellView cellView: boardView){
                canvas.drawRect(cellView.getCoordinate(), cellView.getPaint());
                canvas.drawRect(cellView.getCoordinate(), cellView.getBorderPaint());
                canvas.drawBitmap(cellView.getType(),null,cellView.getCoordinate(), null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            for (CellView cellView: boardView){
                if(cellView.getCoordinate().contains(event.getX(), event.getY())){
                    System.out.println("You have click on a cell");
                    if(!isClickOnce){
                        RectF c = cellView.getCoordinate();
                        firstCellCoordinate = new Coordinate(c.left, c.top, c.right, c.bottom);
                        firstCell = cellView;
                        cellView.click();
                        invalidate();
                        isClickOnce = true;
                    }else{
                        RectF c = cellView.getCoordinate();
                        Coordinate secondCell = new Coordinate(c.left, c.top, c.right, c.bottom);
                        isClickOnce = false;
                        firstCell.unClick();
                        invalidate();
                        EventBus.getDefault().post(new MoveCellEvent(firstCellCoordinate, secondCell));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Subscribe
    public void onFillEvent(FillCellEvent fillCellEvent){
        boardView = fillCellEvent.getCellView(getResources());
        invalidate();
    }

    private CellView findCellView(Coordinate coordinate){
        for(CellView cellView: boardView){
            if(coordinate.equalRectF(cellView.getCoordinate())){
                return cellView;
            }
        }
        return null;
    }

    @Subscribe
    public void onAnimateEvent(final AnimateCellEvent animateCellEvent){
        List<Animator> animations = new ArrayList<>();
        System.out.println("starting backend send command animation");
        System.out.println(animateCellEvent.getCellMoves());
        for(CellPair cellPair: animateCellEvent.getCellMoves()){
            CellView from = findCellView(cellPair.getFrom().getCoordinate());
            Coordinate toCoordinate = cellPair.getTo().getCoordinate();
            ObjectAnimator animateLeft = ObjectAnimator.ofFloat(from.getCoordinate(), "left",from.getCoordinate().left, toCoordinate.getLeft());
            ObjectAnimator animateRight = ObjectAnimator.ofFloat(from.getCoordinate(), "right", from.getCoordinate().right, toCoordinate.getRight());
            ObjectAnimator animateTop = ObjectAnimator.ofFloat(from.getCoordinate(), "top", from.getCoordinate().top, toCoordinate.getTop());
            ObjectAnimator animateBottom = ObjectAnimator.ofFloat(from.getCoordinate(), "bottom", from.getCoordinate().bottom, toCoordinate.getBottom());
            animateLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            animateRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            animateTop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            animateBottom.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            animations.add(animateLeft);
            animations.add(animateRight);
            animations.add(animateTop);
            animations.add(animateBottom);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animations);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(animateCellEvent.getEventName().equals("swap")){
                    System.out.println("finish swap tell the backend that the event have ended");
                    EventBus.getDefault().post(new AnimationEndEvent("end swap"));
                }else{
                    EventBus.getDefault().post(new AnimationEndEvent("end collapse"));
                }
            }
        });
        animatorSet.start();
    }

    @Subscribe
    public void onRemoveEvent(RemoveCellEvent removeCellEvent){
        System.out.println("back notify the front to remove below are remove cells");
        for(Coordinate coordinate: removeCellEvent.getCellToBeRemove()){
            System.out.println(coordinate);
            CellView toBeRemove = null;
            for(CellView cellView: boardView){
                if(coordinate.equalRectF(cellView.getCoordinate())){
                    toBeRemove = cellView;
                }
            }
            boardView.remove(toBeRemove);
        }
        invalidate();

    }
}
