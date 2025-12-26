package cz.cvut.omo.sport;

public abstract class SportEquipment {
    private String name;

    public SportEquipment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
