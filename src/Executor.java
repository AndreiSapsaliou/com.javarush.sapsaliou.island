import entities.island.Island;
import properties.Settings;
import services.IslandCreator;
import services.LifeStarter;
import utils.Utils;

public class

Executor {
    public static void main(String[] args) {

        boolean continueWork = Utils.initializeFields();
        if(!continueWork){ return; }

        Island island = IslandCreator.create(Settings.ISLAND_SIZE_X,Settings.ISLAND_SIZE_Y);
        LifeStarter lifeStarter = new LifeStarter(island);
        lifeStarter.start();
    }
}
