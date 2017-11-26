package me.hamuel.newcrusher.view;

import android.animation.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import me.hamuel.newcrusher.event.*;
import me.hamuel.newcrusher.factory.DefaultAnimator;
import me.hamuel.newcrusher.factory.IAnimator;
import me.hamuel.newcrusher.model.CellView;
import me.hamuel.newcrusher.model.Coordinate;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private IAnimator animator;

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
        if(event.getAction() == MotionEvent.ACTION_DOWN && !isProcessing){
            for (CellView cellView: boardView){
                if(cellView.getCoordinate().contains(event.getX(), event.getY())){
                    if(!isClickOnce){
                        RectF c = cellView.getCoordinate();
                        firstCellCoordinate = new Coordinate(c.left, c.top, c.right, c.bottom);
                        firstCell = cellView;
                        cellView.click();
                        postInvalidate();
                        isClickOnce = true;
                    }else{
                        RectF c = cellView.getCoordinate();
                        Coordinate secondCell = new Coordinate(c.left, c.top, c.right, c.bottom);
                        isClickOnce = false;
                        firstCell.unClick();
                        postInvalidate();
                        isProcessing = true;
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
        animator = new DefaultAnimator(boardView, this);
        invalidate();
    }



    @Subscribe
    public void onAnimateEvent(final AnimateCellEvent animateCellEvent){
        AnimatorSet animatorSet = animator.animateRectTranslation(animateCellEvent.getCellMoves());
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
        animatorSet.setDuration(500);
    }

    @Subscribe
    public void onRemoveEvent(final RemoveCellEvent removeCellEvent){
        //add animation to scale down
        AnimatorSet animatorSet = animator.animateRectScale(removeCellEvent.getCellToBeRemove());
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for(Coordinate coordinate: removeCellEvent.getCellToBeRemove()){
                    CellView toBeRemove = null;
                    for(CellView cellView: boardView){
                        if(coordinate.equalRectF(cellView.getCoordinate())){
                            toBeRemove = cellView;
                        }
                    }
                    boardView.remove(toBeRemove);
                }
                postInvalidate();
                EventBus.getDefault().post(new AnimationEndEvent("end destroy"));
            }
        });
        animatorSet.start();
    }

    @Subscribe
    public void onPartialFillCellEvent(PartialFillCellEvent partialFillCellEvent){
        System.out.println("onPartialFill was call");
        boardView.addAll(partialFillCellEvent.getNewCells(getResources()));
        AnimatorSet animatorSet = animator.animateRectTranslation(partialFillCellEvent.getAnimatedCells());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                EventBus.getDefault().post(new AnimationEndEvent("end refill"));
            }
        });
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    @Subscribe
    public void onUnblockingEvent(ToggleBlockingEvent toggleBlockingEvent){
        isProcessing = false;
    }

    @Subscribe
    public void onGameOverEvent(GameOverEvent gameOverEvent){
        DialogFragment dialogFragment = new GameOverDialog();
        dialogFragment.show(dialogFragment.getFragmentManager(), "gameOver");
        isProcessing = true;
    }

}
