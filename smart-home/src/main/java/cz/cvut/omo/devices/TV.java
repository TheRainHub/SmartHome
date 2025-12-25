package cz.cvut.omo.devices;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.ActiveState;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;

import java.util.HashMap;
import java.util.Map;

public class TV extends Device {
    
    public TV(String name) {
        super(
            name,
            100.0f,
            new IdleState(),
            createConsumption()
        );
    }
    
    public TV(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 150);
        return c;
    }

    public void changeChannel(int channel) {
        if(getState() instanceof ActiveState) {
            System.out.println("Channel changed to " + channel);
        }else {
            System.out.println("TV is off, turn it on first");
        }
    }

}
