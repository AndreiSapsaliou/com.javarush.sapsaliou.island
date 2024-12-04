package entities.organisms.plants;


import entities.island.Location;
import entities.organisms.Organisms;
import properties.Settings;
import utils.Utils;

public class Plant extends Organisms {
    public Plant(){
        weight = Utils.getWeight(this.getClass());
    }

    public static void grow(Location location){
        for (int i = 0; i < Settings.PLANT_GROWTH; i++) {
            location.addOrganism(new Plant());
        }
    }
}
