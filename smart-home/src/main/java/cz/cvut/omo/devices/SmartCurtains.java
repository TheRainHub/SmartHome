package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class SmartCurtains extends Device {
    
    private int positionPercent = 0;
    private boolean autoMode = false;
    private int morningOpenHour = 7;
    private int eveningCloseHour = 20;
    
    public SmartCurtains(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public SmartCurtains(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 15);
        return c;
    }

    public void open() {
        setPosition(100);
    }

    public void close() {
        setPosition(0);
    }

    public void setPosition(int percent) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.positionPercent = Math.max(0, Math.min(100, percent));
        String status = positionPercent == 0 ? "fully closed" : 
                       positionPercent == 100 ? "fully open" : 
                       positionPercent + "% open";
        System.out.println(getName() + " is now " + status);
    }

    public void setAutoMode(boolean enabled) {
        this.autoMode = enabled;
        System.out.println(getName() + " auto mode: " + (enabled ? "ON" : "OFF"));
        if (enabled) {
            System.out.println("   Opens at " + morningOpenHour + ":00, closes at " + eveningCloseHour + ":00");
        }
    }

    public void setSchedule(int openHour, int closeHour) {
        this.morningOpenHour = openHour;
        this.eveningCloseHour = closeHour;
        System.out.println(getName() + " schedule updated: open at " + openHour + ":00, close at " + closeHour + ":00");
    }

    public void onTimeUpdate(int currentHour) {
        if (!autoMode || !(getState() instanceof ActiveState)) return;
        
        if (currentHour == morningOpenHour && positionPercent < 100) {
            System.out.println("Good morning! Auto-opening curtains...");
            open();
        } else if (currentHour == eveningCloseHour && positionPercent > 0) {
            System.out.println("Good evening! Auto-closing curtains...");
            close();
        }
    }

    public void respondToSunlight(int lightIntensity) {
        if (!autoMode || !(getState() instanceof ActiveState)) return;
        
        if (lightIntensity > 80) {
            setPosition(30);
            System.out.println("   (Reducing glare from bright sunlight)");
        } else if (lightIntensity < 20) {
            setPosition(100);
            System.out.println("   (Opening to let in more light)");
        }
    }

    public int getPositionPercent() { return positionPercent; }
    public boolean isAutoMode() { return autoMode; }
}
