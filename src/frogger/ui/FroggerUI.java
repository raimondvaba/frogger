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
    
    private static final int MAX_LIVES_TO_DISPLAY = 10;
    private static final int FONT_SIZE = 14;
    private static final String FONT_NAME = "Sans Serif";
    
    List<ImageResource> heart = Graphics.getImageResources("heart");
    List<ImageResource> gameOver = Graphics.getImageResources("gameover");
    List<ImageResource> levelFinish = Graphics.getImageResources("level_finish");
    List<ImageResource> introTitle = Graphics.getImageResources("splash");
    List<ImageResource> instructions = Graphics.getImageResources("help");

    FontResource font = ResourceFactory.getFactory().getFontResource(new Font(FONT_NAME, Font.BOLD, FONT_SIZE), Color.white,
            null);

    FontResource fontBlack = ResourceFactory.getFactory().getFontResource(new Font(FONT_NAME, Font.BOLD, FONT_SIZE),
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

    public void renderTime(RenderingContext renderingContext) {
        font.render("Time: " + game.getLevelTimer(), renderingContext, AffineTransform.getTranslateInstance(180, 7));
    }

    public void renderScore(RenderingContext renderingContext) {
        font.render("Score: " + game.getScore(), renderingContext, AffineTransform.getTranslateInstance(310, 7));
    }

    public void renderLevel(RenderingContext renderingContext) {
        font.render("L" + game.getCurrentLevel().getLevel(), renderingContext,
                AffineTransform.getTranslateInstance(270, 7));
    }

    public void renderLives(RenderingContext renderingContext) {

        if (game.getFrogger().isAlive()) {
            int dx = 0;
            int livesToDisplay = adjustDisplayableLives();

            for (int i = 0; i < livesToDisplay; i++) {
                heart.get(0).render(renderingContext, AffineTransform.getTranslateInstance(dx + 8, 8));
                dx = 16 * (i + 1);
            }
        }

    }

    public void renderGameStateTitle(RenderingContext renderingContext) {
        switch (game.getGameState()) {
            case Game.GAME_INTRO:
                introTitle.get(0).render(renderingContext, AffineTransform
                        .getTranslateInstance((World.WORLD_WIDTH - introTitle.get(0).getWidth()) / 2, 150));
                break;
            case Game.GAME_INSTRUCTIONS:
                instructions.get(0).render(renderingContext, AffineTransform
                        .getTranslateInstance((World.WORLD_WIDTH - instructions.get(0).getWidth()) / 2, 100));
                break;
            case Game.GAME_OVER:
                gameOver.get(0).render(renderingContext, AffineTransform
                        .getTranslateInstance((World.WORLD_WIDTH - gameOver.get(0).getWidth()) / 2, 150));
                break;
            case Game.GAME_FINISH_LEVEL:
                levelFinish.get(0).render(renderingContext, AffineTransform
                        .getTranslateInstance((World.WORLD_WIDTH - levelFinish.get(0).getWidth()) / 2, 150));
                break;

        }
    }

    public int adjustDisplayableLives() {
        int livesToDisplay = game.getFrogger().getLives();
        return livesToDisplay > MAX_LIVES_TO_DISPLAY ? MAX_LIVES_TO_DISPLAY : livesToDisplay;
    }

    public void update(long deltaMs) {
    }

    public boolean isActive() {
        return true;
    }

    public void setActivation(boolean a) {
    }

}