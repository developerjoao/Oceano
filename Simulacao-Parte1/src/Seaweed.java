import java.util.Random;
/**
 * Write a description of class Fish here.
 * 
 * NOTE: This should serve as a superclass to all specific types of fish
 * 
 * @author Jo�o Cabral
 * @version (a version number or a date)
 */
public class Seaweed {
	private int ammount;
	private Ocean ocean;
	private Location location;
	private static final Random rand = new Random();
	
	
	public Seaweed(Ocean ocean, Location location){
		this.ocean = ocean;
		this.location = location;
		this.ammount = rand.nextInt(11);
	}
}
