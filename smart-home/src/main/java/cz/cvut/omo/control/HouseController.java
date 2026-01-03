package cz.cvut.omo.control;

import cz.cvut.omo.event.*;
import cz.cvut.omo.observer.Observer;
import cz.cvut.omo.model.House;


public class HouseController implements Observer {

    private House house;
    private EventHandler chain;

    public void initChain(House house){
        EventHandler leakHandler = new WaterLeakHandler(house);
        EventHandler weatherHandler = new WeatherAlertHandler(house);
        EventHandler brokenHandler = new DeviceBrokenHandler(house);
        EventHandler foodHandler = new FoodFinishedHandler(house);
        EventHandler tempHandler = new TemperatureAlertHandler(house);

        leakHandler.setNext(weatherHandler);
        weatherHandler.setNext(brokenHandler);
        brokenHandler.setNext(foodHandler);
        foodHandler.setNext(tempHandler);
        
        this.chain = leakHandler;
    }
    
   @Override
    public void update(Event event) {
        if (chain != null) {
            chain.handle(event);
        } else {
            System.out.println("Warning: Chain not initialized for event " + event.getType());
        }
    }
}
