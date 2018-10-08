import java.util.List;
/**
 * Write a description of class Fish here.
 * 
 * NOTE: This should serve as a superclass to all specific types of fish
 * 
 * @author Sandino 
 * @version (a version number or a date)
 */
public abstract class Fish
{
	private boolean alive;
	private Ocean ocean;
	private Location location;
	/**
	 * Cria um novo animal no Oceano
	 * 
	 * @param ocean O oceano ocupado atualmente
	 * @param location A localização no oceano
	 */
	public Fish(Ocean ocean, Location location)
	{
		alive = true;
		this.ocean = ocean;
		setLocation(location);
	}
	
	/**
	 * Faz o peixe agir
	 * @param newFishes Uma lista de peixes
	 */
	abstract public void act(List<Fish> newFishes);
	
	/**
	 * verifica se o peixe está vivo ou não
	 * @return true se o peixe está vivo
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Indica que o peixe não está mais vivo
	 * e o remove do oceano
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
	 * Retorna a localização do peixe
	 * @return A localização do peixe
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * Retorna o oceano do peixe
	 * @return O oceano do peixe
	 */
	public Ocean getOcean()
	{
		return ocean;
	}
	
	/**
	 * Coloca o peixe numa nova localização do respectivo oceano
	 * @param newLocation A nova localização do peixe
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
