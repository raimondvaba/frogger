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
import jig.engine.util.Vector2D;

/**
 * Besides being a simple MovingEntity
 * 
 * Turtles go under water every now and then and when they're submerged, they
 * can't hold the frog, otherwise they behave just like tree logs.
 * 
 * @author vitaliy
 *
 */
public class Turtles extends MovingEntity {
    
    private final static int SIZE = 3;

    private long underwaterTime = 0;
    private long underwaterPeriod = 1200;

    protected boolean isUnderwater = false;

    public boolean isUnderwater() {
        return isUnderwater;
    }

    // Animation variables
    private boolean isAnimating = false;
    private long localDeltaMs;
    private long startAnimatingMs;
    private long timerMs;
    private static final long ANIMATING_PERIOD_MS = 150;
    private int aFrame = 0;
    private int max_aFrame = 2; // Animate only 2 frames

    /**
     * Build a Turtle object that is floating by default
     * 
     * submerging still happens according to the period defined by the
     * underwaterPeriod
     * 
     * @param pos
     * @param velocity
     */
    public Turtles(Vector2D pos, Vector2D velocity) {
        super(Main.SPRITE_SHEET + "#turtles");
        init(pos, velocity);
    }

    /**
     * Build a submerged or floating object
     * 
     * @param pos
     * @param v
     * @param water
     *            - 0 submerged; 1 - floating
     */
    public Turtles(Vector2D pos, Vector2D v, int water) {
        super(Main.SPRITE_SHEET + "#turtles");
        init(pos, v);

        // set submerged/floating state based on water variable
        if (water == 0) {
            isUnderwater = false;
        } else {
            isUnderwater = true;
            setFrame(getFrame() + 2);
        }

    }

    /**
     * Initializing the Turtles object
     * 
     * building collision spheres, etc
     * 
     * @param pos
     *            - position vector
     * @param v
     *            - velocity vector
     */
    public void init(Vector2D pos, Vector2D v) {
        position = pos;
        addEntityCollisionObjects(SIZE);
        velocity = v;
        setVisibleFrame(v, 0, 3);
    }

    /**
     * Timer to go under water or float
     */
    public void checkAirTime() {
        underwaterTime += localDeltaMs;
        if (underwaterTime > underwaterPeriod) {
            underwaterTime = 0;
            startAnimation();
        }
    }

    /**
     * Perform Animating sequence of submerging/floating based on a bunch of
     * local time variables at the top
     */
    public void animate() {
        if (!isAnimating)
            return;

        if (startAnimatingMs < timerMs) {
            startAnimatingMs = timerMs + ANIMATING_PERIOD_MS;

            if (isUnderwater)
                setFrame(getFrame() - 1);
            else
                setFrame(getFrame() + 1);

            aFrame++;
        }

        if (aFrame >= max_aFrame) {
            isAnimating = false;
            isUnderwater = !isUnderwater;
        }
    }

    /**
     * Initiate the animation by reseting the animation variables
     * and flagging "isAnimationg"
     */
    public void startAnimation() {
        isAnimating = true;
        startAnimatingMs = 0;
        timerMs = 0;
        aFrame = 0;
    }

    public void update(final long deltaMs) {
        super.update(deltaMs);
        localDeltaMs = deltaMs;
        timerMs += localDeltaMs;
        checkAirTime();
        animate();
    }
}