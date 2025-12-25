package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class DishWasher extends Device {
    
    public enum WashProgram { 
        QUICK(30, 40, 10),           
        NORMAL(60, 55, 15),          
        INTENSIVE(90, 70, 20),       
        ECO(120, 45, 8),             
        GLASS(45, 40, 12);           
        
        final int durationMinutes;
        final int temperatureCelsius;
        final int waterLiters;
        
        WashProgram(int duration, int temp, int water) {
            this.durationMinutes = duration;
            this.temperatureCelsius = temp;
            this.waterLiters = water;
        }
    }
    
    private int loadedDishes = 0;
    private int maxCapacity = 12;
    private WashProgram currentProgram = null;
    private boolean isRunning = false;
    private int remainingMinutes = 0;
    private int detergentLevel = 100;
    private int rinseAidLevel = 100;
    private int totalCyclesRun = 0;
    
    public DishWasher(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public DishWasher(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 1800);
        c.put(ConsumptionType.WATER, 15);
        return c;
    }

    public void loadDishes(int count) {
        if (isRunning) {
            System.out.println("Cannot load dishes while " + getName() + " is running!");
            return;
        }
        int newLoad = loadedDishes + count;
        if (newLoad > maxCapacity) {
            System.out.println(getName() + " capacity exceeded! Max: " + maxCapacity + " sets");
            loadedDishes = maxCapacity;
        } else {
            loadedDishes = newLoad;
            System.out.println("Dishes Loaded " + count + " dish sets. Total: " + loadedDishes + "/" + maxCapacity);
        }
    }

    public void unloadDishes() {
        if (isRunning) {
            System.out.println("Cannot unload while " + getName() + " is running!");
            return;
        }
        System.out.println(" Dishes unloaded " + loadedDishes + " clean dish sets from " + getName());
        loadedDishes = 0;
    }

    public void startWashing(WashProgram program) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        if (isRunning) {
            System.out.println(getName() + " is already running!");
            return;
        }
        if (loadedDishes == 0) {
            System.out.println(getName() + " is empty! Load dishes first.");
            return;
        }
        if (detergentLevel < 10) {
            System.out.println(getName() + " needs detergent refill!");
            return;
        }
        
        currentProgram = program;
        isRunning = true;
        remainingMinutes = program.durationMinutes;
        detergentLevel -= 10;
        rinseAidLevel -= 5;
        
        System.out.println(getName() + " starting " + program + " cycle:");
        System.out.println("   Duration: " + program.durationMinutes + " min");
        System.out.println("   Temperature: " + program.temperatureCelsius + "°C");
        System.out.println("   Water usage: " + program.waterLiters + "L");
    }

    public void tick() {
        if (!isRunning || remainingMinutes <= 0) return;
        
        remainingMinutes--;
        if (remainingMinutes == 0) {
            finishWashing();
        } else if (remainingMinutes % 10 == 0) {
            System.out.println(getName() + ": " + remainingMinutes + " minutes remaining...");
        }
    }

    private void finishWashing() {
        isRunning = false;
        totalCyclesRun++;
        System.out.println(getName() + " finished " + currentProgram + " cycle!");
        System.out.println(loadedDishes + " dish sets are now clean. Remember to unload!");
        currentProgram = null;
        
        checkSupplies();
    }

    public void cancelWashing() {
        if (!isRunning) {
            System.out.println(getName() + " is not running.");
            return;
        }
        isRunning = false;
        remainingMinutes = 0;
        System.out.println(getName() + " cycle cancelled");
    }

    public void refillDetergent() {
        detergentLevel = 100;
        System.out.println(getName() + " detergent refilled");
    }

    public void refillRinseAid() {
        rinseAidLevel = 100;
        System.out.println(getName() + " rinse aid refilled");
    }

    private void checkSupplies() {
        if (detergentLevel < 20) {
            System.out.println(getName() + " detergent low: " + detergentLevel + "%");
        }
        if (rinseAidLevel < 20) {
            System.out.println(getName() + " rinse aid low: " + rinseAidLevel + "%");
        }
    }

    public void printStatus() {
        System.out.println(getName() + " status:");
        System.out.println("   Loaded: " + loadedDishes + "/" + maxCapacity + " dish sets");
        System.out.println("   Running: " + (isRunning ? currentProgram + " (" + remainingMinutes + " min left)" : "No"));
        System.out.println("   Detergent: " + detergentLevel + "%");
        System.out.println("   Rinse aid: " + rinseAidLevel + "%");
        System.out.println("   Total cycles: " + totalCyclesRun);
    }

    public boolean isRunning() { return isRunning; }
    public int getRemainingMinutes() { return remainingMinutes; }
    public int getLoadedDishes() { return loadedDishes; }
}
