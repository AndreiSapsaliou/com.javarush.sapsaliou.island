package entities.island;

import entities.organisms.Organisms;
import java.util.*;


public class Location {
    private int positionX;
    private   int positionY;
    private ArrayList<Organisms> organisms = new ArrayList<>();
    private HashMap<Class<?>,Integer> population = new HashMap<>();

    public Location(int x,int y){
        positionX = x;
        positionY = y;
    }

    public int getPositionX(){ return positionX;}
    public int getPositionY(){ return positionY;}
    public HashMap<Class<?>,Integer> getPopulation(){
        return population;
    }

    public ArrayList<Organisms> getOrganisms(){ return organisms;}

    public synchronized void addOrganism(Organisms organism){
        if(population.containsKey(organism.getClass())){
            population.put(organism.getClass(),population.get(organism.getClass())+1);
        }else{
            population.put(organism.getClass(),1);
        }
        organisms.add(organism);
    }
    public synchronized void deleteOrganism(Organisms organism){
        if(population.containsKey(organism.getClass())){
            population.put(organism.getClass(),population.get(organism.getClass())-1);
        }else{
            System.out.println("Check the Application");
        }
        organisms.remove(organism);
    }
}

