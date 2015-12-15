package frogger.entities;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Turtles extends MovingEntity {
    
    private final static int SIZE = 3;

    private long underwaterTime = 0;
    private long underwaterMaximumPeriod = 1200;

    protected boolean isUnderwater = false;

    public boolean isUnderwater() {
        return isUnderwater;
    }

    private boolean isAnimating = false;
    private long localDeltaMs;
    private long startAnimatingMs;
    private long timerMs;
    private static final long ANIMATING_PERIOD_MS = 150;
    private int currentFrame = 0;
    private int frameMax = 2; // Animate only 2 frames

    public Turtles(Vector2D position, Vector2D velocity) {
        super(Graphics.SPRITE_SHEET + "#turtles");
        init(position, velocity);
    }

    public Turtles(Vector2D position, Vector2D velocity, int water) {
        super(Graphics.SPRITE_SHEET + "#turtles");
        init(position, velocity);

        if (water == 0) {
            isUnderwater = false;
        } else {
            isUnderwater = true;
            setFrame(getFrame() + 2);
        }

    }

    public void init(Vector2D position, Vector2D velocity) {
        this.position = position;
        addEntityCollisionObjects(SIZE);
        this.velocity = velocity;
        setVisibleFrame(velocity, 0, 3);
    }

    public void checkAirTime() {
        underwaterTime += localDeltaMs;
        if (underwaterTime > underwaterMaximumPeriod) {
            underwaterTime = 0;
            startAnimation();
        }
    }

    public void animate() {
        if (!isAnimating)
            return;

        if (startAnimatingMs < timerMs) {
            startAnimatingMs = timerMs + ANIMATING_PERIOD_MS;

            if (isUnderwater)
                setFrame(getFrame() - 1);
            else
                setFrame(getFrame() + 1);

            currentFrame++;
        }

        if (currentFrame >= frameMax) {
            isAnimating = false;
            isUnderwater = !isUnderwater;
        }
    }

    public void startAnimation() {
        isAnimating = true;
        startAnimatingMs = 0;
        timerMs = 0;
        currentFrame = 0;
    }

    public void update(final long deltaMs) {
        super.update(deltaMs);
        localDeltaMs = deltaMs;
        timerMs += localDeltaMs;
        checkAirTime();
        animate();
    }
}