package cz.cvut.omo.state;

import java.util.Random;

import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.Resident;

public class ActiveState implements DeviceState {

    private Random random = new Random();
    
    @Override
    public void fix(Device device, Resident resident) {
        System.out.println("Device " + device.getName() + "is already fixed");
    }

    @Override
    public void turnOn(Device device) {
        System.out.println("Device " + device.getName() + "is already on");
    }

    @Override
    public void turnOff(Device device) {
        System.out.println("Device " + device.getName() + "is already off");
        device.setState(new IdleState());
    }

    @Override
    public void use(Device device) {
        float wear = 1 + random.nextFloat() * 14; 
        
        float newDurability = device.getDurability() - wear;
        
        device.setDurability(newDurability);

        System.out.printf("%s using now Wear: -%.1f%%. Current Durability: %.1f%%%n", 
                          device.getName(), wear, newDurability);

        if (newDurability <= 0) {
            device.setDurability(0);
            System.out.println(" Opps! Device" + device.getName() + " is fully broken");
            device.setState(new BrokenState());
        }
    }   
}
