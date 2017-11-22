package me.hamuel.newcrusher.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import me.hamuel.newcrusher.R;

public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        Button buttonOne = (Button) findViewById(R.id.button);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sendMessage(v);
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
    }

}
