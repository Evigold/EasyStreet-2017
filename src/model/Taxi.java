/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * THis class manages the behavior of an instance of a Taxi Vehicle.
 * 
 * @author Eviatar Goldschmidt evigold@uwe.du
 * @version Oct. 28th 2017
 */
public class Taxi extends AbstractVehicle {

    /** The default death time for this Vehicle object.  */
    private static final int DEATH_TIME = 5;
    
    /** The default pause length when TAxi faces a red Cross-walk light. */
    private static final int LIGHT_PAUSE_DURATION = 3;
    
    /** Keeps track of each refresh. */
    private int myTimeCount;
    /**
     * Constructs an instance of this vehicle object.
     * 
     * @param theX the original X coordinate.
     * @param theY the original Y coordinate.
     * @param theDir the original Direction.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(DEATH_TIME, theX, theY, theDir);
        myTimeCount = 0;
    }

    /**
     * {@inheritDoc AbstractVehicle#canPass(Terrain, Light)}. Every time this method is called,
     * it increases the count of refreshes by 1.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        myTimeCount++;
        return super.canPass(theTerrain, theLight);
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return orderedDirection(theNeighbors);
    }
    
    /**
     * {@inheritDoc AbstractVehicle#lightBehavior(Terrain, Light)}. If the passed terrain 
     * is a Cross Walk, the Light is Red and the refresh count is larger than the 
     * LIGHT_PAUSE_DURATION, the count resets and it returns false. If the count is 
     * exactly LIGHT_PAUSE_DURATION, will return true;
     */
    @Override
    public boolean lightBehavior(final Terrain theTerrain, final Light theLight) {
        boolean canCross = true;
        if (theTerrain == Terrain.LIGHT && theLight == Light.RED) {
            canCross = false;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED 
                        && myTimeCount != LIGHT_PAUSE_DURATION) {
            if (myTimeCount > LIGHT_PAUSE_DURATION) {
                myTimeCount = 0;
            }
            canCross = false;
        }
        return canCross;
    }
}
