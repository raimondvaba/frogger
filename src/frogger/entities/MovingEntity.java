/**
 * Copyright (c) 2009 Vitaliy Pavlenko
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package frogger.entities;

import java.util.LinkedList;
import java.util.List;

import frogger.World;
import frogger.collision.CollisionObject;
import jig.engine.physics.Body;
import jig.engine.util.Vector2D;

/**
 * Abstract class for moving entities in the game
 * 
 * They all have update, sync methods and underlining collision spheres
 * 
 * @author vitaliy
 *
 */
public abstract class MovingEntity extends Body {

    public static final int SPRITE_SIZE = 32;

    // List that holds collision spheres
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
    
    /**
     * 
     * 
     * @param size - amount of objects to add
     */
    protected void addEntityCollisionObjects(final int size) {
        for (int i = 0; i < size; i++) {
            Vector2D posSphere = new Vector2D(position.getX() + SPRITE_SIZE * i, position.getY());
            collisionObjects.add(new CollisionObject("colSmall", posSphere));
        }
    }

    /**
     * Updates the collision spheres with new position
     * 
     * @param position
     */
    public void sync(Vector2D position) {
        int i = 0;
        for (CollisionObject a : collisionObjects) {
            Vector2D deltaPos = new Vector2D(position.getX() + (SPRITE_SIZE * i++), position.getY());
            a.setPosition(deltaPos);
        }
    }

    /**
     * Check bounds in the game
     * 
     * The way this game works, we only worry about the x-axis
     * 
     * None of the objects (except the Frogger which has it's own collision
     * detection) travel in y-axis
     */
    public void update(final long deltaMs) {
        if (position.getX() > World.WORLD_WIDTH + width || position.getX() < -(SPRITE_SIZE * 4))
            setActivation(false);

        position = new Vector2D(position.getX() + velocity.getX() * deltaMs,
                position.getY() + velocity.getY() * deltaMs);
        sync(position);
    }
}