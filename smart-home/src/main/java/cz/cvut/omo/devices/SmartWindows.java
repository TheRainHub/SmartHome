package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class SmartWindows extends Device {
    
    private boolean isOpen = false;
    private int tiltAngle = 0;
    private boolean childLock = false;
    private boolean rainSensorEnabled = true;
    
    public SmartWindows(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public SmartWindows(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 20);
        return c;
    }

    public void open() {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        if (childLock) {
            System.out.println(getName() + " is child-locked. Cannot open fully.");
            return;
        }
        this.isOpen = true;
        this.tiltAngle = 0;
        System.out.println(getName() + " opened fully");
    }

    public void close() {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.isOpen = false;
        this.tiltAngle = 0;
        System.out.println(getName() + " closed");
    }

    public void tilt(int angle) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.tiltAngle = Math.max(0, Math.min(90, angle));
        this.isOpen = false;
        System.out.println(getName() + " tilted to " + tiltAngle + "° for ventilation");
    }

    public void setChildLock(boolean enabled) {
        this.childLock = enabled;
        System.out.println(getName() + " child lock: " + (enabled ? "ENABLED" : "DISABLED"));
        if (enabled && isOpen) {
            close();
            System.out.println("Window closed for safety");
        }
    }

    public void onRainDetected() {
        if (!rainSensorEnabled || !(getState() instanceof ActiveState)) return;
        
        if (isOpen || tiltAngle > 0) {
            System.out.println("Rain detected! Auto-closing " + getName());
            close();
        }
    }

    public void onStrongWind(int windSpeedKmh) {
        if (!(getState() instanceof ActiveState)) return;
        
        if (windSpeedKmh > 50 && (isOpen || tiltAngle > 30)) {
            System.out.println("Strong wind (" + windSpeedKmh + " km/h)! Closing " + getName());
            close();
        }
    }

    public void setRainSensorEnabled(boolean enabled) {
        this.rainSensorEnabled = enabled;
        System.out.println(getName() + " rain sensor: " + (enabled ? "ON" : "OFF"));
    }

    public boolean isOpen() { return isOpen; }
    public int getTiltAngle() { return tiltAngle; }
    public boolean isChildLocked() { return childLock; }
}
