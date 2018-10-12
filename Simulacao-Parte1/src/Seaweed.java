import java.util.Random;
/**
 * 
 * @author Joao Cabral e Sandino
 * @version (a version number or a date)
 */
public class Seaweed {
	private boolean wasEaten = false;
	private int amount;
	private Ocean ocean;
	private Location location;
	private static final Random rand = new Random();
	
	/**
	 * Creates a new Seaweed
	 * @param ocean Sets the ocean it's into.
	 * @param location Sets the it's location in the Seaweed field.
	 */
	public Seaweed(Ocean ocean, Location location){
		this.ocean = ocean;
		this.location = location;
		this.amount = rand.nextInt(11);
	}
	
	/**
	 * Method that returns the value of wasEaten, that indicates wether the Seaweed was eaten or not.
	 * @return True if this Seaweed was eaten.
	 */
	public boolean getEaten() {
		return this.wasEaten;
	}
	
	/**
	 * Method that sets wasEaten to true, indicating it was eaten.
	 */
	public void eaten() {
		this.wasEaten = true;
	}
	
	/**
	 * Method that sets wasEaten to false, enabling it to grow during the simulation.
	 */
	public void enableGrow() {
		this.wasEaten = false;
	}
	
	/**
	 * Method that returns the amount of food that the Seaweed gives to the Sardine.
	 * @return The amount of food that this Seaweed provide.
	 */
	public int getAmount() {
		return this.amount;
	}
	
	/**
	 * Method that sets the value of the amount of food this Seaweed gives in a random number from 0-10.
	 */
	public void regenerate(int regen) {
		this.amount = regen;
	}
	
	/**
	 * Method that places a new Seaweed adjacent from the current Seaweed acting as it's grown to another location.
	 */
	public void grow() {
		Seaweed weed = new Seaweed(ocean,ocean.randomAdjacentLocation(this.location));
		ocean.placeWeed(weed,ocean.randomAdjacentLocation(this.location));
	}
}
