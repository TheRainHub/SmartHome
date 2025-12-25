package cz.cvut.omo.sport;

public class SportEquipmentPool {
    private static SportEquipmentPool instance;

    private SportEquipmentPool() {}

    public static SportEquipmentPool getInstance() {
        if (instance == null) {
            instance = new SportEquipmentPool();
        }
        return instance;
    }
}