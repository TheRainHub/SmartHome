package cz.cvut.omo.residents;


public class Mother extends Person {
    
    public Mother(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 50.0f;
    }
}
