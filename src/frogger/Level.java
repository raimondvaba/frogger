package frogger;

public class Level {

    private int level;

    private static final int STARTING_LEVEL = 1;
    private static final int CHEATING_LEVEL = 10;

    // public Level(World world, final int level) {
    // this.level = level;
    // this.world = world;
    //// initialize();
    // }

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
