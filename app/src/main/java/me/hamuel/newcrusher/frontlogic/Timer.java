package me.hamuel.newcrusher.frontlogic;

import org.greenrobot.eventbus.EventBus;

import me.hamuel.newcrusher.event.GameOverEvent;
import me.hamuel.newcrusher.event.TimeTickEvent;

/**
 * Created by Hamuel on 11/27/17.
 */

public class Timer {
    private int timeLimit; //unit in seconds
    private int maxTimeLimit;

    public Timer(int timeLimit) {
        this.timeLimit = timeLimit;
        this.maxTimeLimit = timeLimit;
    }

    public void startTimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeLimit > 0){
                    timeLimit--;
                    try {
                        Thread.sleep(1000);
                        EventBus.getDefault().post(new TimeTickEvent((float)timeLimit/(float)maxTimeLimit));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Still sending gameOver");
                EventBus.getDefault().post(new GameOverEvent());
            }
        }).start();
    }

    public void restartTime(){
        timeLimit = maxTimeLimit;
        startTimer();
    }

}
