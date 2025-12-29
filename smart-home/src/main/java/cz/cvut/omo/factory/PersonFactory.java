package cz.cvut.omo.factory;

import cz.cvut.omo.residents.FamilyRole;
import cz.cvut.omo.residents.Father;
import cz.cvut.omo.residents.Mother;
import cz.cvut.omo.residents.Child;
import cz.cvut.omo.residents.Person;

import java.util.HashMap;
import java.util.Map;


public class PersonFactory {
    private static final Map<FamilyRole, ResidentCreator> registry = new HashMap<>();
    
    static {
        registry.put(FamilyRole.FATHER, (id, name) -> new Father(id, name));
        registry.put(FamilyRole.MOTHER, (id, name) -> new Mother(id, name));
        registry.put(FamilyRole.SON, (id, name) -> new Child(id, name));
        registry.put(FamilyRole.DAUGHTER, (id, name) -> new Child(id, name));
        registry.put(FamilyRole.GRANDMOTHER, (id, name) -> new Mother(id, name));
        registry.put(FamilyRole.GRANDFATHER, (id, name) -> new Father(id, name));
    }

    public static Person createPerson(String id, String name, FamilyRole role) {
        ResidentCreator creator = registry.get(role);
        if (creator == null) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        
        return creator.createResident(id, name);
    }
}
