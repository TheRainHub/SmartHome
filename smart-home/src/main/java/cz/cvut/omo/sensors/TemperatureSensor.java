package cz.cvut.omo.sensors;

import cz.cvut.omo.event.Event;
import cz.cvut.omo.event.EventType;

public class TemperatureSensor extends Sensor {
    private double temperature = 20.0;
    private static double COLD_TEMPERATURE = 15.0;
    private static double HOT_TEMPERATURE = 30.0;

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        checkStatus();
    }

    @Override
    public void checkStatus() {
        if (temperature > HOT_TEMPERATURE) {
            notifyObservers(new Event(
                    EventType.TEMPERATURE_ALERT, this,
                    "To hot, temperature is " + temperature
            ));
        } else if (temperature < COLD_TEMPERATURE) {
            notifyObservers(new Event(
                    EventType.TEMPERATURE_ALERT, this,
                    "To cold, temperature is " + temperature
            ));
        }
    }
}
