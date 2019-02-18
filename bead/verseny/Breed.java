package verseny;

import java.io.*;
import java.net.*;
import java.util.*;

public class Breed {
    public final String name;
    public final double seconds;
    public final Skill skill;
    private ArrayList<String> animals;

    public Breed(String name, int speed, Skill skill, ArrayList<String> animals) {
        this.name = name;
        // this.seconds = 10L / (new Long(speed));
        // System.out.println("breed ctor " + speed);
        this.seconds = (double) 10 / (double) speed;
        // System.out.println("breed ctor " + this.seconds);
        this.skill = skill;
        this.animals = new ArrayList<String>(animals);
    }

    public boolean canCatch(String animal) {
        if (skill == Skill.CATCH) {
            return animals.contains(animal);
        }
        return false;
    }

    public boolean canSave(String animal) {
        if (skill == Skill.SAVE) {
            return animals.contains(animal);
        }
        return false;
    }

    @Override
    public String toString() {
        return "name: " + name + ", seconds/meter: " + seconds + ", skill: " + skill + ", animals: " + Arrays.toString(animals.toArray());
    }
}