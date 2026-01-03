package cz.cvut.omo.event;

import cz.cvut.omo.event.Event;
import cz.cvut.omo.event.EventType;
import cz.cvut.omo.model.House;
import cz.cvut.omo.devices.SmartWindows;

public class WeatherAlertHandler extends EventHandler {
    private final House house;

    public WeatherAlertHandler(House house) {
        this.house = house;
    }
    
    @Override
    protected boolean canHandle(Event event) {
        return event.getType() == EventType.WEATHER_ALERT;
    }

    @Override
    protected void doHandle(Event event) {
        System.out.println("Weather alert! Closing all windows...");
        house.getDevices().stream()
                .filter(d -> d instanceof SmartWindows)
                .map(d -> (SmartWindows) d)
                .forEach(SmartWindows::close);
    }
}
