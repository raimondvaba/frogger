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

import frogger.collision.CollisionObject;
import frogger.goal.ReachableBonusGoal;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Goal extends MovingEntity implements ReachableBonusGoal {

    private boolean isReached = false;
    private boolean isBonus = false;

    public Goal(int location) {
        this(new Vector2D(SPRITE_SIZE * (1 + 2 * location), SPRITE_SIZE));
    }

    public Goal(Vector2D position) {
        super(Graphics.getSpritePath("goal"));
        this.position = position;
        collisionObjects.add(new CollisionObject("colSmall", position));
        sync(position);
        setFrame(0);
    }

    @Override
    public void setReached() {
        isReached = true;
        setFrame(1);
    }

    public void setBonus(boolean b) {
        isBonus = b;
        if (b) 
            setFrame(2);
        else 
            setFrame(0);
    }

    @Override
    public void update(long deltaMs) {
    }

    @Override
    public boolean isReached() {
        return isReached;
    }
    
    @Override
    public boolean isBonus() {
        return isBonus;
    }

}