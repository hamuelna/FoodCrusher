package me.hamuel.newcrusher.event;

/**
 * Created by Hamuel on 11/27/17.
 */

public class ScoringEvent {
    private int scoreIncrease;
    private int combo;

    public ScoringEvent(int scoreIncrease, int combo) {
        this.scoreIncrease = scoreIncrease;
        this.combo = combo;
    }

    public int getScoreIncrease() {
        return scoreIncrease;
    }

    public int getCombo() {
        return combo;
    }
}
