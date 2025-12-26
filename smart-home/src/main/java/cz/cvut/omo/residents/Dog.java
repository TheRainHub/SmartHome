package cz.cvut.omo.residents;


public class Dog extends Animal {
    
    public Dog(String id, String name) {
        super(id, name);
    }

    public void bark() {
        System.out.println(getName() + " barked!");
    }

    public void sleep() {
        System.out.println(getName() + " is sleeping.");
    }

    public void play() {
        System.out.println(getName() + " is playing.");
    }

    public void eat() {
        System.out.println(getName() + " is eating.");
    }

    public void openDoor() {
        System.out.println(getName() + " opened the door.");
    }

}
