package cz.cvut.omo.residents;



public class Child extends Person {
    
    public Child(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 0.0f; 
    }
}
