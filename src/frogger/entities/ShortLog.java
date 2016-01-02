package frogger.entities;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class ShortLog extends MovingEntity {

    public final static int SIZE = 3;
    public final static int LENGTH = SPRITE_SIZE * SIZE;

    public ShortLog(Vector2D pos, Vector2D velocity) {
        super(Graphics.getSpritePath("shortlog"), pos, velocity);
        addEntityCollisionObjects(SIZE);
        setVisibleFrame(velocity, 1, 0);
    }

}