package cz.cvut.omo.event;

public class Event {
    private final EventType type;
    private final Object source;
    private final String message;
    private boolean handled = false;
    private String handledBy = "None";

    public Event(EventType type, Object source, String message) {
        this.type = type;
        this.source = source;
        this.message = message;
    }

    public EventType getType() {
        return type; 
    }
    public Object getSource() { return source; }
    public String getMessage() { return message; }
    
    public boolean isHandled() { return handled; }

    public void setHandledBy(String handledBy) { 
        this.handledBy = handledBy;
        this.handled = true;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Handled by: %s)", type, message, handledBy);
    }
}
