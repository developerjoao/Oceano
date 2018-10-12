import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Joao Cabral e Sandino
 */
public class Ocean
{
	
	 // A random number generator for providing random locations.
     private static final Random rand = Randomizer.getRandom();
     
	 private int height, width;
	 
	 private Object[][] ocean;
	 private Object[][] seaweeds;
	 
    /**
     * Represent an ocean of the given dimensions.
     * @param height The height of the ocean.
     * @param width The width of the ocean.
     */
    public Ocean(int height, int width)
    {
    	this.height = height;
    	this.width = width;
    	ocean = new Object[height][width];
    	seaweeds = new Object[height][width];
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int row, int col)
    {
        return (Fish) ocean[row][col];
    }
    
    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        return this.height;
    }
    
    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        return this.width;
    }
    
    /**
     * Empty the seaweed field.
     */
    public void clearWeed(){
    	for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                seaweeds[row][col] = null;
            }
        }
    }
    
    /**
     * Empty the ocean.
     */
    public void clear()
    {
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                ocean[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        ocean[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Places a Seaweed in the ocean at a given row and column.
     * @param seaweed The seaweed to place.
     * @param row The row to place.
     * @param col The column to place.
     */
    public void placeWeed(Object seaweed, int row, int col){
    	placeWeed(seaweed, new Location(row,col));
    }
    
    /**
     * Places a weed in the ocean at given location.
     * @param seaweed The seaweed to place.
     * @param location Where to place the Seaweed.
     */
    public void placeWeed(Object seaweed, Location location){
    	seaweeds[location.getRow()][location.getCol()] = seaweed;
    }
    
    /**
     * Place an fish at the given location.
     * If there is already an fish at the location it will
     * be lost.
     * @param fish The fish to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object fish, int row, int col)
    {
        place(fish, new Location(row, col));
    }
    
    /**
     * Place an fish at the given location.
     * If there is already an fish at the location it will
     * be lost.
     * @param fish The fish to be placed.
     * @param location Where to place the fish.
     */
    public void place(Object fish, Location location)
    {
        ocean[location.getRow()][location.getCol()] = fish;
    }
    
    /**
     * Return a random adjacent location from the given location
     * @param location Where in the field.
     * @return The random adjacent location.
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param location Where in the field.
     * @return The fish at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col)
    {
        return ocean[row][col];
    }
    
    /**
     * Return the Seaweed at the given location, if any.
     * @param location Where in the field.
     * @return The fish at the given location, or null if there is none.
     */
    public Object getWeedAt(Location location)
    {
        return getWeedAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the Seaweed at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Object getWeedAt(int row, int col)
    {
        return seaweeds[row][col];
    }
    
    /**
     * Increment the amount of every Seaweed in the ocean, plus growing the
     * Seaweeds that haven't been eaten in the last step.
     */
    public void growWeed() {
    	Random rand = new Random();
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                Location location = new Location(row, col);
                Seaweed weed = (Seaweed) getWeedAt(location);
                weed.regenerate(rand.nextInt(11));
                if(!weed.getEaten()) {
                	weed.grow();
                }
                weed.enableGrow();
            }
    	}
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < height) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
}
