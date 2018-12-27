/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Bicycle;
import model.Direction;
import model.Human;
import model.Light;
import model.Terrain;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for class Bicycle.
 * 
 * @author Eviatar Goldschmidt evigold@uw.edu
 * @version Oct. 28th 2017.
 *
 */
public class BicycleTest {

    /** The default X coordinate for the Bicycle. */
    private static final int X_COORDINATE = 15;
    
    /** The default Y coordinate for the Bicycle. */
    private static final int Y_COORDINATE = 10;
    
    /** The default Direction for the Bicycle. */
    private static  final Direction START_DIRECTION = Direction.NORTH;
    
    /** The Death time for a Bicycle Vehicle. */
    private static final int DEATH_TIME = 15;
    
    /** A Bicycle to use in tests. */
    private Bicycle myBicycle;
    
    /** SetUp method for Bicycle Test. */
    @Before
    public void setUp() { 
        myBicycle = new Bicycle(X_COORDINATE, Y_COORDINATE, START_DIRECTION);
    }
    
    /** Test method for the X Coordinate of the Bicycle Constructor. */
    @Test
    public void testConstructorForX() {
        assertEquals("Bicycle X coordinate not initialized correctly!", 
                     X_COORDINATE, myBicycle.getX());
    }
    
    /** Test method for the Y Coordinate of the Bicycle Constructor. */
    @Test
    public void testConstructorForY() {
        assertEquals("Bicycle Y coordinate not initialized correctly!", 
                     Y_COORDINATE, myBicycle.getY());
    }
    
    /** Test method for the Direction of the Bicycle Constructor. */
    @Test
    public void testConstructorForDirection() {
        assertEquals("Bicycle Direction not initialized correctly!", 
                     START_DIRECTION, myBicycle.getDirection());
    }
    
    /** Test method for the Death Time of the Bicycle Constructor. */
    @Test
    public void testConstructorForDeathTime() {
        assertEquals("Bicycle death time not initialized correctly!", 
                     DEATH_TIME, myBicycle.getDeathTime());
    }
    
    /** Test method for the IsAlive after initialization. */
    @Test
    public void testConstructorForIsAlive() {
        assertTrue("Bicycle isAlive() failed afrer initialization!", myBicycle.isAlive());
    }
    
    /** Test method for Bicycle setX. */
    @Test
    public void testSetterForX() {
        myBicycle.setX(5);
        assertEquals("Bicycle setX faile!d", 5, myBicycle.getX());
    }
    
    /** Test method for Bicycle setY. */
    @Test
    public void testSetterForY() {
        myBicycle.setY(7);
        assertEquals("Bicycle setY failed!", 7, myBicycle.getY());
    }
    
    /** Test method for Bicycle setDirection. */
    @Test
    public void testSetterForDirection() {
        myBicycle.setDirection(Direction.EAST);
        assertEquals("Bicycle setDirection failed!", Direction.EAST, myBicycle.getDirection());
    }
    
    /**
     * Test method for {@link Bicycle#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.TRAIL);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        
        for (final Terrain terrain : Terrain.values()) {
            for (final Light curLight : Light.values()) {
                if (terrain == Terrain.LIGHT || terrain == Terrain.CROSSWALK) {
                    if (curLight != Light.RED) {
                        assertTrue("Bicycle should be able to pass " + terrain 
                                 + "with light " + curLight, 
                                 myBicycle.canPass(terrain, curLight));
                    } else {
                        assertFalse("Bicycle should NOT be able to pass " + terrain 
                                   + "with light " + curLight, 
                                   myBicycle.canPass(terrain, curLight));
                    }
                } else if (validTerrain.contains(terrain)) {
                    assertTrue("Bicycle should be able to pass " + terrain 
                               + "with light " + curLight, 
                               myBicycle.canPass(terrain, curLight));
                } else if (!validTerrain.contains(terrain)) {
 
                    assertFalse("Bicycle should NOT be able to pass " + terrain
                        + ", with light " + curLight,
                        myBicycle.canPass(terrain, curLight));
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
                     Direction.NORTH, myBicycle.chooseDirection(neighbors));
    }
    
    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} when the only legal 
     * option is to turn left..
     */
    @Test
    public void testChooseDirectionOnStreetMustLeft() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK 
                            && t != Terrain.TRAIL) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.STREET);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when turn left was the only valid choice!",
                             Direction.WEST, myBicycle.chooseDirection(neighbors));
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
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK 
                            && t != Terrain.TRAIL) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when turn right was the only valid choice!",
                             Direction.EAST, myBicycle.chooseDirection(neighbors));
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
            if (t != Terrain.LIGHT && t != Terrain.STREET && t != Terrain.CROSSWALK 
                            && t != Terrain.TRAIL) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                assertEquals("Bicycle chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, myBicycle.chooseDirection(neighbors));
            }
                
        }
    }
    
    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)} in the case that
     * a trail is near by.
     */
    @Test
    public void testChooseDirectionOnStreetNearTrail() {
        
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.TRAIL);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        for (final Direction d : Direction.values()) {
            myBicycle.setDirection(d);
            
            if (d == Direction.EAST) {
                assertNotEquals("A human near a crosswalk and facing " + d
                                + " should not reverse direction!",
                                Direction.WEST, myBicycle.chooseDirection(neighbors));
                   
            } else {
                assertEquals("A human near a crosswalk and facing " + d
                            + " chose a wrong direction!",
                            Direction.WEST, myBicycle.chooseDirection(neighbors));
            }
        }
    }
}

