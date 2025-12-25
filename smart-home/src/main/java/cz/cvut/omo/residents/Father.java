package cz.cvut.omo.residents;


public class Father extends Person {
    
    public Father(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 100.0f;
    }
}
