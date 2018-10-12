import java.util.List;
/**
 * Write a description of class Fish here.
 * 
 * NOTE: This should serve as a superclass to all specific types of fish
 * 
 * @author Sandino e João Cabral
 * @version (a version number or a date)
 */
public abstract class Fish
{
	private boolean alive;
	private Ocean ocean;
	private Location location;
	/**
	 * Creates a new Fish in the ocean.
	 * 
	 * @param ocean The ocean to occupy.
	 * @param location The given location in the ocean.
	 */
	public Fish(Ocean ocean, Location location)
	{
		alive = true;
		this.ocean = ocean;
		setLocation(location);
	}
	
	/**
	 * Makes the fish act.
	 * @param newFishes Uma lista de peixes
	 */
	abstract public void act(List<Fish> newFishes);
	
	/**
	 * Verifies wether the fish is alive.
	 * @return True if the fish is alive.
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Set the fish to dead and removes from the ocean
	 */
	public void setDead()
	{
		alive = false;
		if(location != null) {
			ocean.clear(location);
			location = null;
			ocean = null;
		}
	}
	
	/**
	 * Return the Fish location.
	 * @return The Fish location.
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * Return the ocean that the Fish is into.
	 * @return The ocean that the Fish is into.
	 */
	public Ocean getOcean()
	{
		return ocean;
	}
	
	/**
	 * Puts the Fish in a given location
	 * @param newLocation The Fish's new location.
	 */
	public void setLocation(Location newLocation) 
	{
		if(location != null) {
			ocean.clear(location);
		}
		location = newLocation;
		ocean.place(this, newLocation);
	}
}
