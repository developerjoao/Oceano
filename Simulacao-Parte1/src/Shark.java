import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A simple model of a shark.
 * Sharks age, move, breed, and die.
 * Sharks eat groper or herring but they prefer groper.
 * Sharks are loners - they prefer not to swim next to each other
 * @author Sandino e Joao Cabral
 */
public class Shark extends Fish
{
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 150;
    private static final double BREEDING_PROBABILITY = 0.40;
    private static final int MAX_LITTER_SIZE = 1;
    
    private static final int TUNA_FOOD_VALUE = 10;
    private static final int SARDINE_FOOD_VALUE = 7;
    
    private static final Random rand = Randomizer.getRandom();
    
    private int age;
    
    private int foodLevel;
    
    /**
     * Creates a new Shark.
     * @param randomAge If true, sets a random value of age and food for this Shark.
     * @param ocean Sets the ocean it's into.
     * @param location Sets it's location in the ocean.
     */
    public Shark(boolean randomAge, Ocean ocean, Location location)
    {
    	super(ocean, location);
    	if(randomAge) {
    		age = rand.nextInt(MAX_AGE);
    		foodLevel = rand.nextInt(TUNA_FOOD_VALUE);
    	}else {
    		age = 0;
    		foodLevel = TUNA_FOOD_VALUE;
    	}
    }
    
	/**
	 * Represents the acts of a Shark.
	 * @see Fish#act(java.util.List)
	 */
    public void act(List<Fish> newSharks)
    {
    	incrementAge();
    	incrementHunger();
    	if(isAlive()) {
    		giveBirth(newSharks);
    		
    		Location location = getLocation();
    		Location newLocation = findFood(location);
    		if(newLocation == null) {
    			newLocation = findShark(location);
    			if(newLocation == null) {
    				newLocation = getOcean().freeAdjacentLocation(location);
    			}
    		}
    		if(newLocation != null) {
    			setLocation(newLocation);
    		}
    		else {
    			setDead();
    		}
    	}
    }
    
	/**
	 * Increments the age of the Shark.
	 */
    private void incrementAge() 
    {
    	age++;
    	if(age > MAX_AGE) {
    		setDead();
    	}
    }
    
	/**
	 * Increments the hunger of the Sardine.
	 */
    private void incrementHunger() 
    {
    	foodLevel--;
    	if(foodLevel <= 0) {
    		setDead();
    	}
    }
    
    /**
     * Finds an adjacent Shark so this Shark can try to stay alone.
     * @param location This Shark location.
     * @return
     */
    private Location findShark(Location location)
    {
    	Ocean ocean = getOcean();
    	List<Location> adjacents = ocean.adjacentLocations(location);
    	
    	for(Location adjacent : adjacents) {
    		Object fish = ocean.getFishAt(adjacent.getRow(), adjacent.getCol());
    		if(fish instanceof Shark) {
    			Shark shark = (Shark) fish;
    			if(shark.isAlive()) {
    				shark.setDead();
        			return adjacent;
    			}
    		}
    	}
    	return null;
    }
    
	/**
	 * Tries to find Tuna and Sardines to feed the Shark, preferring Tunas over Sardines.
	 * @return The location where it shall swim into.
	 */
    private Location findFood(Location location) 
    {
    	Ocean ocean = getOcean();
    	List<Location> adjacent = ocean.adjacentLocations(getLocation());
    	Iterator<Location> it = adjacent.iterator();
    	while(it.hasNext()) {
    		Location where = it.next();
    		Object fish = ocean.getFishAt(where.getRow(), where.getCol());
    		if(fish instanceof Tuna) {
    			Tuna tuna = (Tuna) fish;
    			if(tuna.isAlive()) {
    				tuna.setDead();
    				foodLevel = TUNA_FOOD_VALUE;
    				return where;
    			}
    		}else if(fish instanceof Sardine) {
    			Sardine sardine = (Sardine) fish;
    			if(sardine.isAlive()) {
    				sardine.setDead();
    				foodLevel = SARDINE_FOOD_VALUE;
    				return where;
    			}
    		}
    	}
    	return null;
    }
    
	/**
	 * Spawns new Sharks in the ocean.
	 */
    private void giveBirth(List<Fish> newSharks) 
    {
    	Ocean ocean = getOcean();
    	List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
    	int births = breed();
    	for(int b = 0; b < births && free.size() > 0; b++) {
    		Location loc = free.remove(0);
    		Shark young = new Shark(false, ocean, loc);
    		newSharks.add(young);
    	}
    }
    
	/**
	 * Method 
	 * @return
	 */
    private int breed()
    {
    	int births = 0;
    	if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
    		births = rand.nextInt(MAX_LITTER_SIZE) + 1;
    	}
    	return births;
    }
    
	/**
	 * Returns wether the Shark can breed or not.
	 * @return True if the age is bigger than the Shark breeding age.
	 */
    private boolean canBreed()
    {
    	return age >= BREEDING_AGE;
    }  
}
