package me.hamuel.newcrusher.event;

/**
 * Created by Hamuel on 11/27/17.
 */

public class TimeTickEvent {
    private float timePercent;

    public TimeTickEvent(float timePercent) {
        this.timePercent = timePercent;
    }

    public float getTimePercent() {
        return timePercent;
    }
}
