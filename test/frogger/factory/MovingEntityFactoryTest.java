package frogger.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import jig.engine.util.Vector2D;

public class MovingEntityFactoryTest {

    private MovingEntityFactory factory;

    @Before
    public void setUp() {
        factory = new MovingEntityFactory(new Vector2D(0, 0), new Vector2D(0, 0));
    }

    @Test
    public void testMovingEntityFactory() {
        assertNotEquals(null, factory);
    }

    @Test
    public void testBuildBasicObject() {
        assertEquals(null, factory.buildBasicObject(5, 0));
    }

    @Test(expected = NullPointerException.class)
    public void testBuildBasicObjectCar() {
        factory.update(2000);
        factory.buildBasicObject(0, 1000);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildBasicObjectTruck() {
        factory.update(2000);
        factory.buildBasicObject(1, 1000);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildBasicObjectShortLog() {
        factory.update(2000);
        factory.buildBasicObject(2, 1000);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildBasicObjectLongLog() {
        factory.update(2000);
        factory.buildBasicObject(3, 1000);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildShortLogWithTurtles() {
        factory.update(2000);
        factory.buildShortLogWithTurtles(0);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildLongLogWithCrocodile() {
        factory.update(2000);
        factory.buildLongLogWithCrocodile(0);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildVehicle() {
        factory.update(2000);
        factory.buildVehicle();
    }

    @Test
    public void testUpdateNull() {
        factory.update(0);
        assertNotEquals(null, factory);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNotNull() {
        factory.update(2000);
        factory.buildBasicObject(3, 1000);
    }

}
