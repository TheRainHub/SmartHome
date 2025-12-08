package cz.cvut.omo.event;

public abstract class EventHandler {
    protected EventHandler next;

    public void setNext(EventHandler next) {
        this.next = next;
    }

    public abstract void handle(Event e);
}