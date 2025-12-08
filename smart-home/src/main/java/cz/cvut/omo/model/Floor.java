package cz.cvut.omo.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private final String id;
    private final int number;
    private final List<Room> rooms = new ArrayList<>();
    private House house;

    public Floor(String id, int number) {
        this.id = id;
        this.number = number;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setFloor(this);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getId() { return id; }

    public int getNumber() { return number; }

    public void setHouse(House house) {
        this.house = house;
    }

    public House getHouse() { return house; }
}
