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

    
}
