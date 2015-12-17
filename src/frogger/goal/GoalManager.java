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

    final static int BONUS_RATE_MS = 5000;
    final static int BONUS_SHOW_MS = 5000;
    private int deltaRateMs = 0;
    private int deltaShowMs = 0;

    public GoalManager() {
        goals = new LinkedList<Goal>();
        random = new Random(System.currentTimeMillis());
        initializeGoalsPerLevel(1);
    }

    public int initializeGoalsPerLevel(final int level) {
        goals.clear();

        int stepStart, stepEnd;
        if (level == 1) {
            stepStart = 5;
            stepEnd = 9;
        } else {
            stepStart = 3;
            stepEnd = 11;
        }

        int stepSize = 2;
        for (int step = stepStart; step < stepEnd; step += stepSize) {
            goals.add(new Goal(new Vector2D(step * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE)));
        }

        return goals.size();
    }

    public List<Goal> get() {
        return goals;
    }

    private List<Goal> getUnreached() {
        List<Goal> unreachedGoals = new LinkedList<Goal>();
        for (Goal g : goals)
            if (!g.isReached)
                unreachedGoals.add(g);

        return unreachedGoals;
    }
    
    public boolean areAllGoalsReached() {
        return getUnreached().size() == 0 ? true : false;
    }

    public void doBonusCheck() {
        if (!showingBonus && deltaRateMs > BONUS_RATE_MS) {
            deltaShowMs = 0;
            showingBonus = true;
            List<Goal> unreachedGoals = getUnreached();
            unreachedGoals.get(random.nextInt(unreachedGoals.size())).setBonus(true);
        }

        if (showingBonus && deltaShowMs > BONUS_SHOW_MS) {
            deltaRateMs = 0;
            showingBonus = false;
            for (Goal g : goals)
                if (!g.isReached)
                    g.setBonus(false);
        }
    }

    public void update(long deltaMs) {
        deltaRateMs += deltaMs;
        deltaShowMs += deltaMs;
        doBonusCheck();
    }
}
