package cz.cvut.omo;

import cz.cvut.omo.builder.HouseConfigurationBuilder;
import cz.cvut.omo.devices.TV;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.House;
import cz.cvut.omo.residents.Child;
import cz.cvut.omo.residents.Father;
import cz.cvut.omo.state.IdleState;

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


        TV tv = new TV("Samsung Hall");
        Father father = new Father("1", "John");
        Child child = new Child("2", "Tommy");

        tv.use(child);
        tv.use(child);
        tv.use(child);
        tv.use(child);
        tv.use(father);
        tv.use(father);
        tv.use(father);
        tv.use(father);
        tv.use(father);

        tv.fix(child);
        tv.fix(father);
    }
}
