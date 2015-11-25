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

import frogger.audio.AudioEfx;
import frogger.entities.Frogger;
import frogger.entities.MovingEntity;
import frogger.entities.Particle;
import frogger.graphics.Graphics;
import jig.engine.util.Vector2D;


public class HeatWave {

    final static int PERIOD_MS = 2000;
    final static int DURATION_MS = 1000;

    private Random random;

    private long timeMs;
    private long durationMs;
    
    private int levelMultiplier = 10;

    public boolean isHot;

    public HeatWave() {
        isHot = false;
        timeMs = 0;
        random = new Random(System.currentTimeMillis());
    }


    public void perform(Frogger frogger, final long deltaMs, final int level) {
        if (!frogger.isAlive) {
            isHot = false;
            return;
        }

        if (isHot && durationMs > (DURATION_MS - (level * levelMultiplier)) && !frogger.hw_hasMoved) {
            frogger.randomJump(random.nextInt(4));
            isHot = false;
        }

        if (frogger.hw_hasMoved)
            isHot = false;
    }

    public void start(Frogger f, final int gameLevel) {

        if (!isHot && timeMs > PERIOD_MS) {
            if (random.nextInt(100) < gameLevel * levelMultiplier) {
                durationMs = 1;
                isHot = true;
                f.hw_hasMoved = false;
                AudioEfx.heat.play(0.2);
            }
            timeMs = 0;
        }
    }

    public MovingEntity genParticles(Vector2D pos) {
        if (!isHot || random.nextInt(100) > 10)
            return null;

        Vector2D velocity = new Vector2D((random.nextDouble() - 0.5) * 0.1, (random.nextDouble() - 0.5) * 0.1);

        return new Particle(Graphics.SPRITE_SHEET + "#smoke_cloud", pos, velocity, DURATION_MS);
    }

    public void update(final long deltaMs) {
        timeMs += deltaMs;
        durationMs += deltaMs;
    }
}
