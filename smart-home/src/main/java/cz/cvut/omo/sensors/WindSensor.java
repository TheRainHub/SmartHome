package cz.cvut.omo.sensors;

import cz.cvut.omo.event.Event;
import cz.cvut.omo.event.EventType;


public class WindSensor extends Sensor {

    private int windSpeed = 0;
    private static final int STORM_THRESHOLD = 20;

    public void setWindSpeed(int speed) {
        this.windSpeed = speed;
        checkStatus();
    }

    @Override
    public void checkStatus() {
        if (windSpeed > STORM_THRESHOLD) {
            System.out.println("Wind sensor detects storm warning!");
            notifyObservers(new Event(EventType.WEATHER_ALERT,
                    this, "Storm warning!" + windSpeed));
        }
    }
}
