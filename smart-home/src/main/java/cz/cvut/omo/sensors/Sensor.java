package cz.cvut.omo.sensors;

import java.util.ArrayList;
import java.util.List;
import cz.cvut.omo.observer.Observer;
import cz.cvut.omo.event.Event;


public abstract class Sensor {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    public abstract void checkStatus();
}
