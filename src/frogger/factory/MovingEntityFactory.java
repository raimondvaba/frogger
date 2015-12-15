
package frogger.factory;

import java.util.Random;

import frogger.World;
import frogger.entities.Car;
import frogger.entities.CopCar;
import frogger.entities.Crocodile;
import frogger.entities.LongLog;
import frogger.entities.MovingEntity;
import frogger.entities.ShortLog;
import frogger.entities.Truck;
import frogger.entities.Turtles;
import jig.engine.util.Vector2D;

public class MovingEntityFactory {

    private int CAR = 0;
    private int TRUCK = 1;
    private int SLOG = 2;
    private int LLOG = 3;
    private int STEP_SIZE = 32;

    private Vector2D position;
    private Vector2D velocity;

    private Random random;

    private long updateMs = 0;
    private long copCarDelay = 0;

    private long rateMs = 1000;

    private int padding = STEP_SIZE * 2; 

    private int[] creationRate = new int[4];

    public MovingEntityFactory(Vector2D pos, Vector2D velocity) {
        position = pos;
        this.velocity = velocity;
        random = new Random(System.currentTimeMillis());
        creationRate[CAR] = (int) Math.round(((Car.LENGTH) + padding + STEP_SIZE) / Math.abs(velocity.getX()));
        creationRate[TRUCK] = (int) Math.round(((Truck.LENGTH) + padding + STEP_SIZE) / Math.abs(velocity.getX()));
        creationRate[SLOG] = (int) Math.round(((ShortLog.LENGTH) + padding - STEP_SIZE) / Math.abs(velocity.getX()));
        creationRate[LLOG] = (int) Math.round(((LongLog.LENGTH) + padding - STEP_SIZE) / Math.abs(velocity.getX()));
    }

    public MovingEntity buildBasicObject(int type, int chance) {
        if (updateMs > rateMs) {
            updateMs = 0;

            rateMs = creationRate[type];

            if (random.nextInt(100) < chance)
                switch (type) {
                    case 0:
                        return new Car(position, velocity);
                    case 1:
                        return new Truck(position, velocity);
                    case 2:
                        return new ShortLog(position, velocity);
                    case 3:
                        return new LongLog(position, velocity);
                    default:
                        return null;
                }
        }
        return null;
    }

    public MovingEntity buildShortLogWithTurtles(int chance) {
        MovingEntity movingEntity = buildBasicObject(SLOG, 80);
        if (movingEntity != null && random.nextInt(100) < chance)
            return new Turtles(position, velocity, random.nextInt(2));
        return movingEntity;
    }

    public MovingEntity buildLongLogWithCrocodile(int chance) {
        MovingEntity movingEntity = buildBasicObject(LLOG, 80);
        if (movingEntity != null && random.nextInt(100) < chance)
            return new Crocodile(position, velocity);
        return movingEntity;
    }

    public MovingEntity buildVehicle() {

        MovingEntity movingEntity = random.nextInt(100) < 80 ? buildBasicObject(CAR, 50) : buildBasicObject(TRUCK, 50);
        if (movingEntity != null) {

            if (Math.abs(velocity.getX() * copCarDelay) > World.WORLD_WIDTH) {
                copCarDelay = 0;
                return new CopCar(position, velocity.scale(5));
            }
            copCarDelay = 0;
        }
        return movingEntity;
    }

    public void update(final long deltaMs) {
        updateMs += deltaMs;
        copCarDelay += deltaMs;
    }
}