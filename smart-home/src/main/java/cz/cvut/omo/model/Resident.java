package cz.cvut.omo.model;

public class Resident {

    private final String id;
    private final String name;
    private Room currentRoom;

    public Resident(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void moveTo(Room room) {
        this.currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getId() { return id; }

    public String getName() { return name; }
}
