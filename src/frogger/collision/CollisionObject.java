package frogger.collision;

import jig.engine.physics.vpe.VanillaSphere;
import jig.engine.util.Vector2D;

public class CollisionObject extends VanillaSphere {

    private static final int MIDDLE_OFFSET = 16;

    public CollisionObject(Vector2D pos) {
        super("col");
        setPosition(pos);
    }

    public CollisionObject(String name, Vector2D pos) {
        super(name);
        setPosition(pos);
    }

    @Override
    public void setPosition(Vector2D pos) {
        double dX = MIDDLE_OFFSET - getRadius();
        double dY = -getRadius() + MIDDLE_OFFSET;
        position = new Vector2D(pos.getX() + dX, pos.getY() + dY);
    }

    @Override
    public void update(long deltaMs) {}

}