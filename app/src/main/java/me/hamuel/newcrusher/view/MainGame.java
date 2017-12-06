package me.hamuel.newcrusher.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.hamuel.newcrusher.event.GameOverEvent;
import me.hamuel.newcrusher.event.RegisterEvent;
import me.hamuel.newcrusher.event.RestartGameEvent;
import me.hamuel.newcrusher.event.UnRegisterEvent;
import me.hamuel.newcrusher.frontlogic.Timer;
import me.hamuel.newcrusher.model.Board;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainGame extends AppCompatActivity {

    private View view;
    private Board board;
    private Timer timer;
    private final int DIM = 8;
    private final int TIME_LIMIT = 300;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(board);
        EventBus.getDefault().unregister(view);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("starting again");
        timer.startTimer();
        EventBus.getDefault().register(board);
        EventBus.getDefault().register(view);
        EventBus.getDefault().register(this);
        board.initBoard();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("recreating");
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        board = new Board(DIM);
        timer = new Timer(TIME_LIMIT);
        setContentView(view);

    }

    @Subscribe
    public void onRestartGameEvent(RestartGameEvent restartGameEvent){
        System.out.println("The restart signal was sent");
        if(restartGameEvent.getEventChoice().equals("restart")){
            //actually restart the game
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }else{
            //go back to the start menu
            finish();
            Intent intent = new Intent(this, StartMenu.class);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onGameOver(GameOverEvent gameOverEvent){
        DialogFragment dialogFragment = new GameOverDialog();
        dialogFragment.show(getFragmentManager(), "gameOver");
    }

//    @Subscribe
//    public void onRegisterEvent(RegisterEvent registerEvent){
//        if (registerEvent.getSubscriber().equals("Front")){
//            EventBus.getDefault().register(view);
//        }else if(registerEvent.getSubscriber().equals("Back")){
//            EventBus.getDefault().register(board);
//        }else{
//            EventBus.getDefault().register(board);
//            EventBus.getDefault().register(view);
//        }
//    }
//
//    @Subscribe
//    public void onUnRegisterEvent(UnRegisterEvent unRegisterEvent){
//        if (unRegisterEvent.getSubscriber().equals("Front")){
//            EventBus.getDefault().unregister(view);
//        }else if(unRegisterEvent.getSubscriber().equals("Back")){
//            EventBus.getDefault().unregister(board);
//        }else{
//            EventBus.getDefault().unregister(board);
//            EventBus.getDefault().unregister(view);
//        }
//    }

}
