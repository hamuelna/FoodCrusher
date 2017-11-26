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
import me.hamuel.newcrusher.factory.MyAnimatorUtils;
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
        invalidate();
    }



    @Subscribe
    public void onAnimateEvent(final AnimateCellEvent animateCellEvent){
        AnimatorSet animatorSet = MyAnimatorUtils.animateRect(this, animateCellEvent.getCellMoves(), boardView);
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
    public void onRemoveEvent(RemoveCellEvent removeCellEvent){
        //might add animation to scale down in the future
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
        EventBus.getDefault().post(new AnimationEndEvent("end destroy"));
    }

    @Subscribe
    public void onPartialFillCellEvent(PartialFillCellEvent partialFillCellEvent){
        System.out.println("onPartialFill was call");
        boardView.addAll(partialFillCellEvent.getNewCells(getResources()));
        AnimatorSet animatorSet = MyAnimatorUtils.animateRect(this, partialFillCellEvent.getAnimatedCells(), boardView);
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
