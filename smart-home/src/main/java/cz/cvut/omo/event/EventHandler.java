package cz.cvut.omo.event;


public abstract class EventHandler {
    protected EventHandler nextHandler;

    public void setNext(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(Event event) {
        if (canHandle(event)) {
            doHandle(event);
            event.setHandledBy(this.getClass().getSimpleName());
        } else if (nextHandler != null) {
            nextHandler.handle(event);
        } else {
            System.out.println("Event reached end of chain: " + event.getType() + " (Unhandled)");
        }
    }

    protected abstract boolean canHandle(Event event);
    protected abstract void doHandle(Event event);
}