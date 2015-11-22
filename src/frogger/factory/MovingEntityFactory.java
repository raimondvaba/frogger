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

package frogger.factory;

import jig.engine.util.Vector2D;

import java.util.Random;

import frogger.Main;
import frogger.entities.Car;
import frogger.entities.CopCar;
import frogger.entities.Crocodile;
import frogger.entities.LongLog;
import frogger.entities.MovingEntity;
import frogger.entities.ShortLog;
import frogger.entities.Truck;
import frogger.entities.Turtles;

public class MovingEntityFactory {

    public static final int CAR = 0;
    public static final int TRUCK = 1;
    public static final int SLOG = 2;
    public static final int LLOG = 3;
    public static final int STEP_SIZE = 32;

    public Vector2D position;
    public Vector2D velocity;

    public Random random;

    private long updateMs = 0;
    private long copCarDelay = 0;

    private long rateMs = 1000;

    private int padding = 64; 

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
                    case CAR:
                        return new Car(position, velocity, random.nextInt(Car.TYPES));
                    case TRUCK:
                        return new Truck(position, velocity);
                    case SLOG:
                        return new ShortLog(position, velocity);
                    case LLOG:
                        return new LongLog(position, velocity);
                    default:
                        return null;
                }
        }
        return null;
    }

    public MovingEntity buildShortLogWithTurtles(int chance) {
        MovingEntity m = buildBasicObject(SLOG, 80);
        if (m != null && random.nextInt(100) < chance)
            return new Turtles(position, velocity, random.nextInt(2));
        return m;
    }

    public MovingEntity buildLongLogWithCrocodile(int chance) {
        MovingEntity m = buildBasicObject(LLOG, 80);
        if (m != null && random.nextInt(100) < chance)
            return new Crocodile(position, velocity);
        return m;
    }

    public MovingEntity buildVehicle() {

        MovingEntity m = random.nextInt(100) < 80 ? buildBasicObject(CAR, 50) : buildBasicObject(TRUCK, 50);

        if (m != null) {

            if (Math.abs(velocity.getX() * copCarDelay) > Main.WORLD_WIDTH) {
                copCarDelay = 0;
                return new CopCar(position, velocity.scale(5));
            }
            copCarDelay = 0;
        }
        return m;
    }

    public void update(final long deltaMs) {
        updateMs += deltaMs;
        copCarDelay += deltaMs;
    }
}