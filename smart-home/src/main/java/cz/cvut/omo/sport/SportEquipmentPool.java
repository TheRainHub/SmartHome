package cz.cvut.omo.sport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import cz.cvut.omo.residents.Person;

public class SportEquipmentPool {

    private static SportEquipmentPool instance;
    private Map<Class<? extends SportEquipment>, Queue<SportEquipment>> availableEquipment = new HashMap<>();
    private Map<Class<? extends SportEquipment>, Queue<SportEquipment>> inUseEquipment = new HashMap<>();
    private Map<SportEquipment, Person> borrowedBy = new HashMap<>();

    private SportEquipmentPool() {
        initPool();
    }

    public static synchronized SportEquipmentPool getInstance() {
        if (instance == null) {
            instance = new SportEquipmentPool();
        }
        return instance;
    }

    private void initPool() {
        Queue<SportEquipment> bikes = new LinkedList<>();
        bikes.add(new Bicycle("Scott bike 1"));
        bikes.add(new Bicycle("Canyon bike 2"));
        
        availableEquipment.put(Bicycle.class, bikes);

        Queue<SportEquipment> benches = new LinkedList<>();
        benches.add(new Bench("Bench 1"));
        availableEquipment.put(Bench.class, benches);

        Queue<SportEquipment> dumbbells = new LinkedList<>();
        dumbbells.add(new Dumbbells("Dumbbells 1"));
        availableEquipment.put(Dumbbells.class, dumbbells);

        Queue<SportEquipment> skis = new LinkedList<>();
        skis.add(new Ski("Ski 1"));
        availableEquipment.put(Ski.class, skis);

        Queue<SportEquipment> treadmills = new LinkedList<>();
        treadmills.add(new Treadmill("Treadmill 1"));
        availableEquipment.put(Treadmill.class, treadmills);
    }

    public <T extends SportEquipment> T getEquipment(Class<T> type) {
        Queue<SportEquipment> queue = availableEquipment.get(type);

        if (queue == null || queue.isEmpty()) {
            System.out.println("Garage: No available " + type.getSimpleName());
            return null; 
        }

        SportEquipment item = queue.poll();
        System.out.println("Garage: Given -> " + item.getName());
        
        return type.cast(item);
    }

    public <T extends SportEquipment> T borrowEquipment(Person user, Class<T> type) {
        Queue<SportEquipment> available = availableEquipment.get(type);

        if (available == null || available.isEmpty()) {
            System.out.println("Garage: No available " + type.getSimpleName() + " for " + user.getName());
            return null;
        }

        SportEquipment item = available.poll();
        
        inUseEquipment.computeIfAbsent(type, k -> new LinkedList<>()).offer(item);
        
        borrowedBy.put(item, user);
        
        System.out.println("Garage: " + user.getName() + " borrowed -> " + item.getName());
        
        return type.cast(item);
    }

    public void returnEquipment(SportEquipment item) {
        if (item == null) {
            return;
        }

        Class<? extends SportEquipment> type = item.getClass();

        Queue<SportEquipment> inUse = inUseEquipment.get(type);
        if (inUse != null) {
            inUse.remove(item);
        }
        
        Person user = borrowedBy.remove(item);
        
        Queue<SportEquipment> available = availableEquipment.get(type);
        if (available != null) {
            available.offer(item);
            String returnedBy = (user != null) ? user.getName() : "Unknown";
            System.out.println("Garage: " + returnedBy + " returned -> " + item.getName());
        }
    }

    public Person getBorrower(SportEquipment item) {
        return borrowedBy.get(item);
    }

    public void printPool() {
        System.out.println("===== Sport Equipment Pool =====");
        availableEquipment.entrySet().stream()
            .forEach(entry -> {
                String typeName = entry.getKey().getSimpleName();
                int count = entry.getValue().size();
                System.out.println(typeName + " (" + count + " available):");
                entry.getValue().stream()
                    .forEach(item -> System.out.println("  - " + item.getName()));
            });
        System.out.println("================================");
    }
}