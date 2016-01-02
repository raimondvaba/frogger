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