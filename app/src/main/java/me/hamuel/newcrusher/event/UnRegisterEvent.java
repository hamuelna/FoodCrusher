package me.hamuel.newcrusher.event;

public class UnRegisterEvent {
    private String subscriber;

    public UnRegisterEvent(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriber() {
        return subscriber;
    }
}
