package cz.cvut.omo.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String id;
    private final String name;
    private final List<Device> devices = new ArrayList<>();
    private Floor floor;

    public Room(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public void addDevice(Device device) {
        devices.add(device);
        device.setRoom(this);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public Floor getFloor() { return floor; }
}
