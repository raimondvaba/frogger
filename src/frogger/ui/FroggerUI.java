/**
 * Copyright (c) 2009 Vitaliy Pavlenko
 * <p>
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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

    public void render(RenderingContext rc) {

        font.render("Time: " + game.getLevelTimer(), rc, AffineTransform.getTranslateInstance(180, 7));

        font.render("Score: " + game.getScore(), rc, AffineTransform.getTranslateInstance(310, 7));

        if (game.getFrogger().getLives() > 0) {
            int dx = 0;

            int maxHearts = game.getFrogger().getLives();
            if (maxHearts > 10) {
                maxHearts = 10;
            } else {
                maxHearts = game.getFrogger().getLives();
            }

            for (int i = 0; i < maxHearts; i++) {
                heart.get(0).render(rc, AffineTransform.getTranslateInstance(dx + 8, 8));
                dx = 16 * (i + 1);
            }
        }

        font.render("L" + game.getCurrentLevel().getLevel(), rc, AffineTransform.getTranslateInstance(270, 7));

        switch (game.getGameState()) {
            case Game.GAME_INTRO:
                introTitle.get(0).render(rc, AffineTransform.getTranslateInstance(
                        (World.WORLD_WIDTH - introTitle.get(0).getWidth()) / 2, 150));
                break;
            case Game.GAME_INSTRUCTIONS:
                instructions.get(0).render(rc, AffineTransform.getTranslateInstance(
                        (World.WORLD_WIDTH - instructions.get(0).getWidth()) / 2, 100));
                break;
            case Game.GAME_OVER:
                gameOver.get(0).render(rc, AffineTransform
                        .getTranslateInstance((World.WORLD_WIDTH - gameOver.get(0).getWidth()) / 2, 150));
                break;
            case Game.GAME_FINISH_LEVEL:
                levelFinish.get(0).render(rc, AffineTransform.getTranslateInstance(
                        (World.WORLD_WIDTH - levelFinish.get(0).getWidth()) / 2, 150));
                break;
            default:

        }

    }

    public void update(long deltaMs) {
    }

    public boolean isActive() {
        return true;
    }

    public void setActivation(boolean a) {
     
    }

}