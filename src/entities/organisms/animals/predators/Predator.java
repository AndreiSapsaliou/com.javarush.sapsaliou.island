package entities.organisms.animals.predators;

import entities.island.Location;
import entities.organisms.animals.Animal;

public abstract class Predator extends Animal {

    public Predator() {
        super();
    }
    @Override
    public void eat(Location location) {
        super.eat(location);
    }
}
