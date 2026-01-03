package cz.cvut.omo.observer;

import cz.cvut.omo.event.Event;

public interface Observer {
    void update(Event event);
}
