package entities.organisms.animals;

import entities.island.Location;
import entities.organisms.Organisms;
import properties.Settings;
import services.IslandCreator;
import utils.Utils;

import java.util.ArrayList;

public abstract class Animal extends Organisms {
    public Integer moveSpeed;
    public Float saturation;
    public boolean hasFood = false;
    public boolean hasBreed = false;

    public Animal(){
        this.weight = Utils.getRandom(0.0F,Utils.getWeight(this.getClass())); //Utils.getWeight(this.getClass());
        moveSpeed = Utils.getMaxSpeed(this.getClass());
        this.saturation = Utils.getRandom(0.0F,Utils.getFullSaturation(this.getClass())); //Utils.getFullSaturation(this.getClass());
    }

    public void eat(Location location){
        ArrayList<Organisms> foods = new ArrayList<>();
        ArrayList<Organisms> organisms = location.getOrganisms();
        Float fullSaturation = Utils.getFullSaturation(this.getClass());
        if(organisms.contains(this) && saturation < fullSaturation){
            for(Organisms organism : organisms){
                if(Utils.checkFood(Settings.PROBABILITY_EAT,this.getClass(),organism.getClass())){
                    foods.add(organism);
                }
            }
            if(foods.size() > 0){
                Organisms food = foods.get(0);
                Integer probability = Utils.getProbability(Settings.PROBABILITY_EAT,this.getClass(), food.getClass());
                if(Utils.getRandom(probability)){
                    increaseWeight(food.weight);
                    location.deleteOrganism(food);
                }
            }
        }
    }

    public void multiply(Location location,ArrayList<Organisms> organisms){
        Integer maxPopulation = Utils.getMaxPopulation(this.getClass());
        if(this.hasBreed || location.getPopulation().get(this.getClass()) >= maxPopulation){
            return;
        }
        ArrayList<Organisms> oneTypeAnimals = new ArrayList<>();
        for(Organisms organism : organisms){
            if(organism.getClass() == this.getClass() && organism != this && ((Animal) organism).hasBreed == false){
                oneTypeAnimals.add(organism);
            }
        }
        if (0 < oneTypeAnimals.size()) {
            location.addOrganism(Utils.createOrganism(this.getClass()));
            this.hasBreed = true;
            Animal animal = (Animal) oneTypeAnimals.get(0);
            animal.hasBreed = true;
        }
    }

    public void move(Location location){
        Integer maxSpeed = Utils.getMaxSpeed(this.getClass());
        int step = Utils.getRandom(0, maxSpeed);
        if(step == 0){
            return;
        }
        Integer maxPopulation = Utils.getMaxPopulation(this.getClass());
        Location nextLocation = chooseDirection(location,step);
        if(nextLocation.getPopulation().get(this.getClass()) != null && nextLocation.getPopulation().get(this.getClass()) >= maxPopulation
                || (nextLocation.getPositionX() == location.getPositionX() && nextLocation.getPositionY() == location.getPositionY())){
            return;
        }
        nextLocation.addOrganism(this);
        location.deleteOrganism(this);
    }

    public Location chooseDirection(Location location,Integer step){
        int maxX = (Settings.ISLAND_SIZE_X-1);
        int maxY = (Settings.ISLAND_SIZE_Y-1);
        int positionX = location.getPositionY();
        int positionY = location.getPositionX();

        int direction = Utils.getRandom(0, 3);
        switch(direction){
            case 0 : positionX = (positionX + step) > maxX ? maxX : (positionX + step);break;
            case 1 : positionX = (positionX - step) < 0 ? 0 : (positionX - step);break;
            case 2 : positionY = (positionY - step) < 0 ? 0 : (positionY - step);break;
            case 3 : positionY = (positionY + step) > maxY ? maxY : (positionY + step); break;
        }
        return IslandCreator.getIsland().getLocations()[positionY][positionX];
    }

    public void increaseWeight(Float foodWeight){
        Float maxWeight = Utils.getWeight(this.getClass());
        Float fullSaturation = Utils.getFullSaturation(this.getClass());
        weight = weight + foodWeight;
        if(weight > maxWeight){
            weight = maxWeight;
            saturation = fullSaturation;
        }else{
            saturation = (weight/maxWeight) * fullSaturation;
        }
        hasFood = true;
    }

    public void decreaseWeight(Location location){
        Float maxWeight = Utils.getWeight(this.getClass());
        Float fullSaturation = Utils.getFullSaturation(this.getClass());
        weight = weight - (maxWeight / 10);
        saturation = saturation - (fullSaturation / 10);
        if(weight <= 0){
            die(location);
        }
    }

    public void die(Location location){
        location.deleteOrganism(this);
    }

}
