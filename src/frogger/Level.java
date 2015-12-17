package frogger;

public class Level {
    
    static final int STARTING_LEVEL = 1;
    static final int CHEATING_LEVEL = 10;

    private int level;

    public Level() {
        level = STARTING_LEVEL;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public void setCheatingLevel() {
        level = CHEATING_LEVEL;
    }

    public int getLevel() {
        return level;
    }
    
    public void resetLevel() {
        level = STARTING_LEVEL;
    }
    
    public void incrementLevel() {
        level++;
    }

}
