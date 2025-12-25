package cz.cvut.omo.state;

import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.Resident;


public interface DeviceState {
    void fix(Device device, Resident resident);
    void turnOn(Device device);
    void turnOff(Device device);
    void use(Device device);
}
