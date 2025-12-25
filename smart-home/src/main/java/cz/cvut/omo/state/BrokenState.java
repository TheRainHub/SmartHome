package cz.cvut.omo.state;

import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.Resident;


public class BrokenState implements DeviceState {
    
    @Override
    public void fix(Device device, Resident resident) {
        if (!resident.canRepair()) {
            System.out.println(resident.getName() + " cannot repair " + device.getName() + " - no repair skills!");
            return;
        }
        
        float repairAmount = resident.getRepairSkill();
        float newDurability = Math.min(100.0f, device.getDurability() + repairAmount);
        device.setDurability(newDurability);
        
        System.out.printf("%s repaired %s by %.0f%%. New durability: %.1f%%%n", 
                          resident.getName(), device.getName(), repairAmount, newDurability);
        
        device.setState(new IdleState());
    }

    @Override
    public void turnOn(Device device) {
        System.out.println("Cannot turn on " + device.getName() + " - it's broken! Needs repair first.");
    }

    @Override
    public void turnOff(Device device) {
        System.out.println(device.getName() + " is already off (broken).");
    }

    @Override
    public void use(Device device) {
        System.out.println("Cannot use " + device.getName() + " - it's broken! Needs repair first.");
    }
}
