package cz.cvut.omo.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class Refrigerator extends Device {
    
    public record FoodItem(String name, int quantity, int expiresInDays) {}
    
    private int fridgeTemperature = 4;
    private int freezerTemperature = -18;
    private boolean doorOpen = false;
    private List<FoodItem> fridgeContents = new ArrayList<>();
    private List<FoodItem> freezerContents = new ArrayList<>();
    private int doorOpenWarningSeconds = 60;
    private long doorOpenedTime = 0;
    
    public Refrigerator(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public Refrigerator(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 150);
        return c;
    }

    public void openDoor() {
        doorOpen = true;
        doorOpenedTime = System.currentTimeMillis();
        System.out.println(getName() + " door opened");
    }

    public void closeDoor()
 {
        doorOpen = false;
        System.out.println(getName() + " door closed");
    }

    public void checkDoorWarning() {
        if (doorOpen) {
            long openDuration = (System.currentTimeMillis() - doorOpenedTime) / 1000;
            if (openDuration > doorOpenWarningSeconds) {
                System.out.println(getName() + " door has been open for " + openDuration + " seconds!");
            }
        }
    }

    public void storeFood(String name, int quantity, int expiresInDays, boolean inFreezer) {
        FoodItem item = new FoodItem(name, quantity, expiresInDays);
        if (inFreezer) {
            freezerContents.add(item);
            System.out.println("Stored " + quantity + "x " + name + " in freezer (expires in " + expiresInDays + " days)");
        } else {
            fridgeContents.add(item);
            System.out.println("Stored " + quantity + "x " + name + " in fridge (expires in " + expiresInDays + " days)");
        }
    }

    public boolean takeFood(String name, boolean fromFreezer) {
        List<FoodItem> contents = fromFreezer ? freezerContents : fridgeContents;
        Iterator<FoodItem> it = contents.iterator();
        while (it.hasNext()) {
            FoodItem item = it.next();
            if (item.name().equalsIgnoreCase(name)) {
                if (item.quantity() > 1) {
                    contents.set(contents.indexOf(item), 
                        new FoodItem(item.name(), item.quantity() - 1, item.expiresInDays()));
                } else {
                    it.remove();
                }
                System.out.println("Took " + name + " from " + (fromFreezer ? "freezer" : "fridge"));
                return true;
            }
        }
        System.out.println(name + " not found in " + (fromFreezer ? "freezer" : "fridge"));
        return false;
    }

    public void checkExpiry() {
        System.out.println("Checking expiry dates in " + getName() + ":");
        
        boolean foundExpiring = false;
        for (FoodItem item : fridgeContents) {
            if (item.expiresInDays() <= 2) {
                System.out.println("   " + item.name() + " expires in " + item.expiresInDays() + " days!");
                foundExpiring = true;
            }
        }
        for (FoodItem item : freezerContents) {
            if (item.expiresInDays() <= 7) {
                System.out.println("   " + item.name() + " (freezer) expires in " + item.expiresInDays() + " days!");
                foundExpiring = true;
            }
        }
        
        if (!foundExpiring) {
            System.out.println("  All items are fresh!");
        }
    }

    public void advanceDay() {
        List<FoodItem> newFridgeContents = new ArrayList<>();
        List<FoodItem> newFreezerContents = new ArrayList<>();
        
        for (FoodItem item : fridgeContents) {
            if (item.expiresInDays() > 1) {
                newFridgeContents.add(new FoodItem(item.name(), item.quantity(), item.expiresInDays() - 1));
            } else {
                System.out.println(item.name() + " has expired and was removed");
            }
        }
        
        for (FoodItem item : freezerContents) {
            if (item.expiresInDays() > 1) {
                newFreezerContents.add(new FoodItem(item.name(), item.quantity(), item.expiresInDays() - 1));
            } else {
                System.out.println(item.name() + " (freezer) has expired and was removed");
            }
        }
        
        fridgeContents = newFridgeContents;
        freezerContents = newFreezerContents;
    }

    public void setFridgeTemperature(int celsius) {
        if (celsius < 1 || celsius > 8) {
            System.out.println("Fridge temperature must be between 1°C and 8°C");
            return;
        }
        this.fridgeTemperature = celsius;
        System.out.println(getName() + " fridge set to " + celsius + "°C");
    }

    public void setFreezerTemperature(int celsius) {
        if (celsius < -24 || celsius > -14) {
            System.out.println("Freezer temperature must be between -24°C and -14°C");
            return;
        }
        this.freezerTemperature = celsius;
        System.out.println(getName() + " freezer set to " + celsius + "°C");
    }

    public void printContents() {
        System.out.println(getName() + " contents:");
        System.out.println("   Fridge (" + fridgeTemperature + "°C):");
        if (fridgeContents.isEmpty()) {
            System.out.println("      (empty)");
        } else {
            for (FoodItem item : fridgeContents) {
                System.out.println("      " + item.quantity() + "x " + item.name() + " (expires in " + item.expiresInDays() + "d)");
            }
        }
        System.out.println("  Freezer (" + freezerTemperature + "°C):");
        if (freezerContents.isEmpty()) {
            System.out.println("      (empty)");
        } else {
            for (FoodItem item : freezerContents) {
                System.out.println("      " + item.quantity() + "x " + item.name() + " (expires in " + item.expiresInDays() + "d)");
            }
        }
    }

    public List<String> getShoppingList() {
        List<String> neededItems = new ArrayList<>();
        // could be extended with inventory tracking
        if (fridgeContents.size() < 3) {
            System.out.println(getName() + " is getting empty. Consider shopping!");
        }
        return neededItems;
    }

    public boolean isDoorOpen() { return doorOpen; }
    public int getFridgeTemperature() { return fridgeTemperature; }
    public int getFreezerTemperature() { return freezerTemperature; }
    public List<FoodItem> getFridgeContents() { return new ArrayList<>(fridgeContents); }
    public List<FoodItem> getFreezerContents() { return new ArrayList<>(freezerContents); }
}
