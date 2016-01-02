package frogger.goal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import frogger.Level;
import frogger.entities.Goal;
import frogger.entities.MovingEntity;
import jig.engine.util.Vector2D;

public class GoalManager {

    final static int STARTING_NUMBER_OF_GOALS = 2;
    final static int SECOND_LEVEL_NUMBER_OF_GOALS = 4;
    final static int MAX_NUMBER_OF_GOALS = 6;

    final static int SPACE_BETWEEN_GOALS = 2;

    final static int FIRST_LEVEL_HORIZONTAL_GOAL_POS_START = 5;
    final static int SECOND_LEVEL_HORIZONTAL_GOAL_POS_START = FIRST_LEVEL_HORIZONTAL_GOAL_POS_START - SPACE_BETWEEN_GOALS;
    final static int MAX_LEVEL_HORIZONTAL_GOAL_POS_START = SECOND_LEVEL_HORIZONTAL_GOAL_POS_START - SPACE_BETWEEN_GOALS;

    private List<Goal> goals = new LinkedList<Goal>();
    private Random random = new Random(System.currentTimeMillis());

    protected boolean showingBonus = false;

    final static int BONUS_RATE_MS = 5000;
    final static int BONUS_SHOW_MS = 5000;
    int deltaRateMs = 0;
    int deltaShowMs = 0;

    public GoalManager() {
        initializeGoalsPerLevel(Level.STARTING_LEVEL);
    }

    public int initializeGoalsPerLevel(final int level) {
        goals.clear();

        int goalHorizontalStartingPosition, goalHorizontalEndPosition, numberOfGoals;

        if (level == Level.STARTING_LEVEL) {
            goalHorizontalStartingPosition = FIRST_LEVEL_HORIZONTAL_GOAL_POS_START;
            numberOfGoals = STARTING_NUMBER_OF_GOALS;
        } else if (level == Level.STARTING_LEVEL + 1) {
            goalHorizontalStartingPosition = SECOND_LEVEL_HORIZONTAL_GOAL_POS_START;
            numberOfGoals = SECOND_LEVEL_NUMBER_OF_GOALS;
        } else {
            goalHorizontalStartingPosition = MAX_LEVEL_HORIZONTAL_GOAL_POS_START;
            numberOfGoals = MAX_NUMBER_OF_GOALS;
        }

        goalHorizontalEndPosition = goalHorizontalStartingPosition + numberOfGoals * SPACE_BETWEEN_GOALS;

        for (int goalPosition = goalHorizontalStartingPosition; goalPosition < goalHorizontalEndPosition; goalPosition += SPACE_BETWEEN_GOALS) {
            Goal goal = new Goal(new Vector2D(goalPosition * MovingEntity.SPRITE_SIZE, MovingEntity.SPRITE_SIZE));
            goals.add(goal);
        }

        return goals.size();
    }

    List<Goal> getUnreached() {
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

    void doBonusCheck() {
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

    public List<Goal> getGoals() {
        return goals;
    }

}
