import java.util.List;
import java.util.Random;
/**
 * 
 * @author Jo�o Cabral e Sandino
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
	
	public int getAmmount() {
		return this.ammount;
	}
}
