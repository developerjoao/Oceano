import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author João Cabral e Sandino
 */

public class Simulator
{
	// Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_HEIGHT = 50;
	
	private List<Fish> fishes;
	
	private int step;
	
    private Ocean ocean;
    private SimulatorView simView;
    
    
    public static void main(String[] args) 
    {
        Simulator sim = new Simulator(50, 60);
        sim.run(1000);
    }
    
    
    
    public Simulator(int height, int width)
    {
    	if(height <= 0 || width <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            height = DEFAULT_HEIGHT;
            width = DEFAULT_WIDTH;
        }
    	fishes = new ArrayList<Fish>();
    	
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
        
        // define in which color fish should be shown
        simView.setColor(Fish.class, Color.red);
        
        reset();
    }
    
    public void run(int steps)
    {
        // put the simulation main loop here
        
        simView.showStatus(0, ocean);
    }


	public void reset()
	{
		step = 0;
		fishes.clear();
		//populate();
    
		// Show the starting state in the view.
		simView.showStatus(step, ocean);
	}
}