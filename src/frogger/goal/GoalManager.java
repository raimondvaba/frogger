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
        initializeGoalsPerLevel(1);
    }

    public int initializeGoalsPerLevel(final int level) {

        goals.clear();

        switch (level) {
            case 1:
                for (int i = 5; i < 9; i += 2) {
                    goals.add(new Goal(new Vector2D(i * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE)));
                }
                break;
            default:
                for (int i = 3; i < 11; i += 2) {
                    goals.add(new Goal(new Vector2D(i * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE)));
                }
                break;
        }
        
        return goals.size();
    }

    public List<Goal> get() {
        return goals;
    }

    public List<Goal> getUnreached() {
        List<Goal> unreachedGoals = new LinkedList<Goal>();
        for (Goal g : goals)
            if (!g.isReached)
            	unreachedGoals.add(g);

        return unreachedGoals;
    }

    public void doBonusCheck() {
        if (!showingBonus && dRMs > bonusRateMs) {
            dSMs = 0;
            showingBonus = true;
            List<Goal> unreachedGoals = getUnreached();
            unreachedGoals.get(random.nextInt(unreachedGoals.size())).setBonus(true);
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
