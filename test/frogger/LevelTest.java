package frogger;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LevelTest {
    
    private Level level;
    
    @Before
    public void setUp() {
        level = new Level();
    }
    
    @Test
    public void testLevelConstructor() {
        assertEquals(Level.STARTING_LEVEL, level.getLevel());
    }

    @Test
    public void testSetLevel() {
        int testLevel = 5;
        level.setLevel(testLevel);
        assertEquals(testLevel, level.getLevel());
    }

    @Test
    public void testSetCheatingLevel() {
        level.setCheatingLevel();
        assertEquals(Level.CHEATING_LEVEL, level.getLevel());
    }

    @Test
    public void testResetLevel() {
        int testLevel = 5;
        level.setLevel(testLevel);
        level.resetLevel();
        assertEquals(Level.STARTING_LEVEL, level.getLevel());
    }

    @Test
    public void testIncrementLevel() {
        level.incrementLevel();
        assertEquals(Level.STARTING_LEVEL + 1, level.getLevel());
        int testLevel = 5;
        level.setLevel(testLevel);
        level.incrementLevel();
        assertEquals(testLevel + 1, level.getLevel());
    }

}
