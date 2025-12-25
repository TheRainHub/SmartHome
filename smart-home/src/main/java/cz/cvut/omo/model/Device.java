package cz.cvut.omo.model;

import java.util.Map;

import cz.cvut.omo.state.DeviceState;

public abstract class Device {

    private final String name;

    private Room room;
    private float durability;
    private DeviceState state;
    private Resident lastUsedBy;
    private long lastUsedTime;

    private Map<ConsumptionType, Integer> consumption;
    
    public Device(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        this.name = name;
        this.durability = durability;
        this.state = state;
        this.consumption = consumption;
    }

    public Map<ConsumptionType, Integer> getConsumption() {
        return consumption;
    }

    public void turnOn(){
        state.turnOn(this);
    }

    public void turnOff(){
        state.turnOff(this);
    }

    public void use(Resident resident){
        this.lastUsedBy = resident;
        this.lastUsedTime = System.currentTimeMillis();
        state.use(this);
    }

    public void fix(Resident person){
        state.fix(this, person);
    }

    public String getName(){
        return name;
    }

    public Resident getLastUsedBy() {
        return lastUsedBy;
    }

    public void setLastUsedBy(Resident lastUsedBy) {
        this.lastUsedBy = lastUsedBy;
    }

    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }
    public void setState(DeviceState state) {
        this.state = state;
    }

    public DeviceState getState() {
        return state;
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }

    public void setConsumption(Map<ConsumptionType, Integer> consumption) {
        this.consumption = consumption;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
