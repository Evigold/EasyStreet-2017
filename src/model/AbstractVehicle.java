/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * This abstract class manages all the common methods for all vehicle types.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
 */
public abstract class AbstractVehicle implements Vehicle {

    /** The death time for this vehicle.*/
    private final int myDefualtDeath;
    
    /** The count of updates left in this vehicle's death. */
    private  int myDeath;
    
    /** The current X coordinate of this vehicle. */
    private int myX;
    
    /** The original X coordinate of this vehicle. */
    private final int myOriginalX;
    
    /** The current Y coordinate of this vehicle. */
    private int myY;
    
    /** The original Y coordinate of this vehicle. */
    private final int myOriginalY;
    
    /** The current Direction of this vehicle. */
    private Direction myDirection;
    
    /** The original Direction of this vehicle. */
    private final Direction myOriginalDirection;
    

    /**
     * Constructs an instance for a child vehicle type.
     * 
     * @param theDeath the number of updates for this vehivle's death time.
     * @param theX the starting X coordinate for this vehicle.
     * @param theY the starting Y coordinate for this vehicle.
     * @param theDir the starting Direction for this vehicle.
     */
    protected AbstractVehicle(final int theDeath, final int theX, final int theY, 
                           final Direction theDir) {
        this.myDefualtDeath = theDeath;
        this.myDeath = 0;
        this.myX = theX;
        this.myOriginalX = theX;
        this.myY = theY;
        this.myOriginalY = theY;
        this.myDirection = theDir;
        this.myOriginalDirection = theDir;
    }
    
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = false;
        if (terrainBehavior(theTerrain)) {
            if (theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.LIGHT) {
                if (lightBehavior(theTerrain, theLight)) {
                    passable = true;
                }
            } else {
                passable = true;
            }
        }
        return passable;
    }
    
    

    @Override
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);


    @Override
    public void collide(final Vehicle theOther) {
        if (this.isAlive() && theOther.isAlive() 
                        && this.myDefualtDeath > theOther.getDeathTime()) {
            this.myDeath = this.myDefualtDeath;
        }
    }

    @Override
    public int getDeathTime() {
        return this.myDefualtDeath;
    }

    @Override
    public String getImageFileName() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append(getClass().getSimpleName().toLowerCase());
        if (!isAlive()) {
            builder.append("_dead");
        }
        builder.append(".gif");
        return builder.toString();
    }

    @Override
    public Direction getDirection() {
        return this.myDirection;
    }

    @Override
    public int getX() {
        return this.myX;
    }

    @Override
    public int getY() {
        return this.myY;
    }

    @Override
    public boolean isAlive() {
        boolean alive = false;
        if (this.myDeath == 0) {
            alive = true;
        }
        return alive;
    }

    @Override
    public void poke() {
        if (this.isAlive()) {
            this.myDirection = Direction.random();
        } else {
            this.myDeath--;
        }
    }

    @Override
    public void reset() {
        this.myX = this.myOriginalX;
        this.myY = this.myOriginalY;
        this.myDirection = this.myOriginalDirection;
    }

    @Override
    public void setDirection(final Direction theDir) {
        this.myDirection = theDir;
    }

    @Override
    public void setX(final int theX) {
        this.myX = theX;
    }

    @Override
    public void setY(final int theY) {
        this.myY = theY;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    /**
     * A query that returns whether this vehicle can pass the given terrain, given a 
     * certain light state.
     * 
     * @param theTerrain the Terrain type tested.
     * @param theLight the Light state tested.
     * 
     * @return whether or not this vehicle can pass through the given Terrain, given 
     * the current Light state.
     */
    protected abstract boolean lightBehavior(Terrain theTerrain, Light theLight);
    
    /**
     * A query that returns whether this vehicle can pass through the given Terrain.
     * 
     * @param theTerrain the Terrain type tested.
     * @return whether this vehicle can pass through the given Terrain.
     */
    protected boolean terrainBehavior(final Terrain theTerrain) {
        boolean passableTerrain = false;
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.CROSSWALK 
                        || theTerrain == Terrain.LIGHT) {
            passableTerrain = true;
        }
        return passableTerrain;
    }
    
    /**
     * A helper query that returns whether this vehicle can pass the surrounding terrain.
     * 
     * @param theNeighbors a Map of the Directions with values of Terrain surrounding this
     * vehicle.
     * 
     * @return true if this vehicle can cross at least one of the Terrain values passed in
     * the map..
     */
    protected boolean legalNeighborhood(final Map<Direction, Terrain> theNeighbors) {
        boolean neighborhoodLegal = false;
        for (final Direction test : theNeighbors.keySet()) {
            if (terrainBehavior(theNeighbors.get(test)) 
                            && test != getDirection().reverse()) {
                neighborhoodLegal = true;
            }
        }
        return neighborhoodLegal;
    }
    
    /**
     * A helper query that generates and returns a random, non-reverse  Direction of canPass 
     * Terrain for this vehicle. 
     * 
     * @param theNeighbors a Map of the Direction with values of Terrain surrounding 
     * this vehicle. At least one, non-reverse direction must have a canPass Terrain.
     * @param theDirection a passed direction.
     * 
     * @return a randomized, non-reverse Direction.
     */
    protected Direction randomDirection(final Map<Direction, Terrain> theNeighbors, 
                                        final Direction theDirection) {
        Direction direction = theDirection;
        if (!terrainBehavior(theNeighbors.get(direction)) 
                        || direction == getDirection().reverse()) {
            direction = randomDirection(theNeighbors, Direction.random());
        }
        return direction;
    }
    
    /**
     * A helper query that generates and returns a Direction of a canPass Terrain from a 
     * predetermined order. If possible, will choose strait, if not left, then right and
     * lastly, reverse.
     * 
     * @param theNeighbors a Map of the Directions with values of Terrain surrounding this
     * vehicle.
     * 
     * @return a direction.
     */
    protected Direction orderedDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction direction = getDirection().reverse();
        if (terrainBehavior(theNeighbors.get(getDirection()))) {
            direction = getDirection();
        } else if (terrainBehavior(theNeighbors.get(getDirection().left()))) {
            direction = getDirection().left();
        } else if (terrainBehavior(theNeighbors.get(getDirection().right()))) {
            direction = getDirection().right();
        }
        return direction;
    }
    
    /**
     * A helper query that generates and returns the Direction of the preferred Terrain of 
     * this vehicle. If there are no nearby preferred Terrain, will return the reverse 
     * direction of this vehicle.
     * 
     * @param theNeighbors a Map of the Directions with values of Terrain surrounding this
     * vehicle.
     * @param thePreferredTerrain the preferred Terrain of this vehicle.
     * 
     * @return the direction towards the preferred terrain, If its not nearby, will return the 
     * reverse direction of this vehicle.
     */
    protected Direction preferredTerrain(final Map<Direction, Terrain> theNeighbors, 
                                        final Terrain thePreferredTerrain) {
        Direction direction = getDirection().reverse();
        if (theNeighbors.get(getDirection()) == thePreferredTerrain) {
            direction = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == thePreferredTerrain) {
            direction = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == thePreferredTerrain) {
            direction = getDirection().right();
        }
        return direction;
    }
}
