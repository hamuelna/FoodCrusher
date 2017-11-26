package me.hamuel.newcrusher.event;

public class RestartGameEvent {
    private String eventChoice;

    public RestartGameEvent(String eventChoice) {
        this.eventChoice = eventChoice;
    }

    public String getEventChoice() {
        return eventChoice;
    }
}
