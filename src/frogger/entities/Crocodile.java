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

import frogger.Main;
import frogger.collision.CollisionObject;
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
        super(Main.SPRITE_SHEET + "#crocodile", pos, velocity);

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