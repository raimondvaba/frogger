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

package frogger.effects;

import java.util.Random;

import frogger.World;
import frogger.audio.AudioEfx;
import frogger.entities.Frogger;
import frogger.entities.MovingEntity;
import frogger.entities.Particle;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;

public class WindGust {

    private final static int PERIOD_MS = 5000;
    private final static int DURATION_MS = 3000;
    
    private int spriteSize = 32;

    private final Random random = new Random(System.currentTimeMillis());;

    private long timeMs;
    private long durationMs;

    private boolean isWindy;
    
    private int levelMultiplier = 10;

    public WindGust() {
        timeMs = 0;
        isWindy = false;
    }

    public void perform(final Frogger frogger, final int level, final long deltaMs) {
        if (!frogger.isAlive()) {
            isWindy = false;
            return;
        }

        if (isWindy && durationMs < DURATION_MS) {
            double vPos = deltaMs * random.nextDouble() * (0.01 * level);
            frogger.windReposition(new Vector2D(vPos, 0));
        } else {
            isWindy = false;
        }
    }

    public void start(final int level) {

        if (!isWindy && timeMs > PERIOD_MS) {

            if (!isSufficientWindInLevel(level)) {
                durationMs = 1;
                isWindy = true;
                AudioEfx.wind.play(0.2);
            }

            timeMs = 0;
        }

    }

    public MovingEntity genParticles(final int level) {

        if (!isWindy || isSufficientWindInLevel(level))
            return null;

        int randomYWindPosition = random.nextInt(World.WORLD_WIDTH) + spriteSize;
        
        Vector2D windParticlePosition = new Vector2D(0, randomYWindPosition);
        Vector2D windVelocity = new Vector2D(0.2 + random.nextDouble(), (random.nextDouble() - 0.5) * 0.1);
        
        return new Particle(Graphics.getSpritePath("white_dot"), windParticlePosition, windVelocity);
    }

    public void update(final long deltaMs) {
        timeMs += deltaMs;
        durationMs += deltaMs;
    }
    
    private boolean isSufficientWindInLevel(final int level){
        if (random.nextInt(100) > level * levelMultiplier)
        	return true;
        else 
        	return false;
    }
}
