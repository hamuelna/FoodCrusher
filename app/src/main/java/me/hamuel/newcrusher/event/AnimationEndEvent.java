package me.hamuel.newcrusher.event;

public class AnimationEndEvent {
    private String message;

    public AnimationEndEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
