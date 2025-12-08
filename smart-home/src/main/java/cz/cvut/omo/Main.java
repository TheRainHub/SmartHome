package cz.cvut.omo;

import cz.cvut.omo.builder.HouseConfigurationBuilder;
import cz.cvut.omo.model.House;

public class Main {
    public static void main(String[] args) {

        HouseConfigurationBuilder builder = new HouseConfigurationBuilder();
        builder.createHouse("house-01", "Smart Home");

        builder.buildFloor("floor-1", 1);
        builder.buildRoom("floor-1", "room-1", "Living Room");

        House house = builder.getResult();

        System.out.println("House created: " + house.getName());
        System.out.println("Floors: " + house.getFloors().size());
        System.out.println("Rooms on floor-1: " + house.getFloors().get(0).getRooms().size());
    }
}
