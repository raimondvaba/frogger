package frogger.entities;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Truck extends MovingEntity {

    private final static int SIZE = 2;
    public final static int LENGTH = SPRITE_SIZE * SIZE;

    public Truck(Vector2D position, Vector2D velocity) {
        super(Graphics.getSpritePath("truck"), position, velocity);
        addEntityCollisionObjects(SIZE);
        setVisibleFrame(velocity, 1, 0);
    }
}