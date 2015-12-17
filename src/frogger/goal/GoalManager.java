package frogger.goal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import frogger.Level;
import frogger.entities.Goal;
import frogger.entities.MovingEntity;
import jig.engine.util.Vector2D;

public class GoalManager {

    final static int MAX_NUMBER_OF_GOALS = 6;

    private List<Goal> goals;
    private Random random;

    protected boolean showingBonus = false;

    final static int BONUS_RATE_MS = 5000;
    final static int BONUS_SHOW_MS = 5000;
    private int deltaRateMs = 0;
    private int deltaShowMs = 0;

    public GoalManager() {
        goals = new LinkedList<Goal>();
        random = new Random(System.currentTimeMillis());
        initializeGoalsPerLevel(Level.STARTING_LEVEL);
    }

    public int initializeGoalsPerLevel(final int level) {
        goals.clear();

        int stepSize = 2;
        int stepStart, stepEnd, maxGoals;
        if (level == Level.STARTING_LEVEL) {
            stepStart = 5;
            maxGoals = 2;
        } else if (level == Level.STARTING_LEVEL + 1) {
            stepStart = 3;
            maxGoals = 4;
        } else {
            stepStart = 1;
            maxGoals = 6;
        }
        
        stepEnd = stepStart + maxGoals * stepSize; 

        for (int step = stepStart; step < stepEnd; step += stepSize) {
            Goal goal = new Goal(new Vector2D(step * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE));
            goals.add(goal);
        }

        return goals.size();
    }

    public List<Goal> getGoals() {
        return goals;
    }

    private List<Goal> getUnreached() {
        List<Goal> unreachedGoals = new LinkedList<Goal>();
        for (Goal goal : goals) {
            if (!goal.isReached()) {
                unreachedGoals.add(goal);
            }
        }
        return unreachedGoals;
    }
    
    public boolean areAllGoalsReached() {
        return getUnreached().size() == 0 ? true : false;
    }

    private void doBonusCheck() {
        if (!showingBonus && deltaRateMs > BONUS_RATE_MS) {
            deltaShowMs = 0;
            showingBonus = true;
            List<Goal> unreachedGoals = getUnreached();
            unreachedGoals.get(random.nextInt(unreachedGoals.size())).setBonus(true);
        }

        if (showingBonus && deltaShowMs > BONUS_SHOW_MS) {
            deltaRateMs = 0;
            showingBonus = false;
            for (Goal g : goals) {
                if (!g.isReached()) {
                    g.setBonus(false);
                }
            }
        }
    }

    public void update(long deltaMs) {
        deltaRateMs += deltaMs;
        deltaShowMs += deltaMs;
        doBonusCheck();
    }
}
