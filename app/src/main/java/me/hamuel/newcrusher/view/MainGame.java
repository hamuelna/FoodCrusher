package me.hamuel.newcrusher.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.hamuel.newcrusher.event.RegisterEvent;
import me.hamuel.newcrusher.event.RestartGameEvent;
import me.hamuel.newcrusher.event.UnRegisterEvent;
import me.hamuel.newcrusher.model.Board;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainGame extends AppCompatActivity {

    private View view;
    private Board board;
    private final int DIM = 8;

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(board);
//        EventBus.getDefault().unregister(view);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(board);
        EventBus.getDefault().register(view);
        EventBus.getDefault().register(this);
        board.initBoard();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        board = new Board(DIM);
        setContentView(view);

    }

    @Subscribe
    public void onRestartGameEvent(RestartGameEvent restartGameEvent){
        if(restartGameEvent.getEventChoice().equals("restart")){
            //actually restart the game
            view = new GameView(this);
            board = new Board(DIM);
            setContentView(view);
        }else{
            //go back to the start menu
            Intent intent = new Intent(this, StartMenu.class);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onRegisterEvent(RegisterEvent registerEvent){
        if (registerEvent.getSubscriber().equals("Front")){
            EventBus.getDefault().register(view);
        }else if(registerEvent.getSubscriber().equals("Back")){
            EventBus.getDefault().register(board);
        }else{
            EventBus.getDefault().register(board);
            EventBus.getDefault().register(view);
        }
    }

    @Subscribe
    public void onUnRegisterEvent(UnRegisterEvent unRegisterEvent){
        if (unRegisterEvent.getSubscriber().equals("Front")){
            EventBus.getDefault().unregister(view);
        }else if(unRegisterEvent.getSubscriber().equals("Back")){
            EventBus.getDefault().unregister(board);
        }else{
            EventBus.getDefault().unregister(board);
            EventBus.getDefault().unregister(view);
        }
    }

}
