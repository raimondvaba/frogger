package frogger.entities;

import frogger.collision.CollisionObject;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Crocodile extends MovingEntity {

    public final static int SIZE = 4;
    public final static int LENGTH = SPRITE_SIZE * SIZE;

    private long animationDelay = 300;
    private long animationTime = 0;
    private int startFrame = 0;
    private int nextFrame = 0;

    protected CollisionObject head;

    public CollisionObject getHead() {
        return head;
    }

    public Crocodile(Vector2D pos, Vector2D velocity) {
        super(Graphics.getSpritePath("crocodile"), pos, velocity);

        addEntityCollisionObjects(SIZE);

        if (velocity.getX() < 0) {
            startFrame = 2;
            head = collisionObjects.get(0);
        } else {
            startFrame = 0;
            head = collisionObjects.get(3);
        }

        setFrame(startFrame);
    }

    public void animate(long deltaMs) {
        animationTime += deltaMs;
        if (animationTime > animationDelay) {
            animationTime = 0;
            nextFrame = (nextFrame + 1) % 2;
            setFrame(nextFrame + startFrame);
        }
    }

    @Override
    public void update(final long deltaMs) {
        super.update(deltaMs);
        animate(deltaMs);
    }
}