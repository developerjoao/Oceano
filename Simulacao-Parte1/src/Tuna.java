import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * A simple model of a tuna.
 * Tuna age, move, breed, and die.
 * They eat herring.
 * 
 * @author Sandino e Joao Cabral
 */
public class Tuna extends Fish
{
	private static final int BREEDING_AGE = 5;
	private static final int MAX_AGE = 45;
	private static final double BREEDING_PROBABILITY = 0.35;
	private static final int MAX_LITTER_SIZE = 5;
	
	private static final int SARDINE_FOOD_VALUE = 10;
	
	private static final Random rand = Randomizer.getRandom();
	
	private int age;
	
	private int foodLevel;
	
	public Tuna(boolean randomAge, Ocean ocean, Location location) 
	{
		super(ocean, location);
		if(randomAge) {
			age = rand.nextInt(MAX_AGE);
			foodLevel = rand.nextInt(SARDINE_FOOD_VALUE);
		}
		else {
			age = 0;
			foodLevel = SARDINE_FOOD_VALUE;
		}
	}
	
	/**
	 * Method that represents the acts of a Tuna.
	 * @see Fish#act(java.util.List)
	 */
	public void act(List<Fish> newTunas)
	{
		incrementAge();
		incrementHunger();
		if(isAlive()) {
			giveBirth(newTunas);
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
	
	/**
	 * Method that increments the age of the Tuna.
	 */
	private void incrementAge()
	{
		age++;
		if(age > MAX_AGE) {
			setDead();
		}
	}
	
	/**
	 * Method that increments the hunger of the Tuna.
	 */
	private void incrementHunger()
	{
		foodLevel--;
		if(foodLevel <= 0) {
			setDead();
		}
	}
	
	/**
	 * Method that tries to find Sardines to feed the Tuna.
	 */
	private Location findFood(Location location)
	{
		Ocean ocean = getOcean();
		List<Location> adjacent = ocean.adjacentLocations(getLocation());
		Iterator<Location> it = adjacent.iterator();
		while(it.hasNext()) {
			Location where = it.next();
			Object fish = ocean.getFishAt(where.getRow(), where.getCol());
			if(fish instanceof Sardine) {
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
	 * Method that spawns new Tunas in the ocean.
	 */
	private void giveBirth(List<Fish> newTunas) {
		Ocean ocean = getOcean();
		List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
		int births = breed();
		for(int b = 0; b < births && free.size() > 0; b++) {
			Location loc = free.remove(0);
			Tuna young = new Tuna(false, ocean, loc);
			newTunas.add(young);
		}
	}
	
	/**
	 * 
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
	 * Method that returns wether the Tuna can breed or not.
	 * @return True if the age is bigger than the Tuna's breeding age.
	 */
	private boolean canBreed()
	{
		return age >= BREEDING_AGE;
	}
}
