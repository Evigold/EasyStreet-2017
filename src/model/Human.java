/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * This class manages the behavior of an instance of a Human Vehicle.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017
 */
public class Human extends AbstractVehicle {

    /** The default death time for this Vehicle object. */
    private static final int DEATH_TIME = 20;
    
    /**
     * Constructs an instance of this vehicle object.
     * 
     * @param theX the original X coordinate.
     * @param theY the original Y coordinate.
     * @param theDir the original Direction.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(DEATH_TIME, theX, theY, theDir);
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction direction = preferredTerrain(theNeighbors, Terrain.CROSSWALK);
        if (direction == getDirection().reverse() && legalNeighborhood(theNeighbors)) {
            direction = randomDirection(theNeighbors, Direction.random());
        }
        return direction;
    }
    
    @Override
    public boolean lightBehavior(final Terrain theTerrain, final Light theLight) {
        boolean canCross = true;
        if (theLight == Light.GREEN) {
            canCross = false;
        }
        return canCross;
    }

    @Override
    public boolean terrainBehavior(final Terrain theTerrain) {
        boolean passableTerrain = false;
        if (theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.GRASS) {
            passableTerrain = true;
        }
        return passableTerrain;
    }
}
