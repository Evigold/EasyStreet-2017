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
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Car.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
 *
 */
public class CarTest {

    /** The default X coordinate for the Car. */
    private static final int X_COORDINATE = 15;
    
    /** The default Y coordinate for the Car. */
    private static final int Y_COORDINATE = 10;
    
    /** The default Direction for the Car. */
    private static  final Direction START_DIRECTION = Direction.NORTH;
    
    /** The Death time for a Car Vehicle. */
    private static final int DEATH_TIME = 5;
    
    /** A Car to use in tests. */
    private Car myCar;
    
    /** SetUp method for Car Test. */
    @Before
    public void setUp() { 
        myCar = new Car(X_COORDINATE, Y_COORDINATE, START_DIRECTION);
    }
    
    /** Test method for the X Coordinate of the Car Constructor. */
    @Test
    public void testConstructorForX() {
        assertEquals("Car X coordinate not initialized correctly!", 
                     X_COORDINATE, myCar.getX());
    }
    
    /** Test method for the Y Coordinate of the Car Constructor. */
    @Test
    public void testConstructorForY() {
        assertEquals("Car Y coordinate not initialized correctly!", 
                     Y_COORDINATE, myCar.getY());
    }
    
    /** Test method for the Direction of the Car Constructor. */
    @Test
    public void testConstructorForDirection() {
        assertEquals("Car Direction not initialized correctly!", 
                     START_DIRECTION, myCar.getDirection());
    }
    
    /** Test method for the Death Time of the Car Constructor. */
    @Test
    public void testConstructorForDeathTime() {
        assertEquals("Car death time not initialized correctly!", 
                     DEATH_TIME, myCar.getDeathTime());
    }
    
    /** Test method for the IsAlive after initialization. */
    @Test
    public void testConstructorForIsAlive() {
        assertTrue("Car isAlive() failed afrer initialization!", myCar.isAlive());
    }
    
    /** Test method for Car setX. */
    @Test
    public void testSetterForX() {
        myCar.setX(5);
        assertEquals("Car setX faile!d", 5, myCar.getX());
    }
    
    /** Test method for Car setY. */
    @Test
    public void testSetterForY() {
        myCar.setY(7);
        assertEquals("Car setY failed!", 7, myCar.getY());
    }
    
    /** Test method for Car setDirection. */
    @Test
    public void testSetterForDirection() {
        myCar.setDirection(Direction.EAST);
        assertEquals("Car setDirection failed!", Direction.EAST, myCar.getDirection());
    }
    
    /**
     * Test method for {@link Car#canPass(Terrain, Light)}.
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
                        assertFalse("Car should NOT be able to pass " + terrain
                            + ", with light " + curLight,
                            myCar.canPass(terrain,
                                          curLight));
                    } else { 
                        assertTrue("Car should be able to pass " + terrain
                            + ", with light " + curLight,
                            myCar.canPass(terrain,
                                          curLight));
                    }
                } else if (terrain == Terrain.STREET) {
                
                    assertTrue("Car should be able to pass STREET"
                               + ", with light " + curLight,
                               myCar.canPass(terrain, curLight));
                } else if (terrain == Terrain.CROSSWALK) {

                    if (curLight == Light.RED || curLight == Light.YELLOW) {
                        assertFalse("Car should NOT be able to pass " + terrain
                            + ", with light " + curLight,
                            myCar.canPass(terrain,
                                          curLight));
                    } else { 
                        assertTrue("Car should be able to pass " + terrain
                            + ", with light " + curLight,
                            myCar.canPass(terrain,
                                          curLight));
                    }
                } else if (!validTerrain.contains(terrain)) {
 
                    assertFalse("Car should NOT be able to pass " + terrain
                        + ", with light " + curLight,
                        myCar.canPass(terrain, curLight));
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
                     Direction.NORTH, myCar.chooseDirection(neighbors));
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
                             Direction.WEST, myCar.chooseDirection(neighbors));
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
                             Direction.EAST, myCar.chooseDirection(neighbors));
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
                             Direction.SOUTH, myCar.chooseDirection(neighbors));
            }
                
        }
    }
}

    
