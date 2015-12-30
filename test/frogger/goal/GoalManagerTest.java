package frogger.goal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frogger.graphics.Graphics;

import frogger.Level;
import frogger.entities.Goal;

public class GoalManagerTest {

    private GoalManager manager;
    
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        for (Field field : Graphics.class.getFields()) {
            System.out.println("value: " + field.get(null) + " name: " + field.getName());
        }
        System.out.println();
        setFinalStatic(Graphics.class.getField("SPRITE_SHEET_PATH"), "../resources/" + Graphics.SPRITE_SHEET);
        for (Field field : Graphics.class.getFields()) {
            System.out.println("value: " + field.get(null) + " name: " + field.getName());
        }
    }

    @Before
    public void setUp() throws Exception {
        manager = new GoalManager();
    }

    @Test
    public void testGoalManager() {
        assertTrue(manager.getGoals().isEmpty());
    }

    @Test
    public void testInitializeGoalsPerLevel() {
        assertEquals(GoalManager.STARTING_NUMBER_OF_GOALS, manager.initializeGoalsPerLevel(Level.STARTING_LEVEL));
        assertEquals(GoalManager.STARTING_NUMBER_OF_GOALS, manager.getUnreached().size());

        assertEquals(GoalManager.SECOND_LEVEL_NUMBER_OF_GOALS,
                manager.initializeGoalsPerLevel(Level.STARTING_LEVEL + 1));
        assertEquals(GoalManager.SECOND_LEVEL_NUMBER_OF_GOALS, manager.getUnreached().size());

        assertEquals(GoalManager.MAX_NUMBER_OF_GOALS,
                manager.initializeGoalsPerLevel(Level.STARTING_LEVEL + 2));
        assertEquals(GoalManager.MAX_NUMBER_OF_GOALS, manager.getUnreached().size());
    }

    @Test
    public void testGetUnreached() {
        manager.initializeGoalsPerLevel(Level.STARTING_LEVEL);
        for (Goal goal : manager.getGoals()) {
            goal.setReached();
        }
        assertTrue(manager.getUnreached().isEmpty());
        manager.initializeGoalsPerLevel(Level.STARTING_LEVEL);
        manager.getGoals().get(0).setReached();
        Goal unreachedGoal = manager.getGoals().get(1);
        assertEquals(GoalManager.STARTING_NUMBER_OF_GOALS - 1, manager.getUnreached().size());
        assertTrue(manager.getUnreached().contains(unreachedGoal));
    }

    @Test
    public void testAreAllGoalsReached() {
        fail("Not yet implemented");
    }

    @Test
    public void testDoBonusCheck() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetGoals() {
        assertNotNull(manager.getGoals());
    }

}
