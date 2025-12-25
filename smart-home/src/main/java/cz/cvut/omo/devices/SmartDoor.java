package cz.cvut.omo.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.Resident;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class SmartDoor extends Device {
    
    public record AccessLogEntry(String residentName, String action, long timestamp) {}
    
    private boolean isLocked = true;
    private boolean isOpen = false;
    private String accessCode = "0000";
    private List<AccessLogEntry> accessLog = new ArrayList<>();
    private List<String> authorizedResidents = new ArrayList<>();
    private boolean autoLock = true;
    private int autoLockDelaySeconds = 30;
    
    public SmartDoor(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public SmartDoor(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 10);
        return c;
    }

    public boolean unlock(String code) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is offline. Cannot unlock.");
            return false;
        }
        
        if (code.equals(accessCode)) {
            isLocked = false;
            System.out.println(getName() + " unlocked with code");
            logAccess("Code", "unlock");
            return true;
        } else {
            System.out.println(getName() + " wrong code!");
            logAccess("Unknown", "failed_unlock_attempt");
            return false;
        }
    }

    public boolean unlock(Resident resident) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is offline. Cannot unlock.");
            return false;
        }
        
        if (authorizedResidents.contains(resident.getName()) || authorizedResidents.isEmpty()) {
            isLocked = false;
            System.out.println(getName() + " unlocked by " + resident.getName());
            logAccess(resident.getName(), "unlock");
            return true;
        } else {
            System.out.println(resident.getName() + " is not authorized to unlock " + getName());
            logAccess(resident.getName(), "unauthorized_attempt");
            return false;
        }
    }

    public void lock() {
        isLocked = true;
        isOpen = false;
        System.out.println(getName() + " locked");
    }

    public void open(Resident resident) {
        if (isLocked) {
            System.out.println(getName() + " is locked. Unlock first!");
            return;
        }
        isOpen = true;
        System.out.println(getName() + " opened by " + resident.getName());
        logAccess(resident.getName(), "enter");
    }

    public void close() {
        isOpen = false;
        System.out.println(getName() + " closed");
        if (autoLock) {
            System.out.println("   (Will auto-lock in " + autoLockDelaySeconds + " seconds)");
        }
    }

    public void setAccessCode(String oldCode, String newCode) {
        if (oldCode.equals(accessCode)) {
            accessCode = newCode;
            System.out.println(getName() + " access code changed");
        } else {
            System.out.println(" Wrong old code. Cannot change access code.");
        }
    }

    public void addAuthorizedResident(String name) {
        if (!authorizedResidents.contains(name)) {
            authorizedResidents.add(name);
            System.out.println(name + " added to " + getName() + " authorized list");
        }
    }

    public void removeAuthorizedResident(String name) {
        authorizedResidents.remove(name);
        System.out.println(name + " removed from " + getName() + " authorized list");
    }

    private void logAccess(String residentName, String action) {
        accessLog.add(new AccessLogEntry(residentName, action, System.currentTimeMillis()));
    }

    public void printAccessLog() {
        System.out.println("Access log for " + getName() + ":");
        if (accessLog.isEmpty()) {
            System.out.println("   (No entries)");
            return;
        }
        for (AccessLogEntry entry : accessLog) {
            System.out.println("   " + entry.residentName() + " - " + entry.action());
        }
    }

    public void setAutoLock(boolean enabled, int delaySeconds) {
        this.autoLock = enabled;
        this.autoLockDelaySeconds = delaySeconds;
        System.out.println(getName() + " auto-lock: " + (enabled ? "ON (" + delaySeconds + "s)" : "OFF"));
    }

    public boolean isLocked() { return isLocked; }
    public boolean isOpen() { return isOpen; }
    public List<AccessLogEntry> getAccessLog() { return new ArrayList<>(accessLog); }
}
