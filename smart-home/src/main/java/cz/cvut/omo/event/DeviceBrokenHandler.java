package cz.cvut.omo.event;

import cz.cvut.omo.model.House;


public class DeviceBrokenHandler extends EventHandler {
    private final House house;
    
    public DeviceBrokenHandler(House house) {
        this.house = house;
    }
    
    @Override
    protected boolean canHandle(Event event) {
        return event.getType() == EventType.DEVICE_BROKEN;
    }

    @Override
    protected void doHandle(Event event) {
        System.out.println("Device broken handled by DeviceBrokenHandler");
    }
}
