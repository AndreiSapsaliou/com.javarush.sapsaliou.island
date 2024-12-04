package services;

import entities.island.Location;
import entities.organisms.Organisms;
import entities.organisms.animals.Animal;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LifeTask implements Callable {
    private Location location;
    public LifeTask(Location location){
        this.location = location;
    }

    @Override
    public Object call() {
        organismsNutrition();
        organismsReproduction();
        organimsMovings();
        return null;
    }

    private void organismsNutrition(){
        synchronized (location){
            ArrayList<Organisms> organismsBeforeEating = (ArrayList<Organisms>) location.getOrganisms().clone();
            for (int i = 0; i < organismsBeforeEating.size(); i++) {
                if (organismsBeforeEating.get(i) instanceof Animal) {
                    Animal animal = (Animal) organismsBeforeEating.get(i);
                    animal.eat(location);
                }
            }
            ArrayList<Organisms> organismsAfterEating = location.getOrganisms();
            for (int i = 0; i < organismsAfterEating.size(); i++) {
                if (organismsAfterEating.get(i) instanceof Animal) {
                    Animal animal = (Animal) organismsAfterEating.get(i);
                    if (!animal.hasFood) {
                        animal.decreaseWeight(location);
                    } else {
                        animal.hasFood = false;
                    }
                }
            }
        }
    }

    private void organismsReproduction(){
        synchronized (location) {
            ArrayList<Organisms> organismsBeforeReproduction = (ArrayList<Organisms>) location.getOrganisms().clone();
            for (int i = 0; i < organismsBeforeReproduction.size(); i++) {
                if (organismsBeforeReproduction.get(i) instanceof Animal) {
                    Animal animal = (Animal) organismsBeforeReproduction.get(i);
                    animal.multiply(location, organismsBeforeReproduction);
                }
            }
            ArrayList<Organisms> organismsAfterReproduction = location.getOrganisms();
            for (int i = 0; i < organismsAfterReproduction.size(); i++) {
                if (organismsAfterReproduction.get(i) instanceof Animal) {
                    Animal animal = (Animal) organismsAfterReproduction.get(i);
                    animal.hasBreed = false;
                }
            }
        }
    }

    private void organimsMovings(){
        ArrayList<Organisms> organismsBeforeMove = (ArrayList<Organisms>) location.getOrganisms().clone();
        for (int i = 0; i < organismsBeforeMove.size(); i++) {
            if (organismsBeforeMove.get(i) instanceof Animal) {
                Animal animal = (Animal) organismsBeforeMove.get(i);
                animal.move(location);
            }
        }

    }

}
