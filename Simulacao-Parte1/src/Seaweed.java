import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author João Cabral e Sandino
 * @version (a version number or a date)
 */
public class Seaweed {
	private boolean wasEaten = false;
	private int ammount;
	private Ocean ocean;
	private Location location;
	private static final Random rand = new Random();
	
	
	public Seaweed(Ocean ocean, Location location){
		this.ocean = ocean;
		this.location = location;
		this.ammount = rand.nextInt(11);
	}
	
	public boolean getEaten() {
		return this.wasEaten;
	}
	public void eaten() {
		this.wasEaten = true;
	}
	
	public void enableGrow() {
		this.wasEaten = false;
	}
	
	public int getAmmount() {
		return this.ammount;
	}
	
	public void regenerate(int regen) {
		this.ammount = regen;
	}
	
	public void grow() {
		Seaweed weed = new Seaweed(ocean,ocean.randomAdjacentLocation(this.location));
		ocean.placeWeed(weed,ocean.randomAdjacentLocation(this.location));
	}
}
