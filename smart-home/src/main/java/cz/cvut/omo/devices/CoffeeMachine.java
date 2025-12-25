package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class CoffeeMachine extends Device {
    
    public enum CoffeeType { ESPRESSO, AMERICANO, CAPPUCCINO, LATTE, MOCHA }
    public enum Strength { MILD, MEDIUM, STRONG, EXTRA_STRONG }
    
    private int waterLevelMl = 1500;
    private int beanLevelGrams = 500;
    private int milkLevelMl = 500;
    private Strength currentStrength = Strength.MEDIUM;
    private int cupsBrewedToday = 0;
    private boolean needsCleaning = false;
    
    private static final int MAX_WATER = 1500;
    private static final int MAX_BEANS = 500;
    private static final int MAX_MILK = 500;
    
    public CoffeeMachine(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public CoffeeMachine(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 1200);
        c.put(ConsumptionType.WATER, 50);
        return c;
    }

    public void brewCoffee(CoffeeType type) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        
        if (needsCleaning) {
            System.out.println(getName() + " needs cleaning before brewing!");
            return;
        }
        
        int waterNeeded = getWaterNeeded(type);
        int beansNeeded = getBeansNeeded(type);
        int milkNeeded = getMilkNeeded(type);
        
        if (waterLevelMl < waterNeeded) {
            System.out.println(getName() + " needs water refill!");
            return;
        }
        if (beanLevelGrams < beansNeeded) {
            System.out.println(getName() + " needs beans refill!");
            return;
        }
        if (milkLevelMl < milkNeeded) {
            System.out.println(getName() + " needs milk refill!");
            return;
        }
        
        waterLevelMl -= waterNeeded;
        beanLevelGrams -= beansNeeded;
        milkLevelMl -= milkNeeded;
        cupsBrewedToday++;
        
        System.out.println("Brewing " + currentStrength + " " + type + "...");
        System.out.println("   Your " + type + " is ready! Enjoy!");
        
        if (cupsBrewedToday >= 10) {
            needsCleaning = true;
            System.out.println(getName() + " needs cleaning after " + cupsBrewedToday + " cups");
        }
        
        printStatus();
    }

    private int getWaterNeeded(CoffeeType type) {
        switch (type) {
            case ESPRESSO: return 30;
            case AMERICANO: return 150;
            case CAPPUCCINO: return 100;
            case LATTE: return 120;
            case MOCHA: return 120;
            default: return 100;
        }
    }

    private int getBeansNeeded(CoffeeType type) {
        int base = 18;
        switch (currentStrength) {
            case MILD: return base - 4;
            case STRONG: return base + 4;
            case EXTRA_STRONG: return base + 8;
            default: return base;
        }
    }

    private int getMilkNeeded(CoffeeType type) {
        switch (type) {
            case CAPPUCCINO: return 80;
            case LATTE: return 150;
            case MOCHA: return 100;
            default: return 0;
        }
    }

    public void refillWater() {
        waterLevelMl = MAX_WATER;
        System.out.println(getName() + " water tank refilled");
    }

    public void refillBeans() {
        beanLevelGrams = MAX_BEANS;
        System.out.println(getName() + " bean container refilled");
    }

    public void refillMilk() {
        milkLevelMl = MAX_MILK;
        System.out.println(getName() + " milk container refilled");
    }

    public void clean() {
        needsCleaning = false;
        cupsBrewedToday = 0;
        System.out.println(getName() + " cleaned and ready!");
    }

    public void setStrength(Strength strength) {
        this.currentStrength = strength;
        System.out.println(getName() + " strength set to " + strength);
    }

    public void printStatus() {
        System.out.println(getName() + " status:");
        System.out.println("   Water: " + (waterLevelMl * 100 / MAX_WATER) + "%");
        System.out.println("   Beans: " + (beanLevelGrams * 100 / MAX_BEANS) + "%");
        System.out.println("   Milk:  " + (milkLevelMl * 100 / MAX_MILK) + "%");
        if (needsCleaning) System.out.println(" NEEDS CLEANING");
    }

    public int getWaterLevel() { return waterLevelMl; }
    public int getBeanLevel() { return beanLevelGrams; }
    public int getMilkLevel() { return milkLevelMl; }
    public boolean needsCleaning() { return needsCleaning; }
}
