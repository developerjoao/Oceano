
/**
 * (Fill in description and author info here)
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
        // some code needs to go here
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int row, int col)
    {
        // put code here
        return (Fish) ocean[row][col];
    }
    
    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        // put something here
        return this.height;
    }
    
    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        // and something here
        return this.width;
    }
}
