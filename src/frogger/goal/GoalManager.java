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

package frogger.goal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import frogger.entities.Goal;
import frogger.entities.MovingEntity;
import jig.engine.util.Vector2D;

public class GoalManager {

    final static int MAX_NUM_OF_GOALS = 6;

    private List<Goal> goals;
    private Random random;

    protected boolean showingBonus = false;

    private int bonusRateMs = 5000;
    private int bonusShowMs = 5000;
    private int dRMs = 0;
    private int dSMs = 0;

    public GoalManager() {
        goals = new LinkedList<Goal>();
        random = new Random(System.currentTimeMillis());
        init(1);
    }

    public void init(final int level) {

        goals.clear();

        switch (level) {
            case 1:
                for (int i = 5; i < 9; i += 2) {
                    goals.add(new Goal(new Vector2D(i * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE)));
                }
                break;
            case 2:
            default:
                for (int i = 3; i < 11; i += 2) {
                    goals.add(new Goal(new Vector2D(i * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE)));
                }
                break;
        }
        return;
    }

    public List<Goal> get() {
        return goals;
    }

    public List<Goal> getUnreached() {
        List<Goal> l = new LinkedList<Goal>();
        for (Goal g : goals)
            if (!g.isReached)
                l.add(g);

        return l;
    }

    public void doBonusCheck() {
        if (!showingBonus && dRMs > bonusRateMs) {
            dSMs = 0;
            showingBonus = true;
            List<Goal> l = getUnreached();
            l.get(random.nextInt(l.size())).setBonus(true);
        }

        if (showingBonus && dSMs > bonusShowMs) {
            dRMs = 0;
            showingBonus = false;
            for (Goal g : goals)
                if (!g.isReached)
                    g.setBonus(false);
        }
    }

    public void update(long deltaMs) {
        dRMs += deltaMs;
        dSMs += deltaMs;
        doBonusCheck();
    }
}
