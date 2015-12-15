package frogger.entities;

import java.util.Random;

import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class Car extends MovingEntity {

	private static int TYPES = 3;
    private static int SIZE = 1;
    public final static int LENGTH = SPRITE_SIZE * SIZE;
    private static Random random = new Random(System.currentTimeMillis());

    public Car(Vector2D pos, Vector2D velocity) {
        super(Graphics.SPRITE_SHEET + "#car" + random.nextInt(TYPES), pos, velocity);
        addEntityCollisionObjects(SIZE);
        setVisibleFrame(velocity, 1, 0);
    }

	public int getLENGTH() {
		return LENGTH;
	}

}