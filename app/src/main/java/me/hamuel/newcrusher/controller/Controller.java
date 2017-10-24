package me.hamuel.newcrusher.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import me.hamuel.newcrusher.model.Board;

import java.util.HashMap;
import java.util.Map;

public class Controller extends BroadcastReceiver {

    private Board board;
    private BoardAdapter boardAdapter;

    public Controller(Board board, BoardAdapter boardAdapter) {
        this.board = board;
        this.boardAdapter = boardAdapter;
    }

    private Map<String, Executable> commands = new HashMap<String, Executable>(){{
        put("touch", new Touch());
        put("destroy", null);
        put("fall", null);
    }};
    @Override
    public void onReceive(Context context, Intent intent) {
        commands.get(intent.getAction()).execute(intent.getStringArrayExtra("args"), board, boardAdapter);
    }


}
