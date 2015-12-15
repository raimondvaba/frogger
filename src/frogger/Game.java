package frogger;

import frogger.audio.AudioEfx;
import frogger.collision.FroggerCollisionDetection;
import frogger.entities.Frogger;
import frogger.entities.MovingEntity;
import frogger.graphics.Graphics;
import frogger.keyboard.KeyboardManager;
import frogger.ui.FroggerUI;
import jig.engine.RenderingContext;
import jig.engine.hli.StaticScreenGame;
import jig.engine.physics.AbstractBodyLayer;

public class Game extends StaticScreenGame {

    public static final int GAME_INTRO = 0;
    public static final int GAME_PLAY = 1;
    public static final int GAME_FINISH_LEVEL = 2;
    public static final int GAME_INSTRUCTIONS = 3;
    public static final int GAME_OVER = 4;

    private int score = 0;

    public void increaseScore(int increment) {
        score += increment;
    }

    static final int DEFAULT_LEVEL_TIME = 60;
    private int levelTimer = DEFAULT_LEVEL_TIME;

    private World world;
    private int gameState = GAME_INTRO;
    private AbstractBodyLayer<MovingEntity> movingObjectsLayer = new AbstractBodyLayer.IterativeUpdate<MovingEntity>();

    public AbstractBodyLayer<MovingEntity> getMovingObjectsLayer() {
        return movingObjectsLayer;
    }

    private AbstractBodyLayer<MovingEntity> particleLayer = new AbstractBodyLayer.IterativeUpdate<MovingEntity>();

    public AbstractBodyLayer<MovingEntity> getParticleLayer() {
        return particleLayer;
    }

    private Frogger frogger;
    private FroggerUI ui;
    private FroggerCollisionDetection collisionDetection;
    private AudioEfx audioFx;

    public AudioEfx getAudioFx() {
        return audioFx;
    }

    private KeyboardManager keyboardManager;
    private Level level;
    private Graphics graphics;

    public Level getCurrentLevel() {
        return level;
    }

    public Game() {
        super(World.WORLD_WIDTH, World.WORLD_HEIGHT, false);
        score = 0;
        graphics = new Graphics();
        this.frogger = new Frogger(this);
        collisionDetection = new FroggerCollisionDetection(frogger);
        audioFx = new AudioEfx(collisionDetection, frogger);
        this.level = new Level();
        this.world = new World(this);
        this.ui = new FroggerUI(this);
        this.keyboardManager = new KeyboardManager(keyboard, this, frogger);
        gameframe.setTitle("Frogger");
    }

    public void update(final long deltaMs) {
        switch (gameState) {
            case GAME_PLAY:
                keyboardManager.froggerKeyboardHandler();
                world.update(deltaMs);
                // wind.update(deltaMs);
                // hwave.update(deltaMs);
                frogger.update(deltaMs);
                audioFx.update(deltaMs);
                ui.update(deltaMs);

                // world.cycleTraffic(deltaMs);
                collisionDetection.testCollision(movingObjectsLayer);

                // Wind gusts work only when Frogger is on the river
                if (collisionDetection.isInRiver())
                    world.getWindGust().start(level.getLevel());
                world.getWindGust().perform(frogger, level.getLevel(), deltaMs);

                // Do the heat wave only when Frogger is on hot pavement
                if (collisionDetection.isOnRoad())
                    world.getHeatWave().start(frogger, level.getLevel());
                world.getHeatWave().perform(frogger, deltaMs, level.getLevel());

                if (!frogger.isAlive)
                    particleLayer.clear();

                world.getGoalManager().update(deltaMs);

                if (world.getGoalManager().getUnreached().size() == 0) {
                    gameState = GAME_FINISH_LEVEL;
                    audioFx.playCompleteLevel();
                    particleLayer.clear();
                }

                if (frogger.getLives() < 1) {
                    gameState = GAME_OVER;
                }

                break;

            case GAME_OVER:
            case GAME_INSTRUCTIONS:
            case GAME_INTRO:
                world.getGoalManager().update(deltaMs);
                keyboardManager.menuKeyboardHandler();
                world.cycleTraffic(deltaMs);
                break;

            case GAME_FINISH_LEVEL:
                keyboardManager.finishLevelKeyboardHandler();
                break;
        }
    }

    /**
     * Rendering game objects
     */
    public void render(RenderingContext rc) {
        switch (gameState) {
            case GAME_FINISH_LEVEL:
            case GAME_PLAY:
                graphics.getBackgroundLayer().render(rc);

                if (frogger.isAlive) {
                	
                    movingObjectsLayer.render(rc);
                    // frog.collisionObjects.get(0).render(rc);
                    frogger.render(rc);
                } else {
                    frogger.render(rc);
                    movingObjectsLayer.render(rc);
                }

                particleLayer.render(rc);
                ui.render(rc);
                break;

            case GAME_OVER:
            case GAME_INSTRUCTIONS:
            case GAME_INTRO:
                graphics.getBackgroundLayer().render(rc);
                movingObjectsLayer.render(rc);
                ui.render(rc);
                break;
        }
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Frogger getFrogger() {
        return frogger;
    }

    public World getWorld() {
        return world;
    }

    public int getLevelTimer() {
        return levelTimer;
    }

    public void decrementTimer() {
        levelTimer--;
    }

    public int getScore() {
        return score;
    }

    private void resetScore() {
        score = 0;
    }

    public void resetLevelTimer() {
        levelTimer = DEFAULT_LEVEL_TIME;
    }

    public void reset() {
        resetLevelTimer();
        resetScore();
    }

}
