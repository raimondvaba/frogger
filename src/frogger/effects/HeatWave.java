package frogger.effects;

import java.util.Random;

import frogger.audio.AudioEfx;
import frogger.entities.Frogger;
import frogger.entities.MovingEntity;
import frogger.entities.Particle;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;


public class HeatWave {

    final static int PERIOD_MS = 2000;
    final static int DURATION_MS = 1000;

    private Random random;

    private long timeMs;
    private long durationMs;
    
    private int levelMultiplier = 10;

    public boolean isHot;

    public HeatWave() {
        isHot = false;
        timeMs = 0;
        random = new Random(System.currentTimeMillis());
    }


    public void perform(Frogger frogger, final long deltaMs, final int level) {
        if (!frogger.isAlive()) {
            isHot = false;
            return;
        }

        if (isHot && durationMs > (DURATION_MS - (level * levelMultiplier)) && !frogger.hw_hasMoved) {
            frogger.randomJump(random.nextInt(4));
            isHot = false;
        }

        if (frogger.hw_hasMoved)
            isHot = false;
    }

    public void start(Frogger frogger, final int gameLevel) {

        if (!isHot && timeMs > PERIOD_MS) {
            if (random.nextInt(100) < gameLevel * levelMultiplier) {
                durationMs = 1;
                isHot = true;
                frogger.hw_hasMoved = false;
                AudioEfx.heat.play(0.2);
            }
            timeMs = 0;
        }
    }

    public MovingEntity genParticles(Vector2D position) {
        if (!isHot || random.nextInt(100) > 10)
            return null;

        Vector2D velocity = new Vector2D((random.nextDouble() - 0.5) * 0.1, (random.nextDouble() - 0.5) * 0.1);

        return new Particle(Graphics.getSpritePath("smoke_cloud"), position, velocity, DURATION_MS);
    }

    public void update(final long deltaMs) {
        timeMs += deltaMs;
        durationMs += deltaMs;
    }
}
