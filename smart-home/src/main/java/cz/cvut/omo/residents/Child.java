package cz.cvut.omo.residents;



public class Child extends Person {
    
    public Child(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 0.0f; 
    }

    public void play() {
        System.out.println(getName() + " is playing.");
    }
    
    public void watchTV() {
        System.out.println(getName() + " is watching TV.");
    }

    public void sleep() {
        System.out.println(getName() + " is sleeping.");
    }

    public void eat() {
        System.out.println(getName() + " is eating.");
    }
    
    public void washDishes(cz.cvut.omo.devices.DishWasher dishwasher) {
        if (dishwasher != null) {
            System.out.println(getName() + " is loading the dishwasher " + dishwasher.getName());
            if (!dishwasher.isRunning()) {
                dishwasher.loadDishes(10);
                dishwasher.startWashing(cz.cvut.omo.devices.DishWasher.WashProgram.ECO);
            }
        } else {
            System.out.println(getName() + " is washing dishes by hand (no dishwasher found).");
        }
    }
    
}
