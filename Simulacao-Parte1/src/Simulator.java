import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Jo�o Cabral e Sandino
 */

public class Simulator
{
	private static final double SARDINE_CREATION_PROBABILITY = 0.02;
	private static final double TUNA_CREATION_PROBABILITY = 0.02;
	private static final double SHARK_CREATION_PROBABILITY = 0.06;
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
        Simulator sim = new Simulator( 100, 200);
        sim.run(500);
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
        simView.setColor(Sardine.class, Color.red);
        simView.setColor(Tuna.class, Color.blue);
        simView.setColor(Shark.class, Color.green);
        
        reset();
    }
    
    public void run(int steps)
    {
        simulate(steps);
        
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
               
        // Add the newly born fishes to the main lists.
        fishes.addAll(newFishes);

        simView.showStatus(step, ocean);
    }

	public void reset()
	{
		step = 0;
		fishes.clear();
		populateSeaweed();
		populate();
    
		// Show the starting state in the view.
		simView.showStatus(step, ocean);
	}
	
	public void populateSeaweed(){
		ocean.clearWeed();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                Location location = new Location(row, col);
                Seaweed seaweed = new Seaweed(ocean, location);
                ocean.placeWeed(seaweed, location);
            }
    	}
    }
	
	/**
     * Randomly populate the ocean with the Fishes.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        ocean.clear();
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                if(rand.nextDouble() <= SARDINE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fish fish = new Sardine(true, ocean, location);
                    fishes.add(fish);
                }
                else if(rand.nextDouble() <= TUNA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fish fish = new Tuna(true, ocean, location);
                    fishes.add(fish);
                }
                else if(rand.nextDouble() <= SHARK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fish fish = new Shark(true, ocean, location);
                    fishes.add(fish);
                }
                // else leave the location empty.
            }
        }
    }
}