package cz.cvut.omo.event;

import cz.cvut.omo.model.House;

public class WaterLeakHandler extends EventHandler {
    private final House house;
    
    public WaterLeakHandler(House house) {
        this.house = house;
    }
    
    @Override
    protected boolean canHandle(Event event) {
        return event.getType() == EventType.WATER_LEAK;
    }

    @Override
    protected void doHandle(Event event) {
        System.out.println("Water leak handled by WaterLeakHandler");
    }
}
