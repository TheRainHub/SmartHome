package cz.cvut.omo.residents;

import cz.cvut.omo.devices.Stove;

public class Mother extends Person {
    
    public Mother(String id, String name) {
        super(id, name);
    }

    @Override
    public float getRepairSkill() {
        return 50.0f;
    }

    public void goShopping() {
        System.out.println(getName() + " is going shopping for food");
    }

    public void cook(Stove stove) {
        if (stove != null) {
            System.out.println(getName() + " is cooking on " + stove.getName());
            stove.startCooking(Stove.DishType.STEAK, 15);
        } else {
            System.out.println(getName() + " wants to cook but found no stove!");
        }
    }
}
