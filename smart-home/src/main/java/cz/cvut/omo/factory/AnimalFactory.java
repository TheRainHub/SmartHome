package cz.cvut.omo.factory;

import cz.cvut.omo.residents.AnimalRole;
import cz.cvut.omo.residents.*;

public class AnimalFactory  {
    public static Animal createAnimal(String id, String name, AnimalRole role) {
        switch(role) {
            case DOG:
                return new Dog(id, name);
            case CAT:
                return new Cat(id, name);
            default:
                throw new IllegalArgumentException("Invalid animal: " + role);
        }
    }
}
