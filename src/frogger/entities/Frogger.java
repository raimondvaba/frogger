package frogger.entities;

import frogger.Game;
import frogger.World;
import frogger.audio.AudioEfx;
import frogger.collision.CollisionObject;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Frogger extends MovingEntity {

    private static final int STARTING_FROGGER_LIVES = 5;
    private int lives = STARTING_FROGGER_LIVES;

    private static final int ONE_SECOND_MS = 1000;

    private static final double SOUND_DURATION = 0.2;

    final static int MOVE_STEP = SPRITE_SIZE;
    private static final Vector2D FROGGER_START = new Vector2D(6 * SPRITE_SIZE, World.WORLD_HEIGHT - SPRITE_SIZE);

    final static private int ANIMATION_STEP = 4;

    private int curAnimationFrame = 0;
    private int finalAnimationFrame = 0;
    private long animationDelay = 10;
    private long animationBeginTime = 0;
    private boolean isAnimating = false;
    private Vector2D dirAnimation = new Vector2D(0, 0);

    private MovingEntity followObject = null;

    private boolean alive = false;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private long timeOfDeath = 0;

    private int currentFrame = 0;
    private int tmpFrame = 0;

    public int deltaTime = 0;

    public boolean cheating = false;

    public boolean hw_hasMoved = false;

    private Game game;

    public Frogger(Game game) {
        super(Graphics.SPRITE_SHEET + "#frog");
        this.game = game;
        resetFrog();
        collisionObjects.add(new CollisionObject(position));
    }

    public void resetFrog() {
        alive = true;
        isAnimating = false;
        currentFrame = 0;
        followObject = null;
        position = FROGGER_START;
        game.resetLevelTimer();
    }

    public void moveLeft() {
        if (getCenterPosition().getX() - SPRITE_SIZE / 2 > 0 && alive && !isAnimating) {
            currentFrame = 3;
            move(new Vector2D(-1, 0));
            AudioEfx.frogJump.play(SOUND_DURATION);
        }
    }

    public void moveRight() {

        if (getCenterPosition().getX() + SPRITE_SIZE < World.WORLD_WIDTH && alive && !isAnimating) {
            currentFrame = 2;
            move(new Vector2D(1, 0));
            AudioEfx.frogJump.play(SOUND_DURATION);
        }
    }

    public void moveUp() {
        if (position.getY() > MOVE_STEP && alive && !isAnimating) {
            currentFrame = 0;
            move(new Vector2D(0, -1));
            AudioEfx.frogJump.play(SOUND_DURATION);
        }
    }

    public void moveDown() {
        if (position.getY() < World.WORLD_HEIGHT - MOVE_STEP && alive && !isAnimating) {
            currentFrame = 1;
            move(new Vector2D(0, 1));
            AudioEfx.frogJump.play(SOUND_DURATION);
        }
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void move(Vector2D dir) {
        followObject = null;
        curAnimationFrame = 0;
        finalAnimationFrame = MOVE_STEP / ANIMATION_STEP;
        isAnimating = true;
        hw_hasMoved = true;
        animationBeginTime = getTime();
        dirAnimation = dir;

        tmpFrame = currentFrame;
        currentFrame += 5;

        sync(new Vector2D(position.getX() + dirAnimation.getX() * MOVE_STEP,
                position.getY() + dirAnimation.getY() * MOVE_STEP));
    }

    public void updateAnimation() {

        if (!isAnimating || !alive) {
            sync(position);
            return;
        }

        if (curAnimationFrame >= finalAnimationFrame) {
            isAnimating = false;
            currentFrame = tmpFrame;
            return;
        }

        if (animationBeginTime + animationDelay < getTime()) {
            animationBeginTime = getTime();
            position = new Vector2D(position.getX() + dirAnimation.getX() * ANIMATION_STEP,
                    position.getY() + dirAnimation.getY() * ANIMATION_STEP);
            curAnimationFrame++;
            return;
        }
    }

    public void updateFollow(long deltaMs) {
        if (followObject == null || !alive)
            return;
        Vector2D dS = followObject.getVelocity().scale(deltaMs);
        position = new Vector2D(position.getX() + dS.getX(), position.getY() + dS.getY());
    }

    public void follow(MovingEntity log) {
        followObject = log;
    }

    public void windReposition(Vector2D d) {
        if (alive) {
            hw_hasMoved = true;
            setPosition(new Vector2D(getPosition().getX() + d.getX(), getPosition().getY()));
            sync(position);
        }
    }

    public void randomJump(final int rDir) {
        switch (rDir) {
            case 0:
                moveLeft();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveUp();
                break;
            default:
                moveDown();
        }
    }

    public void die() {
        if (isAnimating)
            return;

        if (!cheating) {
            AudioEfx.frogDie.play(SOUND_DURATION);
            followObject = null;
            alive = false;
            currentFrame = 4; // dead sprite
            lives--;
            hw_hasMoved = true;
        }

        timeOfDeath = getTime();
        game.resetLevelTimer();
    }

    public void reach(final Goal g) {
        if (g.isReached() == false) {
            AudioEfx.frogGoal.play(0.4);
            game.increaseScore(100);
            game.increaseScore(game.getLevelTimer());
            if (g.isBonus()) {
                AudioEfx.bonus.play(SOUND_DURATION);
                lives++;
            }
            g.setReached();
            resetFrog();
        } else {
            setPosition(g.getPosition());
        }
    }

    public void update(final long deltaMs) {
        if (getLives() <= 0)
            return;

        if (!alive && timeOfDeath + 2 * ONE_SECOND_MS < System.currentTimeMillis())
            resetFrog();

        updateAnimation();
        updateFollow(deltaMs);
        setFrame(currentFrame);

        // Level timer stuff
        deltaTime += deltaMs;
        if (deltaTime > ONE_SECOND_MS) {
            deltaTime = 0;
            game.decrementTimer();
        }

        if (game.getLevelTimer() <= 0)
            die();
    }

    public int getLives() {
        return lives;
    }

    public void resetLives() {
        lives = STARTING_FROGGER_LIVES;
    }

}