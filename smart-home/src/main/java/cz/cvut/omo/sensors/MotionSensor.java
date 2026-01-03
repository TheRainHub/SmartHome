package cz.cvut.omo.sensors;

import cz.cvut.omo.event.Event;
import cz.cvut.omo.event.EventType;

public class MotionSensor extends Sensor {

    private boolean motionDetected = false;

    public void setMotion(boolean motionDetected) {
        this.motionDetected = motionDetected;
        checkStatus();
    }

    @Override
    public void checkStatus() {
        if(motionDetected) {
            notifyObservers(new Event(
                    EventType.MOTION_DETECTED, this,
                    "Motion detected"
            ));
        }
    }
}
