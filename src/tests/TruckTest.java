/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Car;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Truck.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
  */
public class TruckTest {

    /** 
     * The number of times to repeat a test for randomness to increase the 
     * probability that all possibilities have been explored. 
     * */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** The default X coordinate for the truck. */
    private static final int X_COORDINATE = 15;
    
    /** The default Y coordinate for the truck. */
    private static final int Y_COORDINATE = 10;
    
    /** The default Direction for the truck. */
    private static  final Direction START_DIRECTION = Direction.NORTH;
    
    /** The Death time for a Truck Vehicle. */
    private static final int DEATH_TIME = 0;
    
    /** A Truck to use in tests. */
    private Truck myTruck;
    
    /** SetUp method for Truck Test. */
    @Before
    public void setUp() { 
        myTruck = new Truck(X_COORDINATE, Y_COORDINATE, START_DIRECTION);
    }
    
    /** Test method for {@link Truck#getX()}. */
    @Test
    public void testForGetX() {
        
        assertEquals("Truck getX() did not return the X coordinate correctly!", 
                     X_COORDINATE, myTruck.getX());
    }
    
    /** Test method for {@link Truck#getY()}. */
    @Test
    public void testForGetY() {
        
        assertEquals("Truck getY() did  not return the Y coordinate correctly!", 
                     Y_COORDINATE, myTruck.getY());
    }
    
    /** Test method for {@link Truck#getDirection()}. */
    @Test
    public void testForGetDirection() {
        
        assertEquals("Truck getDirection() did not return the Directioncorrectly!", 
                     START_DIRECTION, myTruck.getDirection());
    }
    
    /** Test method for {@link Truck#getDeathTime()}. */
    @Test
    public void testForGetDeathTime() {
        
        assertEquals("Truck getDeathTime did not return the death time correctly!", 
                     DEATH_TIME, myTruck.getDeathTime());
    }
    
    /** Test method for {@link Truck#isAlive()}. */
    @Test
    public void testForIsAlive() {
        
        assertTrue("Truck isAlive() failed afrer initialization!", myTruck.isAlive());
    }
    
    /** Test method for {@link Truck#setX(int)}. */
    @Test
    public void testForSetX() {
        myTruck.setX(5);
        
        assertEquals("Truck setX faile!d", 5, myTruck.getX());
    }
    
    /** Test method for {@link Truck#setY(int)}. */
    @Test
    public void testForSetY() {
        myTruck.setY(7);
        assertEquals("Truck setY failed!", 7, myTruck.getY());
    }
    
    /** Test method for {@link Truck#setDirection(Direction)}. */
    @Test
    public void testForSetDirection() {
        myTruck.setDirection(Direction.EAST);
        
        assertEquals("Truck setDirection failed!", Direction.EAST, myTruck.getDirection());
    }
    
    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        
        for (final Terrain terrain : Terrain.values()) {
            for (final Light curLight : Light.values()) {
                if (terrain == Terrain.LIGHT) {
                
                    assertTrue("Truck should be able to pass LIGHT"
                               + ", with light " + curLight,
                               myTruck.canPass(terrain, curLight));
                    
                } else if (terrain == Terrain.STREET) {
                
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + curLight,
                               myTruck.canPass(terrain, curLight));
                    
                } else if (terrain == Terrain.CROSSWALK) {

                    if (curLight == Light.RED) {
                        
                        assertFalse("Truck should NOT be able to pass " + terrain
                            + ", with light " + curLight,
                            myTruck.canPass(terrain,
                                          curLight));
                        
                    } else { 
                        
                        assertTrue("Truck should be able to pass " + terrain
                            + ", with light " + curLight,
                            myTruck.canPass(terrain,
                                          curLight));
                        
                    }
                } else if (!validTerrain.contains(terrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + terrain
                        + ", with light " + curLight,
                        myTruck.canPass(terrain, curLight));
                    
                }
            } 
        }
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)} Checking if
     * chooses randomly when all surroundings are legal.
     */
    @Test
    public void testChooseDirectionSurroundedByStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        
        boolean chooseNorth = false;
        boolean chooseEast = false;
        boolean chooseWest = false;
        
        for (int runs = 1; runs <= TRIES_FOR_RANDOMNESS; runs++) {
            final Direction chosen = myTruck.chooseDirection(neighbors);
            
            if (chosen == Direction.NORTH) {
                chooseNorth = true;
            } else if (chosen == Direction.EAST) {
                chooseEast = true;
            } else if (chosen == Direction.WEST) {
                chooseWest = true;
            }
        }
        
        assertTrue("Truck chooseDirection() fails to select Direction "
                        + "randomly among all possible valid choices!", 
                        chooseNorth && chooseEast && chooseWest);
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)} Checking if
     * doesn't choose reverse when all surroundings are legal.
     */
    @Test
    public void testChooseDirectionReverseSurroundedByStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        
        boolean chooseSouth = false;
    
        for (int runs = 1; runs <= TRIES_FOR_RANDOMNESS; runs++) {
            final Direction chosen = myTruck.chooseDirection(neighbors);
            
            if (chosen == Direction.SOUTH) {
                chooseSouth = true;
            }
        }
        
        assertFalse("Truck chooseDirection() reversed when shouldn't!", 
                        chooseSouth);
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)} when the only legal 
     * option is to reverse..
     */
    @Test
    public void testChooseDirectionOnStreetMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, myTruck.chooseDirection(neighbors));
            }
        }
    }
    
    /**
     * Test method for {@link Truck#toString()}.
     */
    @Test
    public void testToString() {
        
        assertEquals("Truck toString() failed to return the String correctly!", 
                     "Truck", myTruck.toString());
    }
    
    /**
     * Test method for {@link Truck#reset()} for the X Coordinate.
     */
    @Test
    public void testForResetX() {
        myTruck.setX(3);
        
        myTruck.reset();
        
        assertEquals("Truck reset() failed to reset the X coordinate value correctly!", 
                     X_COORDINATE, myTruck.getX());
    }

    /**
     * Test method for {@link Truck#reset()} for the Y Coordinate.
     */
    @Test
    public void testForResety() {
        myTruck.setY(3);
        
        myTruck.reset();
        
        assertEquals("Truck reset() failed to reset the Y coordinate value correctly!", 
                     Y_COORDINATE, myTruck.getY());
    }

    /**
     * Test method for {@link Truck#reset()} for the Direction.
     */
    @Test
    public void testForResetDirection() {
        myTruck.setDirection(Direction.WEST);
        
        myTruck.reset();
        
        assertEquals("Truck reset() failed to reset the Direction correctly!", 
                     START_DIRECTION, myTruck.getDirection());
    }
    
    /**
     * Test method for {@link Truck#getImageFileName()}.
     */
    @Test
    public void testGetImageFileName() {
        
        assertEquals("Truck getImageFileName() failed to generate and return the file "
                        + "name correctly!", "truck.gif", myTruck.getImageFileName());
    }
    
    /**
     * Test method for {@link Truck#collide(model.Vehicle)} colliding with another Truck.
     */
    @Test
    public void testCollideWithTruck() {
        myTruck.collide(new Truck(X_COORDINATE, Y_COORDINATE, START_DIRECTION));
        
        assertTrue("Truck collide(vehicle) failed to not die when collided with "
                        + "another Truck", myTruck.isAlive());
    }
    
    /**
     * Test method for {@link Truck#collide(model.Vehicle)} colliding with another vehicle.
     */
    @Test
    public void testCollide() {
        myTruck.collide(new Car(X_COORDINATE, Y_COORDINATE, START_DIRECTION));
        
        assertTrue("Truck collide(vehicle) failed to not die when collided with "
                        + "a Car Vehicle", myTruck.isAlive());
    }
    
    /**
     * Test method for {@link Truck#poke()}on whether it randomizes direction on alive truck.
     */
    @Test
    public void testForPokeRandomDirection() {
        
        boolean chooseNorth = false;
        boolean chooseEast = false;
        boolean chooseWest = false;
        boolean chooseSouth = false;
        
        
        for (int runs = 1; runs <= TRIES_FOR_RANDOMNESS; runs++) {
            myTruck.poke();
            final Direction currentDirection = myTruck.getDirection();
            if (currentDirection == Direction.NORTH) {
                chooseNorth = true;
            } else if (currentDirection == Direction.EAST) {
                chooseEast = true;
            } else if (currentDirection == Direction.WEST) {
                chooseWest = true;
            } else if (currentDirection == Direction.SOUTH) {
                chooseSouth = true;
            }
        }
    
        assertTrue("Truck Poke() failed to generate a new random direction", 
                   chooseNorth && chooseEast && chooseWest && chooseSouth);
    }
}
