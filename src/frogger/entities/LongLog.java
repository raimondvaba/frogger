package frogger.entities;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class LongLog extends MovingEntity {

    public final static int SIZE = 4;
    public final static int LENGTH = SPRITE_SIZE * SIZE;

    public LongLog(Vector2D position, Vector2D velocity) {
        super(Graphics.getSpritePath("longlog"), position, velocity);
        addEntityCollisionObjects(SIZE);
        setVisibleFrame(velocity, 1, 0);
    }
}