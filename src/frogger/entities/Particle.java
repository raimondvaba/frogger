package frogger.entities;

import jig.engine.util.Vector2D;

public class Particle extends MovingEntity {

    private int timeExpire;
    private int timeAlive;

    public Particle(String spritePath, Vector2D position, Vector2D velocity) {
        this(spritePath, position, velocity, 0);
    }

    public Particle(String spritePath, Vector2D position, Vector2D velocity, int timeExpire) {
        super(spritePath);
        this.position = position;
        this.velocity = velocity;
        this.timeExpire = timeExpire;
        setActivation(true);
    }

    @Override
    public void update(final long deltaMs) {
        super.update(deltaMs);

        if (timeExpire != 0) {
            timeAlive += deltaMs;
            if (timeAlive > timeExpire)
                setActivation(false);
        }
    }
}
