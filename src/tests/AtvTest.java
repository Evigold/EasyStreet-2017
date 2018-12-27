/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import model.Atv;
import model.Direction;
import model.Light;
import model.Terrain;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Atv.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
 *
 */
public class AtvTest {

    /** 
     * The number of times to repeat a test for randomness to increase the 
     * probability that all possibilities have been explored. 
     * */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** The default X coordinate for the Atv. */
    private static final int X_COORDINATE = 15;
    
    /** The default Y coordinate for the Atv. */
    private static final int Y_COORDINATE = 10;
    
    /** The default Direction for the Atv. */
    private static  final Direction START_DIRECTION = Direction.NORTH;
    
    /** The Death time for a Atv Vehicle. */
    private static final int DEATH_TIME = 10;
    
    /** A Atv to use in tests. */
    private Atv myAtv;
    
    /** SetUp method for Atv Test. */
    @Before
    public void setUp() { 
        myAtv = new Atv(X_COORDINATE, Y_COORDINATE, START_DIRECTION);
    }
    
    /** Test method for the X Coordinate of the Atv Constructor. */
    @Test
    public void testConstructorForX() {
        assertEquals("Atv X coordinate not initialized correctly!", 
                     X_COORDINATE, myAtv.getX());
    }
    
    /** Test method for the Y Coordinate of the Atv Constructor. */
    @Test
    public void testConstructorForY() {
        assertEquals("Atv Y coordinate not initialized correctly!", 
                     Y_COORDINATE, myAtv.getY());
    }
    
    /** Test method for the Direction of the Atv Constructor. */
    @Test
    public void testConstructorForDirection() {
        assertEquals("Atv Direction not initialized correctly!", 
                     START_DIRECTION, myAtv.getDirection());
    }
    
    /** Test method for the Death Time of the Atv Constructor. */
    @Test
    public void testConstructorForDeathTime() {
        assertEquals("Atv death time not initialized correctly!", 
                     DEATH_TIME, myAtv.getDeathTime());
    }
    
    /** Test method for the IsAlive after initialization. */
    @Test
    public void testConstructorForIsAlive() {
        assertTrue("Atv isAlive() failed afrer initialization!", myAtv.isAlive());
    }
    
    /** Test method for Atv setX. */
    @Test
    public void testSetterForX() {
        myAtv.setX(5);
        assertEquals("Atv setX faile!d", 5, myAtv.getX());
    }
    
    /** Test method for Atv setY. */
    @Test
    public void testSetterForY() {
        myAtv.setY(7);
        assertEquals("Atv setY failed!", 7, myAtv.getY());
    }
    
    /** Test method for Atv setDirection. */
    @Test
    public void testSetterForDirection() {
        myAtv.setDirection(Direction.EAST);
        assertEquals("Atv setDirection failed!", Direction.EAST, myAtv.getDirection());
    }
    
    /**
     * Test method for {@link Atv#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        for (final Terrain terrain : Terrain.values()) {
            for (final Light curLight : Light.values()) {
                if (terrain == Terrain.WALL) {
                    assertFalse("Atv should Not be able to pass through WALL "  
                                + "with light " + curLight, 
                                myAtv.canPass(terrain, curLight));
                } else {
                    assertTrue("ATV should be able to pass " + terrain + "with light " 
                                     + curLight, myAtv.canPass(terrain, curLight));
                }
            }
        }
        
    }

    /**
     * Test method for {@link model.Atv#chooseDirection(java.util.Map)} Checking if
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
            final Direction chosen = myAtv.chooseDirection(neighbors);
            
            if (chosen == Direction.NORTH) {
                chooseNorth = true;
            } else if (chosen == Direction.EAST) {
                chooseEast = true;
            } else if (chosen == Direction.WEST) {
                chooseWest = true;
            }
        }
        
        assertTrue("Atv chooseDirection() fails to select Direction "
                        + "randomly among all possible valid choices!", 
                        chooseNorth && chooseEast && chooseWest);
    }

    /**
     * Test method for {@link model.Atv#chooseDirection(java.util.Map)} Checking if
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
            final Direction chosen = myAtv.chooseDirection(neighbors);
            
            if (chosen == Direction.SOUTH) {
                chooseSouth = true;
            }
        }
        
        assertFalse("Atv chooseDirection() reversed when shouldn't!", 
                        chooseSouth);
    }
}
