package cz.cvut.omo.residents;


public class Cat extends Animal {
    
    public Cat(String id, String name) {
        super(id, name);
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

    public void meow() {
        System.out.println(getName() + " meowed!");
    }

    public void makingTheMess() {
        System.out.println(getName() + " made a mess!");
    }
}
