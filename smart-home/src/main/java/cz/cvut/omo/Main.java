package cz.cvut.omo;

import java.util.Random;
import cz.cvut.omo.builder.HouseConfigurationBuilder;
import cz.cvut.omo.devices.TV;
import cz.cvut.omo.model.House;
import cz.cvut.omo.residents.*;
import cz.cvut.omo.sport.Bicycle;
import cz.cvut.omo.sport.SportEquipmentPool;
import cz.cvut.omo.factory.PersonFactory;
import cz.cvut.omo.factory.AnimalFactory;
import cz.cvut.omo.devices.DishWasher;
import cz.cvut.omo.devices.Stove;
import cz.cvut.omo.event.Event;
import cz.cvut.omo.event.EventType;
import cz.cvut.omo.control.HouseController;
import cz.cvut.omo.sensors.WindSensor;
import cz.cvut.omo.sensors.MotionSensor;
import cz.cvut.omo.sensors.TemperatureSensor;


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
        System.out.println("\n==================================");

        System.out.println("\n===== Testing Event System (Chain of Responsibility + Observer) =====");
        
        house.addResident(father);
        house.addResident(mother);
        house.addResident(son);
        
        HouseController controller = new HouseController();
        controller.initChain(house);
        
        System.out.println("-> Chain initialized.");

        WindSensor windSensor = new WindSensor();
        windSensor.addObserver(controller);
        
        MotionSensor motionSensor = new MotionSensor();
        motionSensor.addObserver(controller);

        TemperatureSensor tempSensor = new TemperatureSensor();
        tempSensor.addObserver(controller);

        System.out.println("\n===== STARTING 24-HOUR SIMULATION =====");
        Random rand = new Random();

        double currentTemp = 22.0;

        for (int hour = 0; hour < 24; hour++) {
            System.out.println("\n--- TIME: " + hour + ":00 ---");

            int wind = rand.nextInt(30);
            System.out.println("[Weather] Wind: " + wind + " km/h");
            windSensor.setWindSpeed(wind);
            currentTemp += (rand.nextDouble() * 4) - 2;
            System.out.printf("[Weather] Temperature: %.1f°C%n", currentTemp);
            tempSensor.setTemperature(currentTemp);

            if (hour >= 18 && hour <= 20) {
                if (rand.nextInt(10) > 7) { 
                    System.out.println("[Life] Food run out during dinner!");
                    Event foodEvent = new Event(
                        EventType.FOOD_FINISHED, 
                        house, 
                        "Fridge is empty at dinner!"
                    );
                    controller.update(foodEvent);
                }
            }

            if (rand.nextInt(10) > 8) {
                motionSensor.setMotion(true);
            }
            house.getDevices().forEach(d -> {
                if (d instanceof DishWasher) ((DishWasher) d).tick();
                if (d instanceof Stove) ((Stove) d).tick();
            });
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n===== SIMULATION ENDED =====");
        garage.printPool();
    }
}