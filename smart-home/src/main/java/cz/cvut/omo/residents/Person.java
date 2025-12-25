package cz.cvut.omo.residents;


import cz.cvut.omo.model.Resident;

public abstract class Person extends Resident {
    
    public Person(String id, String name) {
        super(id, name);
    }
}
