package services;

import entities.island.Island;
import entities.island.Location;
import properties.Settings;
import utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LifeStarter {

    private final Island island;
    private final int stepDuration = Settings.STEP_DURATION;
    private final int simulationStepsNumber = Settings.SIMULATION_STEPS;
    private final AtomicInteger simulationStep = new AtomicInteger(0);
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);  // For plants
    private final ExecutorService fixedExecutorService = Executors.newFixedThreadPool(10);

    public LifeStarter(Island island) {
        this.island = island;
    }

    public void start(){
        Location[][] locations = island.getLocations();
        startPlantSimulation(locations);
        while(simulationStepsNumber > simulationStep.get()){
            simulationStep.incrementAndGet();
            ArrayList<Callable<Object>> tasks = new ArrayList<>();
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations[i].length; j++) {
                    LifeTask lifeTask = new LifeTask(locations[i][j]);
                    tasks.add(lifeTask);
                }
            }
            try {
                fixedExecutorService.invokeAll(tasks);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Utils.printIslandStatistic(island,simulationStep.get());
        }
        executorService.shutdownNow();
        fixedExecutorService.shutdown();
    }

    private void startPlantSimulation(Location[][] locations){
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                executorService.scheduleWithFixedDelay(new PlantTask(locations[i][j]),1,stepDuration, TimeUnit.MILLISECONDS);
            }
        }
    }

}
