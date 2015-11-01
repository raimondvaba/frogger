package frogger;

import jig.engine.util.Vector2D;

public class Frogger extends MovingEntity {

    final static int MOVE_STEP = STEP_SIZE;

    final static private int ANIMATION_STEP = 4;

    private int curAnimationFrame = 0;
    private int finalAnimationFrame = 0;
    private long animationDelay = 10;
    private long animationBeginTime = 0;
    private boolean isAnimating = false;
    private Vector2D dirAnimation = new Vector2D(0, 0);

    private MovingEntity followObject = null;

    public boolean isAlive = false;
    private long timeOfDeath = 0;

    private int currentFrame = 0;
    private int tmpFrame = 0;

    public int deltaTime = 0;

    public boolean cheating = false;

    public boolean hw_hasMoved = false;

    private Main game;

    public Frogger(Main g) {
        super(Main.SPRITE_SHEET + "#frog");
        game = g;
        resetFrog();
        collisionObjects.add(new CollisionObject(position));
    }

    public void resetFrog() {
        isAlive = true;
        isAnimating = false;
        currentFrame = 0;
        followObject = null;
        position = Main.FROGGER_START;
        game.levelTimer = Main.DEFAULT_LEVEL_TIME;
    }

    public void moveLeft() {
        if (getCenterPosition().getX() - 16 > 0 && isAlive && !isAnimating) {
            currentFrame = 3;
            move(new Vector2D(-1, 0));
            AudioEfx.frogJump.play(0.2);
        }
    }

    public void moveRight() {

        if (getCenterPosition().getX() + STEP_SIZE < Main.WORLD_WIDTH && isAlive && !isAnimating) {
            currentFrame = 2;
            move(new Vector2D(1, 0));
            AudioEfx.frogJump.play(0.2);
        }
    }

    public void moveUp() {
        if (position.getY() > 32 && isAlive && !isAnimating) {
            currentFrame = 0;
            move(new Vector2D(0, -1));
            AudioEfx.frogJump.play(0.2);
        }
    }

    public void moveDown() {
        if (position.getY() < Main.WORLD_HEIGHT - MOVE_STEP && isAlive && !isAnimating) {
            currentFrame = 1;
            move(new Vector2D(0, 1));
            AudioEfx.frogJump.play(0.2);
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

        if (!isAnimating || !isAlive) {
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

    public void allignXPositionToGrid() {
        if (isAnimating || followObject != null)
            return;
        double x = position.getX();
        x = Math.round(x / 32) * 32;
        position = new Vector2D(x, position.getY());

    }

    public void updateFollow(long deltaMs) {
        if (followObject == null || !isAlive)
            return;
        Vector2D dS = followObject.getVelocity().scale(deltaMs);
        position = new Vector2D(position.getX() + dS.getX(), position.getY() + dS.getY());
    }

    public void follow(MovingEntity log) {
        followObject = log;
    }

    public void windReposition(Vector2D d) {
        if (isAlive) {
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
            AudioEfx.frogDie.play(0.2);
            followObject = null;
            isAlive = false;
            currentFrame = 4; // dead sprite
            game.GameLives--;
            hw_hasMoved = true;
        }

        timeOfDeath = getTime();
        game.levelTimer = Main.DEFAULT_LEVEL_TIME;
    }

    public void reach(final Goal g) {
        if (g.isReached == false) {
            AudioEfx.frogGoal.play(0.4);
            game.GameScore += 100;
            game.GameScore += game.levelTimer;
            if (g.isBonus) {
                AudioEfx.bonus.play(0.2);
                game.GameLives++;
            }
            g.reached();
            resetFrog();
        } else {
            setPosition(g.getPosition());
        }
    }

    public void update(final long deltaMs) {
        if (game.GameLives <= 0)
            return;

        if (!isAlive && timeOfDeath + 2000 < System.currentTimeMillis())
            resetFrog();

        updateAnimation();
        updateFollow(deltaMs);
        setFrame(currentFrame);

        // Level timer stuff
        deltaTime += deltaMs;
        if (deltaTime > 1000) {
            deltaTime = 0;
            game.levelTimer--;
        }

        if (game.levelTimer <= 0)
            die();
    }
}