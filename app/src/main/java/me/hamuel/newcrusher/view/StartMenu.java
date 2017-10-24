package me.hamuel.newcrusher.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.hamuel.newcrusher.R;

public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
//        setContentView(R.layout.activity_start_menu);
    }
}
