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

package frogger;

import java.util.List;

import jig.engine.physics.AbstractBodyLayer;
import jig.engine.util.Vector2D;

public class FroggerCollisionDetection {

    public static final int STEP_SIZE = 32;
    public Frogger frog;
    public CollisionObject frogSphere;

    // River and Road bounds, all we care about is Y axis in this game
    public int riverVerticalStart = 1 * STEP_SIZE;
    public int riverVerticalEnd = riverVerticalStart + 6 * STEP_SIZE;
    public int roadVerticalStart = 8 * STEP_SIZE;
    public int roadVerticalEnd = roadVerticalStart + 5 * STEP_SIZE;

    public FroggerCollisionDetection(Frogger f) {
        frog = f;
        frogSphere = frog.getCollisionObjects().get(0);
    }

    public void testCollision(AbstractBodyLayer<MovingEntity> l) {

        if (!frog.isAlive)
            return;

        Vector2D frogPos = frogSphere.getCenterPosition();
        double dist2;

        if (isOutOfBounds()) {
            frog.die();
            return;
        }

        for (MovingEntity i : l) {
            if (!i.isActive())
                continue;

            List<CollisionObject> collisionObjects = i.getCollisionObjects();

            for (CollisionObject objectSphere : collisionObjects) {
                dist2 = (frogSphere.getRadius() + objectSphere.getRadius())
                        * (frogSphere.getRadius() + objectSphere.getRadius());

                if (frogPos.distance2(objectSphere.getCenterPosition()) < dist2) {
                    collide(i, objectSphere);
                    return;
                }
            }
        }

        if (isInRiver()) {
            frog.die();
            return;
        }

        // frog.allignXPositionToGrid();
    }

    /**
     * Check game area bounds
     * 
     * @return
     */
    public boolean isOutOfBounds() {
        Vector2D frogPos = frogSphere.getCenterPosition();
        if (frogPos.getY() < STEP_SIZE || frogPos.getY() > Main.WORLD_HEIGHT)
            return true;
        if (frogPos.getX() < 0 || frogPos.getX() > Main.WORLD_WIDTH)
            return true;
        return false;
    }

    /**
     * Bound check if the frog is in river
     * 
     * @return
     */
    public boolean isInRiver() {
        Vector2D frogPos = frogSphere.getCenterPosition();

        if (frogPos.getY() > riverVerticalStart && frogPos.getY() < riverVerticalEnd)
            return true;

        return false;
    }

    /**
     * Bound check if the frog is on the road
     * 
     * @return
     */
    public boolean isOnRoad() {
        Vector2D frogPos = frogSphere.getCenterPosition();

        if (frogPos.getY() > roadVerticalStart && frogPos.getY() < roadVerticalEnd)
            return true;

        return false;
    }

    public void collide(MovingEntity m, CollisionObject s) {

        if (m instanceof Truck || m instanceof Car || m instanceof CopCar) {
            frog.die();
        }

        if (m instanceof Crocodile) {
            if (s == ((Crocodile) m).head)
                frog.die();
            else
                frog.follow(m);
        }

        /* Follow the log */
        if (m instanceof LongLog || m instanceof ShortLog) {
            frog.follow(m);
        }

        if (m instanceof Turtles) {
            if (((Turtles) m).isUnderwater == true)
                frog.die();
            frog.follow(m);
        }

        /* Reach a goal */
        if (m instanceof Goal) {
            frog.reach((Goal) (m));
        }
    }
}
