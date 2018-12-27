/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * This class manages the behavior of an instance of a Atv Vehicle.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017
 */
public class Atv extends AbstractVehicle {

    /** The default death time for this Vehicle object. */
    private static final int DEATH_TIME = 10;

    /**
     * Constructs an instance of this vehicle object.
     * 
     * @param theX the original X coordinate.
     * @param theY the original Y coordinate.
     * @param theDir the original Direction.
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(DEATH_TIME, theX, theY, theDir);
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return randomDirection(theNeighbors, Direction.random());
    }
    
    @Override
    public boolean lightBehavior(final Terrain theTerrain, final Light theLight) {
        return true;
    }
    
    @Override
    public boolean terrainBehavior(final Terrain theTerrain) {
        boolean passableTerrain = true;
        if (theTerrain == Terrain.WALL) {
            passableTerrain = false;
        }
        return passableTerrain;
    }

}
