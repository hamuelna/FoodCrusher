package me.hamuel.newcrusher.event;

public class RegisterEvent {
    private String subscriber;

    public RegisterEvent(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriber() {
        return subscriber;
    }
}
