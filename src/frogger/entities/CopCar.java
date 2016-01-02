package frogger.entities;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class CopCar extends MovingEntity {
    
    private final static int SIZE = 1;

    public CopCar(Vector2D pos, Vector2D velocity) {
        super(Graphics.getSpritePath("copcar"), pos, velocity);
        addEntityCollisionObjects(SIZE);
        setVisibleFrame(velocity, 1, 0);
    }

}