package cz.cvut.omo.event;

import cz.cvut.omo.model.House;
import cz.cvut.omo.residents.Mother;

public class FoodFinishedHandler extends EventHandler {
    private final House house;
    
    public FoodFinishedHandler(House house) {
        this.house = house;
    }
    
    @Override
    protected boolean canHandle(Event event) {
        return event.getType() == EventType.FOOD_FINISHED;
    }

    @Override
    protected void doHandle(Event event) {
        System.out.println("Food finished - Mother goes shopping");
        
        cz.cvut.omo.residents.Mother mom = house.findResident(cz.cvut.omo.residents.Mother.class);
        if (mom != null) {
            mom.goShopping();
            cz.cvut.omo.devices.Stove stove = house.findDevice(cz.cvut.omo.devices.Stove.class);
            mom.cook(stove);
        }

        cz.cvut.omo.residents.Child child = house.findResident(cz.cvut.omo.residents.Child.class);
        if (child != null) {
            cz.cvut.omo.devices.DishWasher dishwasher = house.findDevice(cz.cvut.omo.devices.DishWasher.class);
            child.washDishes(dishwasher);
        }
    }
}
