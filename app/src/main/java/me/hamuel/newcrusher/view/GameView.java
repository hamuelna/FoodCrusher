package me.hamuel.newcrusher.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import me.hamuel.newcrusher.controller.BoardAdapter;
import me.hamuel.newcrusher.controller.Controller;
import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.CellView;

import java.util.List;

public class GameView extends View{
    private BroadcastReceiver br;
    private Board board = new Board(8);
    private BoardAdapter boardAdapter = new BoardAdapter(getResources());
    private List<CellView> boardView;
    public GameView(Context context) {
        super(context);
        boardView = boardAdapter.getViewBoard(board);
        br = new Controller(board, boardAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Intent intent = new Intent();
            intent.setAction("touch");
            String[] args = {Float.toString(event.getX()),Float.toString(event.getY())};
            intent.putExtra("args", args);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            return true;
        }
        return false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (CellView cellView: boardView){
            canvas.drawRect(cellView.getCoordinate(), cellView.getPaint());
            canvas.drawBitmap(cellView.getType(), null, cellView.getCoordinate(), null);
        }
        super.onDraw(canvas);
    }

}
