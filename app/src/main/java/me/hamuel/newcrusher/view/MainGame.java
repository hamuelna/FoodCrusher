package me.hamuel.newcrusher.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.hamuel.newcrusher.model.Board;
import org.greenrobot.eventbus.EventBus;

public class MainGame extends AppCompatActivity {

    private View view;
    private Board board;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(board);
        EventBus.getDefault().unregister(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(board);
        EventBus.getDefault().register(view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(getApplicationContext());
        board = new Board(8);
        setContentView(view);

    }


}
