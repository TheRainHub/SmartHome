package cz.cvut.omo.residents;

import cz.cvut.omo.model.Resident;


public abstract class Animal extends Resident {
    
    public Animal(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 0.0f;
    }
}
