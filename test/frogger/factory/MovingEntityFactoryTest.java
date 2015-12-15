package frogger.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import jig.engine.util.Vector2D;

public class MovingEntityFactoryTest {

	@Test
	public void testMovingEntityFactory() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		assertNotEquals(null,movingEntityFactory);
	}

	@Test
	public void testBuildBasicObject() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		assertEquals(null,movingEntityFactory.buildBasicObject(5,0));
	}
	
	@Test(expected=NullPointerException.class)
	public void testBuildBasicObjectCar() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildBasicObject(0,1000);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBuildBasicObjectTruck() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildBasicObject(1,1000);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBuildBasicObjectShortLog() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildBasicObject(2,1000);
	}
	
	@Test(expected=NullPointerException.class)
	public void testBuildBasicObjectLongLog() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildBasicObject(3,1000);
	}

	@Test(expected=NullPointerException.class)
	public void testBuildShortLogWithTurtles() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildShortLogWithTurtles(0);
	}

	@Test(expected=NullPointerException.class)
	public void testBuildLongLogWithCrocodile() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildLongLogWithCrocodile(0);
	}

	@Test(expected=NullPointerException.class)
	public void testBuildVehicle() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildVehicle();
	}

	@Test
	public void testUpdateNull() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(0);
		assertNotEquals(null,movingEntityFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void testUpdateNotNull() {
		MovingEntityFactory movingEntityFactory = new MovingEntityFactory(new Vector2D(0, 0),new Vector2D(0,0));
		movingEntityFactory.update(2000);
		movingEntityFactory.buildBasicObject(3,1000);
	}
	

}
