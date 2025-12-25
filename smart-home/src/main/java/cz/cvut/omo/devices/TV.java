package cz.cvut.omo.devices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.omo.model.ConsumptionType;
import cz.cvut.omo.model.Device;
import cz.cvut.omo.state.DeviceState;
import cz.cvut.omo.state.IdleState;
import cz.cvut.omo.state.ActiveState;

public class TV extends Device {
    
    public record Channel(int number, String name, String category) {}
    
    private int currentChannel = 1;
    private int volume = 30;
    private boolean muted = false;
    private int sleepTimerMinutes = 0;

    
    private static final List<Channel> ALL_CHANNELS = List.of(
        new Channel(1, "News Channel", "News"),
        new Channel(2, "Sports Channel", "Sports"),
        new Channel(3, "Movie Channel", "Movies"),
        new Channel(4, "Kids Channel", "Kids"),
        new Channel(5, "Discovery Channel", "Documentary"),
        new Channel(6, "Music Channel", "Music"),
        new Channel(7, "Comedy Channel", "Entertainment"),
        new Channel(8, "Cooking Channel", "Lifestyle")
    );
    
    public TV(String name) {
        super(name, 100.0f, new IdleState(), createConsumption());
    }
    
    public TV(String name, float durability, DeviceState state, Map<ConsumptionType, Integer> consumption) {
        super(name, durability, state, consumption);
    }

    private static Map<ConsumptionType, Integer> createConsumption() {
        Map<ConsumptionType, Integer> c = new HashMap<>();
        c.put(ConsumptionType.ELECTRICITY, 150);
        return c;
    }

    public void changeChannel(int channelNumber) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        if (channelNumber < 1 || channelNumber > ALL_CHANNELS.size()) {
            System.out.println("Invalid channel number: " + channelNumber);
            return;
        }
        this.currentChannel = channelNumber;
        Channel ch = ALL_CHANNELS.get(channelNumber - 1);
        System.out.println("TV: " + getName() + " switched to Ch." + ch.number() + " - " + ch.name() + " [" + ch.category() + "]");
    }

    public void channelUp() {
        int next = currentChannel + 1;
        if (next > ALL_CHANNELS.size()) next = 1;
        changeChannel(next);
    }

    public void channelDown() {
        int prev = currentChannel - 1;
        if (prev < 1) prev = ALL_CHANNELS.size();
        changeChannel(prev);
    }

    public void setVolume(int level) {
        if (!(getState() instanceof ActiveState)) {
            System.out.println(getName() + " is off. Turn it on first!");
            return;
        }
        this.volume = Math.max(0, Math.min(100, level));
        this.muted = false;
        System.out.println(" " + getName() + " volume: " + volume + "%");
    }

    public void volumeUp() {
        setVolume(volume + 5);
    }

    public void volumeDown() {
        setVolume(volume - 5);
    }

    public void mute() {
        if (!(getState() instanceof ActiveState)) return;
        muted = !muted;
        System.out.println(getName() + (muted ? " muted" : " unmuted"));
    }

    public void setSleepTimer(int minutes) {
        this.sleepTimerMinutes = minutes;
        if (minutes > 0) {
            System.out.println("TV: " + getName() + " will turn off in " + minutes + " minutes");
        } else {
            System.out.println("TV: " + getName() + " sleep timer cancelled");
        }
    }

    public void tick() {
        if (sleepTimerMinutes > 0 && getState() instanceof ActiveState) {
            sleepTimerMinutes--;
            if (sleepTimerMinutes == 0) {
                System.out.println("TV: " + getName() + " sleep timer expired. Turning off...");
                turnOff();
            }
        }
    }

    public int getCurrentChannel() { return currentChannel; }
    public int getVolume() { return volume; }
    public boolean isMuted() { return muted; }

}
