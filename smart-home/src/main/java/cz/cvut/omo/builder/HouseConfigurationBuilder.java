package cz.cvut.omo.builder;

import cz.cvut.omo.model.Floor;
import cz.cvut.omo.model.House;
import cz.cvut.omo.model.Room;

public class HouseConfigurationBuilder {

    private House house;

    public void createHouse(String id, String name) {
        this.house = new House(id, name);
    }

    public void buildFloor(String floorId, int number) {
        Floor floor = new Floor(floorId, number);
        house.addFloor(floor);
    }

    public void buildRoom(String floorId, String roomId, String roomName) {
        Floor targetFloor = house.getFloors().stream()
                .filter(f -> f.getId().equals(floorId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Floor not found: " + floorId));

        Room room = new Room(roomId, roomName);
        targetFloor.addRoom(room);
    }

    
    public House getResult() {
        return house;
    }
}
