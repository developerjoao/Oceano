import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author João Cabral e Sandino
 */

public class Simulator
{
	private static final double FISH_CREATION_PROBABILITY = 0.02;
	// Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default height of the grid.
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
        runLongSimulation();
        
        //simView.showStatus(0, ocean);
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && simView.isViable(ocean); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Fish> newFishes = new ArrayList<Fish>();        
        // Let all fishes act.
        for(Iterator<Fish> it = fishes.iterator(); it.hasNext(); ) {
            Fish fish = it.next();
            fish.act(newFishes);
            if(! fish.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        fishes.addAll(newFishes);

        simView.showStatus(step, ocean);
    }

	public void reset()
	{
		step = 0;
		fishes.clear();
		populate();
    
		// Show the starting state in the view.
		simView.showStatus(step, ocean);
	}
	
	/**
     * Randomly populate the ocean with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                if(rand.nextDouble() <= FISH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fish fish = new Sardine(true, ocean, location);
                    fishes.add(fish);
                }
//                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
//                    Location location = new Location(row, col);
//                    Rabbit rabbit = new Rabbit(true, ocean, location);
//                    animals.add(rabbit);
//                }
                // else leave the location empty.
            }
        }
    }
}