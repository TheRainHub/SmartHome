package cz.cvut.omo.iterator;

import cz.cvut.omo.model.Device;
import cz.cvut.omo.model.House;

import java.util.Iterator;
import java.util.List;

public class HouseDeviceIterator implements Iterator<Device> {

    private final List<Device> devices;
    private int index = 0;

    public HouseDeviceIterator(House house) {
        this.devices = house.getDevices();
    }

    @Override
    public boolean hasNext() {
        return index < devices.size();
    }

    @Override
    public Device next() {
        return devices.get(index++);
    }
}
