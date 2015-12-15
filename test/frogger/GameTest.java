package frogger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class GameTest {
    
    private static Game game;

    @BeforeClass
    public static void setUp() throws Exception {
        game = new Game();
//        ResourceFactory factory = 
//
//        Class<?> clazz = (Class<J2DResourceFactory>) ResourceFactory.getFactory().getClass();
//        Method method = clazz.getMethod("initializeResources", clazz);
//        method.invoke(factory, null);
//        
//        frame = J2DGameFrame.getGameFrame(title, w, h, preferredFullScreen);
    }
    
    @After
    public void tearDown() throws Exception {
//        GameFrame frame = ResourceFactory.getFactory().getGameFrame("Frogger", World.WORLD_WIDTH, World.WORLD_HEIGHT, false);
//        frame.closeAndExit();
//        frame.requestExitAndClose(true);
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testIncreaseScore() {
        int beginScore = game.getScore();
        int increment = 1;
        game.increaseScore(increment);
        int endScore = game.getScore();
        assertEquals(beginScore + increment, endScore);
    }

    @Test
    public void testGetMovingObjectsLayer() {
        assertNotNull(game.getMovingObjectsLayer());
    }

    @Test
    public void testGetParticleLayer() {
        assertNotNull(game.getParticleLayer());
    }

    @Test
    public void testGetAudioFx() {
        assertNotNull(game.getAudioFx());
    }

    @Test
    public void testRenderRenderingContext() {
        fail("Not yet implemented");
    }

    @Test
    public void testStartingGameState() {
        assertEquals(Game.GAME_INTRO, game.getGameState());
    }

    @Test
    public void testSetGameState() {
        game.setGameState(Game.GAME_FINISH_LEVEL);
        assertEquals(Game.GAME_FINISH_LEVEL, game.getGameState());
    }

    @Test
    public void testGetFrogger() {
        assertNotNull(game.getFrogger());
    }

    @Test
    public void testGetWorld() {
        assertNotNull(game.getWorld());
    }

    @Test
    public void testDecrementTimer() {
        int timer = game.getLevelTimer();
        game.decrementTimer();
        assertEquals(--timer, game.getLevelTimer());
    }

    @Test
    public void testResetLevelTimer() {
        game.resetLevelTimer();
        assertEquals(Game.DEFAULT_LEVEL_TIME, game.getLevelTimer());
    }

    @Test
    public void testReset() {
        game.reset();
        assertEquals(0, game.getScore());
        assertEquals(Game.DEFAULT_LEVEL_TIME, game.getLevelTimer());
    }

}
