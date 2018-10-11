import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A simple model of a sardine.
 * sardines age, move, breed, and die.
 * They eat plankton.
 * They exhibit flocking behaviour - they tend to seek company. 
 * If they spot a predator close by, they panic.
 * 
 * @author Sandino e Jo�o Cabral
 * 
 */
public class Sardine extends Fish
{
	private static final int BREEDING_AGE = 2;
	private static final int MAX_AGE = 75;
	private static final double BREEDING_PROBABILITY = 0.60;
	private static final int MAX_LITTER_SIZE = 15;
	
	private static final int PLANKTON_FOOD_VALUE = 10;
	
	private static final Random rand = Randomizer.getRandom();
	
	private int age;
	
	private int foodLevel;
	
	public Sardine(boolean randomAge, Ocean ocean, Location location)
	{
		super(ocean, location);
		if(randomAge) {
			age = rand.nextInt(MAX_AGE);
			foodLevel = rand.nextInt(PLANKTON_FOOD_VALUE);
		}
		else {
			age = 0;
			foodLevel = PLANKTON_FOOD_VALUE;
		}
	}
	
	public void act(List<Fish> newSardines)
	{
		incrementAge();
		incrementHunger();
		if(isAlive()) {
			giveBirth(newSardines);
			Location location = getLocation();
			Location newLocation = findFood(location);
			if(newLocation == null) {
				newLocation = getOcean().freeAdjacentLocation(location);
			}
			if(newLocation != null) {
				setLocation(newLocation);
			}
			else {
				setDead();
			}
		}
	}
	
	private void incrementAge()
	{
		age++;
		if(age > MAX_AGE) {
			setDead();
		}
	}
	
	private void incrementHunger()
	{
		foodLevel--;
		if(foodLevel <= 0) {
			setDead();
		}
	}
	
	private Location findFood(Location location)
	{
		Ocean ocean = getOcean();
		List<Location> adjacent = ocean.adjacentLocations(getLocation());
		Iterator<Location> it = adjacent.iterator();
		while(it.hasNext()) {
			Location where = it.next();
			Object seaweed = ocean.getWeedAt(where.getRow(), where.getCol());
			if(seaweed instanceof Seaweed && ocean.getObjectAt(where) == null) {
				Seaweed food = (Seaweed) seaweed;
				foodLevel = food.getAmmount();
				return where;
			}
			
		}
		return null;
	}
	
	private void giveBirth(List<Fish> newSardines)
	{
		Ocean ocean = getOcean();
		List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
		int births = breed();
		for(int b =0; b < births && free.size() > 0; b++) {
			Location loc = free.remove(0);
			Sardine young = new Sardine(false, ocean, loc);
			newSardines.add(young);
		}
	}
	
	private int breed()
	{
		int births = 0;
		if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
			births = rand.nextInt(MAX_LITTER_SIZE) + 1;
		}
		return births;
	}
	
	private boolean canBreed()
	{
		return age >= BREEDING_AGE;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
