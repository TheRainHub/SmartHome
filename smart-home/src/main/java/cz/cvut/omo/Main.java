package cz.cvut.omo;

import cz.cvut.omo.builder.HouseConfigurationBuilder;
import cz.cvut.omo.devices.TV;
import cz.cvut.omo.model.House;
import cz.cvut.omo.residents.*;
import cz.cvut.omo.sport.Bicycle;
import cz.cvut.omo.sport.SportEquipmentPool;
import cz.cvut.omo.factory.PersonFactory;
import cz.cvut.omo.factory.AnimalFactory;


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

        System.out.println("\n===== Testing Factory Pattern =====");
        
        Person father = PersonFactory.createPerson("1", "John", FamilyRole.FATHER);
        Person mother = PersonFactory.createPerson("2", "Mary", FamilyRole.MOTHER);
        Person son = PersonFactory.createPerson("3", "Tommy", FamilyRole.SON);
        Person daughter = PersonFactory.createPerson("4", "Anna", FamilyRole.DAUGHTER);
        
        System.out.println("Created via PersonFactory:");
        System.out.println("  - " + father.getName() + " (repair skill: " + father.getRepairSkill() + ")");
        System.out.println("  - " + mother.getName() + " (repair skill: " + mother.getRepairSkill() + ")");
        System.out.println("  - " + son.getName() + " (repair skill: " + son.getRepairSkill() + ")");
        System.out.println("  - " + daughter.getName() + " (repair skill: " + daughter.getRepairSkill() + ")");
        
        Animal dog = AnimalFactory.createAnimal("5", "Bobik", AnimalRole.DOG);
        Animal cat = AnimalFactory.createAnimal("6", "Mursik", AnimalRole.CAT);
        
        System.out.println("\nCreated via AnimalFactory:");
        System.out.println("  - " + dog.getName());
        System.out.println("  - " + cat.getName());

        System.out.println("\n===== Testing TV with Factory-created residents =====");
        TV tv = new TV("Samsung Hall");

        tv.use((Child) son);
        tv.use((Child) son);
        tv.use((Father) father);
        tv.use((Father) father);

        tv.fix((Child) son);
        tv.fix((Father) father);
        System.out.println("\n==================================");

        System.out.println("\n===== Testing Sport Equipment Pool =====");
        
        SportEquipmentPool garage = SportEquipmentPool.getInstance();
        
        Bicycle bike1 = garage.getEquipment(Bicycle.class); 
        
        Bicycle bike2 = garage.getEquipment(Bicycle.class);
        
        Bicycle bike3 = garage.getEquipment(Bicycle.class);
        garage.returnEquipment(bike1);
        
        Bicycle bike4 = garage.getEquipment(Bicycle.class);

        garage.printPool();
        //Garage: No available Bicycle
        System.out.println("\n==================================");
    }
}