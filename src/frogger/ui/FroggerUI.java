package frogger.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.List;

import frogger.Game;
import frogger.World;
import frogger.graphics.Graphics;
import jig.engine.FontResource;
import jig.engine.ImageResource;
import jig.engine.RenderingContext;
import jig.engine.ResourceFactory;
import jig.engine.ViewableLayer;

public class FroggerUI implements ViewableLayer {
    private final List<ImageResource> frames = ResourceFactory.getFactory().getFrames(Graphics.SPRITE_SHEET + "#heart");
    List<ImageResource> heart = frames;
    List<ImageResource> gameOver = ResourceFactory.getFactory().getFrames(Graphics.SPRITE_SHEET + "#gameover");
    List<ImageResource> levelFinish = ResourceFactory.getFactory().getFrames(Graphics.SPRITE_SHEET + "#level_finish");
    List<ImageResource> introTitle = ResourceFactory.getFactory().getFrames(Graphics.SPRITE_SHEET + "#splash");
    List<ImageResource> instructions = ResourceFactory.getFactory().getFrames(Graphics.SPRITE_SHEET + "#help");

    FontResource font = ResourceFactory.getFactory().getFontResource(new Font("Sans Serif", Font.BOLD, 14), Color.white,
            null);

    FontResource fontBlack = ResourceFactory.getFactory().getFontResource(new Font("Sans Serif", Font.BOLD, 14),
            Color.black, null);

    Game game;

    public FroggerUI(final Game game) {
        this.game = game;
    }

    public void render(RenderingContext renderingContext) {
    	renderTime(renderingContext);
    	renderScore(renderingContext);
    	renderLives(renderingContext);
    	renderLevel(renderingContext);
    	renderGameStateTitle(renderingContext);
    }

    public boolean renderTime(RenderingContext renderingContext){
    	font.render("Time: " + game.getLevelTimer(), renderingContext, AffineTransform.getTranslateInstance(180, 7));
    	return true;
    }
    
    public boolean renderScore(RenderingContext renderingContext){
    	font.render("Score: " + game.getScore(), renderingContext, AffineTransform.getTranslateInstance(310, 7));
    	return true;
    }
    
    public boolean renderLevel(RenderingContext renderingContext){
    	font.render("L" + game.getCurrentLevel().getLevel(), renderingContext, AffineTransform.getTranslateInstance(270, 7));
    	return true;
    }
    
    public boolean renderLives(RenderingContext renderingContext){
    	
        if (isFroggerAlive()) {
            int dx = 0;
            int livesToDisplay = adjustDisplayableLives();
            
            for (int i = 0; i < livesToDisplay; i++) {
                heart.get(0).render(renderingContext, AffineTransform.getTranslateInstance(dx + 8, 8));
                dx = 16 * (i + 1);
            }
        }
        
    	return true;
    }
    
    public boolean renderGameStateTitle(RenderingContext renderingContext){
        switch (game.getGameState()) {
	        case Game.GAME_INTRO:
	            introTitle.get(0).render(renderingContext, AffineTransform.getTranslateInstance(
	                    (World.WORLD_WIDTH - introTitle.get(0).getWidth()) / 2, 150));
	            break;
	        case Game.GAME_INSTRUCTIONS:
	            instructions.get(0).render(renderingContext, AffineTransform.getTranslateInstance(
	                    (World.WORLD_WIDTH - instructions.get(0).getWidth()) / 2, 100));
	            break;
	        case Game.GAME_OVER:
	            gameOver.get(0).render(renderingContext, AffineTransform
	                    .getTranslateInstance((World.WORLD_WIDTH - gameOver.get(0).getWidth()) / 2, 150));
	            break;
	        case Game.GAME_FINISH_LEVEL:
	            levelFinish.get(0).render(renderingContext, AffineTransform.getTranslateInstance(
	                    (World.WORLD_WIDTH - levelFinish.get(0).getWidth()) / 2, 150));
	            break;

        }
    	return true;
    }
    
    public boolean isFroggerAlive(){
    	if (game.getFrogger().getLives() > 0)return true;
    	else return false;
    }
    
    public int adjustDisplayableLives(){
    	int livesToDisplay = game.getFrogger().getLives();
        
        if (livesToDisplay > 10) {
        	livesToDisplay = 10;
        } else {
        	livesToDisplay = game.getFrogger().getLives();
        }
        
        return livesToDisplay;
    }
    
    
    public void update(long deltaMs) {
    }

    public boolean isActive() {
        return true;
    }

    public void setActivation(boolean a) {
     
    }

}