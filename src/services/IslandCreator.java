package services;

import entities.island.Island;
import entities.island.Location;
import properties.Settings;
import utils.Utils;

public class IslandCreator {
    private static Island island;
    private static IslandCreator islandCreator;

    private IslandCreator(){}

    public static Island getIsland(){
        return island;
    }

    public static Island create(int x,int y){
        if(islandCreator == null){
            islandCreator = new IslandCreator();
            island = new Island(x,y);
            fillLocations();
        }
        return island;
    }

    private static void fillLocations(){
        Location[][] locations = island.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                createOrganisms(locations[i][j]);
            }
        }
    }

    private static void createOrganisms(Location location){
        for(Class organismClass : Settings.ORGANISMS_PARAMETERS.keySet()){
            if(isCreateOrganism(organismClass)){
                int random = Utils.getRandom(0, Utils.getMaxPopulation(organismClass));
                for (int i = 0; i < random; i++) {
                    location.addOrganism(Utils.createOrganism(organismClass));
                }
            }
        }
    }

    private static boolean isCreateOrganism(Class<?> organism){
        return Utils.getRandom();
    }

}
