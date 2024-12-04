package utils;

import entities.island.Island;
import entities.island.Location;
import entities.organisms.Organisms;
import properties.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private Utils(){
        throw new IllegalStateException("Utility class");
    }

    public static Organisms createOrganism(Class organismClass){
        try {
            Constructor<?>[] constructors = organismClass.getConstructors();
            return (Organisms) constructors[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printIslandEveryLocationStatistic(Island island, int step){
        System.out.println("DAY " + step);
        System.out.println("-------------------");
        Location[][] locations = island.getLocations();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                System.out.print(locations[i][j].getPositionX() + "" + locations[i][j].getPositionY() + " [ ");
                HashMap<Class<?>, Integer> population = locations[i][j].getPopulation();
                for (Class<?> clazz : population.keySet()) {
                    System.out.print(clazz.getSimpleName() + " : " + population.get(clazz) + " ");
                }
                System.out.print("] ");
                System.out.println();
            }
        }
    }

    public static void printIslandStatistic(Island island, int step){
        System.out.println("День " + step);
        System.out.println("-------------------");
        Location[][] locations = island.getLocations();
        Map<Class<?>,Integer> amountEveryOrganismClass = new HashMap<>();
        for(Class organismClass : Settings.ORGANISMS_PARAMETERS.keySet()) {
            amountEveryOrganismClass.put(organismClass, 0);
        }
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                HashMap<Class<?>, Integer> population = locations[i][j].getPopulation();
                for(Class<?> populationOfGroupAnimals : population.keySet()){
                    Integer sum = amountEveryOrganismClass.get(populationOfGroupAnimals) + population.get(populationOfGroupAnimals);
                    amountEveryOrganismClass.put(populationOfGroupAnimals,sum);
                }
            }
        }
        for (Class<?> organismClass : amountEveryOrganismClass.keySet()) {
            System.out.print(Settings.EMOJI.get(organismClass) + " : " + amountEveryOrganismClass.get(organismClass) + " ");
        }
        System.out.println();
    }

    public static <T,K,V> void addElement(Map<T,Map<K,V>> map,T key1, K key2, V value){
        Map<K,V> inner = map.get(key1);
        if(inner == null){
            inner = new HashMap<>();
            map.put(key1,inner);
        }
        inner.put(key2,value);
    }

    public static <T,K,V> V getProbability(Map<T,Map<K,V>> map,T key1, K key2){
        Map<K,V> inner = map.get(key1);
        if (inner==null || !(inner.containsKey(key2))) {
            return null;
        }
        return inner.get(key2);
    }

    public static <T,K,V> boolean checkFood(Map<T,Map<K,V>> map,T key1, K key2){
        Map<K,V> inner = map.get(key1);
        if(inner == null || !(inner.containsKey(key2))){
            return false;
        }
        if((Integer)inner.get(key2) > 0){
            return true;
        }
        return false;
    }

    public static Float getWeight(Class organismClass){
        return Settings.ORGANISMS_PARAMETERS.get(organismClass)[0];
    }

    public static Integer getMaxPopulation(Class organismClass){
        return (int) Settings.ORGANISMS_PARAMETERS.get(organismClass)[1];
    }

    public static Integer getMaxSpeed(Class organismClass){
        return (int) Settings.ORGANISMS_PARAMETERS.get(organismClass)[2];
    }

    public static Float getFullSaturation(Class organismClass){
        return Settings.ORGANISMS_PARAMETERS.get(organismClass)[3];
    }

    public static int getRandom(int from, int to){
        return ThreadLocalRandom.current().nextInt(from,to);
    }

    public static float getRandom(float from, float to){
        float result = ThreadLocalRandom.current().nextFloat();
        return result;
    }

    public static boolean getRandom(int probability){
        int i = ThreadLocalRandom.current().nextInt(0, 100);
        return i < probability;
    }

    public static boolean getRandom(){
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static synchronized void printLocationStatistic(Location location){
        System.out.print(location.getPositionX() + "" + location.getPositionY() + " [ ");
        HashMap<Class<?>, Integer> population = location.getPopulation();
        for (Class<?> clazz : population.keySet()) {
            System.out.print(clazz.getSimpleName() + " : " + population.get(clazz) + " ");
        }
        System.out.print("] ");
        System.out.println();
    }

    public static boolean initializeFields(){
        boolean result = false;
        System.out.println("Хотите задать размеры острова?");
        System.out.println("1 - Да");
        System.out.println("2 - Нет. Значения будут взяты из настроек");
        System.out.println("3 - Выход");

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                int type = scanner.nextInt();
                switch (type) {
                    case (1) : { setIslandParameters(); result = true; break;} // Заполнение параметров
                    case (2) : { result = true; break;} // Продолжение работы
                    case (3) : { result = false; break;} // Выход из программы
                    default : { System.out.println("Режим работы не найден");}
                }
                break;
            } else {
                System.out.println("Введите число");
                scanner.nextLine();
            }
        }
        return result;
    }

    private static void setIslandParameters(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите X");
        while(scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                Settings.ISLAND_SIZE_X = scanner.nextInt();
                break;
            } else {
                System.out.println("Введите число");
                scanner.nextLine();
            }
        }
        System.out.println("Введите Y");
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                Settings.ISLAND_SIZE_Y = scanner.nextInt();
                break;
            } else {
                System.out.println("Введите число");
                scanner.nextLine();
            }
        }
    }
}