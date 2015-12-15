package frogger.keyboard;

import java.awt.event.KeyEvent;

import frogger.Game;
import frogger.entities.Frogger;
import jig.engine.Keyboard;

public class KeyboardManager {
    
    private Keyboard keyboard;
    private Frogger frogger;
    private Game game;

    public KeyboardManager(Keyboard keyboard, Game game, Frogger frogger) {
        this.keyboard = keyboard;
        this.game = game;
        this.frogger = frogger;
    }
    
    private boolean hasSpaceBeenReleased = false;
    private boolean keyPressed = false;
    private boolean listenInput = true;

    public void froggerKeyboardHandler() {
        keyboard.poll();

        boolean keyReleased = false;
        boolean downPressed = keyboard.isPressed(KeyEvent.VK_DOWN);
        boolean upPressed = keyboard.isPressed(KeyEvent.VK_UP);
        boolean leftPressed = keyboard.isPressed(KeyEvent.VK_LEFT);
        boolean rightPressed = keyboard.isPressed(KeyEvent.VK_RIGHT);

        if (keyboard.isPressed(KeyEvent.VK_C))
            frogger.cheating = true;
        if (keyboard.isPressed(KeyEvent.VK_V))
            frogger.cheating = false;
        if (keyboard.isPressed(KeyEvent.VK_0)) {
            game.getCurrentLevel().setCheatingLevel();
            game.getWorld().initialize(game.getCurrentLevel().getLevel());
        }

        if (downPressed || upPressed || leftPressed || rightPressed)
            keyPressed = true;
        else if (keyPressed)
            keyReleased = true;

        if (listenInput) {
            if (downPressed)
                frogger.moveDown();
            if (upPressed)
                frogger.moveUp();
            if (leftPressed)
                frogger.moveLeft();
            if (rightPressed)
                frogger.moveRight();

            if (keyPressed)
                listenInput = false;
        }

        if (keyReleased) {
            listenInput = true;
            keyPressed = false;
        }

        if (keyboard.isPressed(KeyEvent.VK_ESCAPE))
            game.setGameState(Game.GAME_INTRO);
    }

    public void menuKeyboardHandler() {
        keyboard.poll();

        if (!keyboard.isPressed(KeyEvent.VK_SPACE)) {
            hasSpaceBeenReleased = true;
            return;
        }

        if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
            switch (game.getGameState()) {
                case Game.GAME_INSTRUCTIONS:
                case Game.GAME_OVER:
                    game.setGameState(Game.GAME_INTRO);
                    hasSpaceBeenReleased = false;
                    break;
                default:
                    frogger.resetLives();
                    game.reset();
                    frogger.resetFrog();
                    game.setGameState(Game.GAME_PLAY);
                    game.getAudioFx().playGameMusic();
                    game.getWorld().initialize(game.getCurrentLevel().getLevel());
            }
        }
        if (keyboard.isPressed(KeyEvent.VK_H))
            game.setGameState(Game.GAME_INSTRUCTIONS);
    }

    public void finishLevelKeyboardHandler() {
        keyboard.poll();
        if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
            game.setGameState(Game.GAME_PLAY);
            game.getAudioFx().playGameMusic();
            game.getCurrentLevel().incrementLevel();
            game.getWorld().initialize(game.getCurrentLevel().getLevel());
        }
    }
}
