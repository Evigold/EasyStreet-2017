/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * This class manages the behavior of a Car Vehicle instance.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017
 */
public class Car extends AbstractVehicle {

    /** The default death time for this Vehicle object. */
    private static final int DEATH_TIME = 5;
    
    /**
     * Constructs an instance of this vehicle object.
     * 
     * @param theX the original X coordinate.
     * @param theY the original Y coordinate.
     * @param theDir the original Direction.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(DEATH_TIME, theX, theY, theDir);
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return orderedDirection(theNeighbors);
    }
    
    @Override
    public boolean lightBehavior(final Terrain theTerrain, final Light theLight) {
        boolean canCross = true;
        if (theTerrain == Terrain.LIGHT && theLight == Light.RED) {
            canCross = false;
        } else if (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN) {
            canCross = false;
        }
        return canCross;
    }
}
