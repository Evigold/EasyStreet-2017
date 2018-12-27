/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * This class manages the behavior of an instance of a Bicycle Vehicle.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017
 */
public class Bicycle extends AbstractVehicle {

    /** The default death time for this Vehicle object.  */
    private static final int DEATH_TIME = 15;
    
    /**
     * Constructs an instance of this vehicle object.
     * 
     * @param theX the original X coordinate.
     * @param theY the original Y coordinate.
     * @param theDir the original Direction.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(DEATH_TIME, theX, theY, theDir);
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction direction = preferredTerrain(theNeighbors, Terrain.TRAIL);
        if (direction == getDirection().reverse()) {
            direction = orderedDirection(theNeighbors);
        }
        return direction;
    }
    
    @Override
    public boolean lightBehavior(final Terrain theTerrain, final Light theLight) {
        boolean canCross = true;
        if ((theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.LIGHT) 
                        && theLight == Light.RED) {
            canCross = false;
        }
        return canCross;
    }

    @Override
    public boolean terrainBehavior(final Terrain theTerrain) {
        boolean passableTerrain = false;
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.CROSSWALK 
                        || theTerrain == Terrain.LIGHT || theTerrain == Terrain.TRAIL) {
            passableTerrain = true;
        }
        return passableTerrain;
    }

}
