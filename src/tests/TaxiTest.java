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
import model.Direction;
import model.Light;
import model.Taxi;
import model.Terrain;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Taxi.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
 *
 */
public class TaxiTest {

    /** The default X coordinate for the Taxi. */
    private static final int X_COORDINATE = 15;
    
    /** The default Y coordinate for the Taxi. */
    private static final int Y_COORDINATE = 10;
    
    /** The default Direction for the Taxi. */
    private static  final Direction START_DIRECTION = Direction.NORTH;
    
    /** The Death time for a Taxi Vehicle. */
    private static final int DEATH_TIME = 5;
    
    /** A Taxi to use in tests. */
    private Taxi myTaxi;
    
    /** SetUp method for Taxi Test. */
    @Before
    public void setUp() { 
        myTaxi = new Taxi(X_COORDINATE, Y_COORDINATE, START_DIRECTION);
    }
    
    /** Test method for the X Coordinate of the Taxi Constructor. */
    @Test
    public void testConstructorForX() {
        assertEquals("Taxi X coordinate not initialized correctly!", 
                     X_COORDINATE, myTaxi.getX());
    }
    
    /** Test method for the Y Coordinate of the Taxi Constructor. */
    @Test
    public void testConstructorForY() {
        assertEquals("Taxi Y coordinate not initialized correctly!", 
                     Y_COORDINATE, myTaxi.getY());
    }
    
    /** Test method for the Direction of the Taxi Constructor. */
    @Test
    public void testConstructorForDirection() {
        assertEquals("Taxi Direction not initialized correctly!", 
                     START_DIRECTION, myTaxi.getDirection());
    }
    
    /** Test method for the Death Time of the Taxi Constructor. */
    @Test
    public void testConstructorForDeathTime() {
        assertEquals("Taxi death time not initialized correctly!", 
                     DEATH_TIME, myTaxi.getDeathTime());
    }
    
    /** Test method for the IsAlive after initialization. */
    @Test
    public void testConstructorForIsAlive() {
        assertTrue("Taxi isAlive() failed afrer initialization!", myTaxi.isAlive());
    }
    
    /** Test method for Taxi setX. */
    @Test
    public void testSetterForX() {
        myTaxi.setX(5);
        assertEquals("Taxi setX faile!d", 5, myTaxi.getX());
    }
    
    /** Test method for Taxi setY. */
    @Test
    public void testSetterForY() {
        myTaxi.setY(7);
        assertEquals("Taxi setY failed!", 7, myTaxi.getY());
    }
    
    /** Test method for Taxi setDirection. */
    @Test
    public void testSetterForDirection() {
        myTaxi.setDirection(Direction.EAST);
        assertEquals("Taxi setDirection failed!", Direction.EAST, myTaxi.getDirection());
    }
    
    /**
     * Test method for {@link Taxi#canPass(Terrain, Light)}.
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
                
                    if (curLight == Light.RED) {
                        assertFalse("Taxi should NOT be able to pass " + terrain
                            + ", with light " + curLight,
                            myTaxi.canPass(terrain,
                                          curLight));
                    } else { 
                        assertTrue("Taxi should be able to pass " + terrain
                            + ", with light " + curLight,
                            myTaxi.canPass(terrain,
                                          curLight));
                    }
                } else if (terrain == Terrain.STREET) {
                
                    assertTrue("Taxi should be able to pass STREET"
                               + ", with light " + curLight,
                               myTaxi.canPass(terrain, curLight));
                } else if (terrain == Terrain.CROSSWALK) {

                    if (curLight == Light.RED) {
                        assertFalse("Taxi should NOT be able to pass " + terrain
                            + ", with light " + curLight,
                            myTaxi.canPass(terrain,
                                          curLight));
                    } else { 
                        assertTrue("Taxi should be able to pass " + terrain
                            + ", with light " + curLight,
                            myTaxi.canPass(terrain,
                                          curLight));
                    }
                } else if (!validTerrain.contains(terrain)) {
 
                    assertFalse("Taxi should NOT be able to pass " + terrain
                        + ", with light " + curLight,
                        myTaxi.canPass(terrain, curLight));
                }
            } 
        }
        
    }
    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} when the only legal 
     * option is to continue strait..
     */
    @Test
    public void testChooseDirectionOnStreetMustStrait() {
        
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        assertEquals("Truck chooseDirection() failed "
                        + "when strait was the only valid choice!",
                     Direction.NORTH, myTaxi.chooseDirection(neighbors));
    }
    
    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} when the only legal 
     * option is to turn left..
     */
    @Test
    public void testChooseDirectionOnStreetMustLeft() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.STREET);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when turn left was the only valid choice!",
                             Direction.WEST, myTaxi.chooseDirection(neighbors));
            }
                
        }
    }

    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} when the only legal 
     * option is to turn right..
     */
    @Test
    public void testChooseDirectionOnStreetMustRight() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when turn right was the only valid choice!",
                             Direction.EAST, myTaxi.chooseDirection(neighbors));
            }
                
        }
    }

    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} when the only legal 
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
                             Direction.SOUTH, myTaxi.chooseDirection(neighbors));
            }
                
        }
    }
}

    
