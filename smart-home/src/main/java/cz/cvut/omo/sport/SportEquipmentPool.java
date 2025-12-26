package cz.cvut.omo.sport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SportEquipmentPool {

    private static SportEquipmentPool instance;

    private SportEquipmentPool() {
        initPool();
    }

    public static synchronized SportEquipmentPool getInstance() {
        if (instance == null) {
            instance = new SportEquipmentPool();
        }
        return instance;
    }

    private Map<Class<? extends SportEquipment>, Queue<SportEquipment>> availableEquipment = new HashMap<>();

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

    public void returnEquipment(SportEquipment item){
        if (item == null) {
            return;
        }

        Class<? extends SportEquipment> type = item.getClass();

        Queue<SportEquipment> queue = availableEquipment.get(type);

        if (queue != null) {
            queue.offer(item);
            System.out.println("Garage: Return -> " + item.getName());
        }
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