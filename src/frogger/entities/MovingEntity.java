package frogger.entities;

import java.util.LinkedList;
import java.util.List;

import frogger.World;
import frogger.collision.CollisionObject;
import jig.engine.physics.Body;
import jig.engine.util.Vector2D;

public abstract class MovingEntity extends Body {

    public static final int SPRITE_SIZE = 32;

    protected List<CollisionObject> collisionObjects;

    public MovingEntity(String name) {
        super(name);
        collisionObjects = new LinkedList<CollisionObject>();
    }

    public MovingEntity(String name, Vector2D position, Vector2D velocity) {
        this(name);
        this.position = position;
        this.velocity = velocity;
    }

    protected void setVisibleFrame(Vector2D v, int frameOneIndex, int frameTwoIndex) {
        int index = v.getX() < 0 ? frameOneIndex : frameTwoIndex;
        setFrame(index);
    }

    public List<CollisionObject> getCollisionObjects() {
        return collisionObjects;
    }
    
    protected void addEntityCollisionObjects(final int size) {
        for (int i = 0; i < size; i++) {
            Vector2D posSphere = new Vector2D(position.getX() + SPRITE_SIZE * i, position.getY());
            collisionObjects.add(new CollisionObject("colSmall", posSphere));
        }
    }

    public void sync(Vector2D position) {
        int i = 0;
        for (CollisionObject a : collisionObjects) {
            Vector2D deltaPos = new Vector2D(position.getX() + (SPRITE_SIZE * i++), position.getY());
            a.setPosition(deltaPos);
        }
    }

    public void update(final long deltaMs) {
        if (position.getX() > World.WORLD_WIDTH + width || position.getX() < -(SPRITE_SIZE * 4))
            setActivation(false);

        position = new Vector2D(position.getX() + velocity.getX() * deltaMs,
                position.getY() + velocity.getY() * deltaMs);
        sync(position);
    }
}