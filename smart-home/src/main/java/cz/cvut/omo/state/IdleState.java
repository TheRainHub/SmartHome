package cz.cvut.omo.state;

import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.Resident;


public class IdleState implements DeviceState {
    
    @Override
    public void fix(Device device, Resident resident) {
        System.out.println(device.getName() + " is not broken, no repair needed.");
    }

    @Override
    public void turnOn(Device device) {
        System.out.println(device.getName() + " is now ON.");
        device.setState(new ActiveState());
    }

    @Override
    public void turnOff(Device device) {
        System.out.println(device.getName() + " is already off.");
    }

    @Override
    public void use(Device device) {
        System.out.println("Turning on " + device.getName() + " first...");
        turnOn(device);
        device.getState().use(device);
    }
}
