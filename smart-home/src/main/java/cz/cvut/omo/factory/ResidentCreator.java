package cz.cvut.omo.factory;

import cz.cvut.omo.residents.Person;

@FunctionalInterface
public interface ResidentCreator {
    Person createResident(String id, String name);
}
