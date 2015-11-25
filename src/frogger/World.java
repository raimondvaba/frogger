package frogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frogger.effects.HeatWave;
import frogger.effects.WindGust;
import frogger.entities.Goal;
import frogger.entities.MovingEntity;
import frogger.factory.MovingEntityFactory;
import frogger.goal.GoalManager;
import jig.engine.util.Vector2D;

public class World {

    private static final int HEIGHT = 14;
    private static final int WIDTH = 13;
    static final int SPRITE_SIZE = 32;
    public final static int WORLD_WIDTH = WIDTH * SPRITE_SIZE;
    public final static int WORLD_HEIGHT = HEIGHT * SPRITE_SIZE;

    private final List<MovingEntityFactory> road = new ArrayList<>(5);
    private final List<MovingEntityFactory> river = new ArrayList<>(5);
    private final WindGust windGust;
    private final HeatWave heatWave;

    private final GoalManager goalManager;

    private Game game;

    public World(Game game) {
        this.game = game;
        this.goalManager = new GoalManager();
        heatWave = new HeatWave();
        windGust = new WindGust();
        initialize(game.getCurrentLevel().getLevel());
    }

    public void initialize(final int level) {

        /*
         * dV is the velocity multiplier for all moving objects at the current
         * game level
         */
        double dV = level * 0.05 + 1;

        game.getMovingObjectsLayer().clear();

        /* River Traffic */
        river.clear();
        river.addAll(Arrays.asList(
                new MovingEntityFactory(new Vector2D(-(SPRITE_SIZE * 3), 2 * SPRITE_SIZE), new Vector2D(0.06 * dV, 0)),
                new MovingEntityFactory(new Vector2D(WORLD_WIDTH, 3 * SPRITE_SIZE), new Vector2D(-0.04 * dV, 0)),
                new MovingEntityFactory(new Vector2D(-(SPRITE_SIZE * 3), 4 * SPRITE_SIZE), new Vector2D(0.09 * dV, 0)),
                new MovingEntityFactory(new Vector2D(-(SPRITE_SIZE * 4), 5 * SPRITE_SIZE), new Vector2D(0.045 * dV, 0)),
                new MovingEntityFactory(new Vector2D(WORLD_WIDTH, 6 * SPRITE_SIZE), new Vector2D(-0.045 * dV, 0))));

        /* Road Traffic */
        road.clear();
        road.addAll(Arrays
                .asList(new MovingEntityFactory(new Vector2D(WORLD_WIDTH, 8 * SPRITE_SIZE), new Vector2D(-0.1 * dV, 0)),
                        new MovingEntityFactory(new Vector2D(-(SPRITE_SIZE * 4), 9 * SPRITE_SIZE),
                                new Vector2D(0.08 * dV, 0)),
                new MovingEntityFactory(new Vector2D(WORLD_WIDTH, 10 * SPRITE_SIZE), new Vector2D(-0.12 * dV, 0)),
                new MovingEntityFactory(new Vector2D(-(SPRITE_SIZE * 4), 11 * SPRITE_SIZE),
                        new Vector2D(0.075 * dV, 0)),
                new MovingEntityFactory(new Vector2D(WORLD_WIDTH, 12 * SPRITE_SIZE), new Vector2D(-0.05 * dV, 0))));

        goalManager.init(game.getCurrentLevel().getLevel());
        for (Goal goal : goalManager.get()) {
            game.getMovingObjectsLayer().add(goal);
        }

        /*
         * Build some traffic before game starts running
         * MovingEntityFactories for fews cycles
         */
        for (int i = 0; i < 500; i++)
            cycleTraffic(10);

    }

    public void update(final long deltaMs) {
        windGust.update(deltaMs);
        heatWave.update(deltaMs);
        cycleTraffic(deltaMs);
    }

    public void cycleTraffic(long deltaMs) {
        MovingEntity me;
        MovingEntityFactory mef;

        /* Road traffic updates */
        for (MovingEntityFactory lane : road) {
            lane.update(deltaMs);
            if ((me = lane.buildVehicle()) != null) {
                game.getMovingObjectsLayer().add(me);
            }
        }

        /* River traffic updates */
        int i = 0;
        mef = river.get(i++);
        mef.update(deltaMs);
        if ((me = mef.buildShortLogWithTurtles(40)) != null)
            game.getMovingObjectsLayer().add(me);

        mef = river.get(i++);
        mef.update(deltaMs);
        if ((me = mef.buildLongLogWithCrocodile(30)) != null)
            game.getMovingObjectsLayer().add(me);

        mef = river.get(i++);
        mef.update(deltaMs);
        if ((me = mef.buildShortLogWithTurtles(50)) != null)
            game.getMovingObjectsLayer().add(me);

        mef = river.get(i++);
        mef.update(deltaMs);
        if ((me = mef.buildLongLogWithCrocodile(20)) != null)
            game.getMovingObjectsLayer().add(me);

        mef = river.get(i++);
        mef.update(deltaMs);
        if ((me = mef.buildShortLogWithTurtles(10)) != null)
            game.getMovingObjectsLayer().add(me);

        // Do Wind
        if ((me = windGust.genParticles(game.getCurrentLevel().getLevel())) != null)
            game.getParticleLayer().add(me);

        // HeatWave
        if ((me = heatWave.genParticles(game.getFrogger().getCenterPosition())) != null)
            game.getParticleLayer().add(me);

        game.getMovingObjectsLayer().update(deltaMs);
        game.getParticleLayer().update(deltaMs);
    }

    public GoalManager getGoalManager() {
        return goalManager;
    }

    public WindGust getWindGust() {
        return windGust;
    }

    public HeatWave getHeatWave() {
        return heatWave;
    }

}
