package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class Stove extends Device {
    
    public enum HeatLevel { OFF, LOW, MEDIUM, HIGH, MAX }
    public enum DishType { NONE, SOUP, PASTA, STEAK, EGGS, STIR_FRY }
    
    private HeatLevel heatLevel = HeatLevel.OFF;
    private DishType currentDish = DishType.NONE;
    private int cookingTimeMinutes = 0;
    private int timerMinutes = 0;
    private boolean overheatedWarning = false;
    
    public Stove(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public Stove(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 2000);
        c.put(ConsumptionType.GAS, 500);
        return c;
    }

    public void setHeatLevel(HeatLevel level) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.heatLevel = level;
        System.out.println(getName() + " heat level set to " + level);
        
        if (level == HeatLevel.MAX) {
            overheatedWarning = true;
            System.out.println("WARNING: " + getName() + " is at MAX heat! Monitor closely.");
        } else {
            overheatedWarning = false;
        }
    }

    public void startCooking(DishType dish, int minutes) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.currentDish = dish;
        this.timerMinutes = minutes;
        this.cookingTimeMinutes = 0;
        
        HeatLevel recommendedHeat = getRecommendedHeat(dish);
        setHeatLevel(recommendedHeat);
        
        System.out.println("Started cooking " + dish + " for " + minutes + " minutes");
    }

    private HeatLevel getRecommendedHeat(DishType dish) {
        switch (dish) {
            case SOUP: return HeatLevel.LOW;
            case PASTA: return HeatLevel.MEDIUM;
            case EGGS: return HeatLevel.MEDIUM;
            case STEAK: return HeatLevel.HIGH;
            case STIR_FRY: return HeatLevel.HIGH;
            default: return HeatLevel.OFF;
        }
    }

    public void tick() {
        if (currentDish != DishType.NONE && timerMinutes > 0) {
            cookingTimeMinutes++;
            if (cookingTimeMinutes >= timerMinutes) {
                System.out.println(currentDish + " is ready! Turning off " + getName());
                finishCooking();
            }
        }
    }

    public void finishCooking() {
        System.out.println("Finished cooking " + currentDish);
        currentDish = DishType.NONE;
        heatLevel = HeatLevel.OFF;
        timerMinutes = 0;
        cookingTimeMinutes = 0;
        turnOff();
    }

    public HeatLevel getHeatLevel() { return heatLevel; }
    public DishType getCurrentDish() { return currentDish; }
    public boolean isOverheated() { return overheatedWarning; }
    public int getRemainingTime() { return Math.max(0, timerMinutes - cookingTimeMinutes); }
}
