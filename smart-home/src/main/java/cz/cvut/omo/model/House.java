package cz.cvut.omo.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class House {

    private final String id;
    private final String name;

    private final List<Floor> floors = new ArrayList<>();
    private final List<Resident> residents = new ArrayList<>();
    private final List<Device> devices = new ArrayList<>();

    public House(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
        floor.setHouse(this);
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Floor> getFloors() { return floors; }

    public List<Resident> getResidents() { return residents; }

    public List<Device> getDevices() { return devices; }

    public String getId() { return id; }

    public String getName() { return name; }
}
