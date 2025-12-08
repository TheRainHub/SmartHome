package cz.cvut.omo.model;
//ja bych nechala prazdnej ale jinak nemuzu pustit test bez setroom vis
public class Device {

    private Room room;

    public Device() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
