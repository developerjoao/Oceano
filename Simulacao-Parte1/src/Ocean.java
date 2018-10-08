
/**
 * @author João Cabral e Sandino
 */
public class Ocean
{
	 private int height, width;
	 
	 private Object[][] ocean;
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
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param fish The fish to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Fish fish, int row, int col)
    {
        ocean[row][col] = fish;
    }
    
    /**
     * Empty the field.
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
     * @param row, col The location to clear.
     */
    public void clear(int row, int col)
    {
        ocean[row][col] = null;
    }
}
