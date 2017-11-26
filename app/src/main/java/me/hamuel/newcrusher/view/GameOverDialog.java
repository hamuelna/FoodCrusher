package me.hamuel.newcrusher.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import me.hamuel.newcrusher.event.RestartGameEvent;
import org.greenrobot.eventbus.EventBus;

public class GameOverDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("The Game is now over do you want to restart the game?")
                .setPositiveButton("restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new RestartGameEvent("restart"));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new RestartGameEvent("cancel"));

                    }
                });
        return builder.create();

    }
}
