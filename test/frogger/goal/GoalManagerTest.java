package frogger.goal;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalManagerTest {

	@Test
	public void testInitializeGoalsPerLevelOne() {
		GoalManager goalmanager = new GoalManager();
		assertEquals(2,goalmanager.initializeGoalsPerLevel(1));
	}
	
	@Test
	public void testInitializeGoalsPerLevelTwo() {
		GoalManager goalmanager = new GoalManager();
		assertEquals(4,goalmanager.initializeGoalsPerLevel(1));
	}

	@Test
	public void testGet() {
		GoalManager goalmanager = new GoalManager();
	}

	@Test
	public void testGetUnreached() {
		GoalManager goalmanager = new GoalManager();
	}

	@Test
	public void testDoBonusCheck() {
		GoalManager goalmanager = new GoalManager();
	}

	@Test
	public void testUpdate() {
		GoalManager goalmanager = new GoalManager();
	}

}
