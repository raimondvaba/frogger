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

package frogger.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import frogger.Main;
import frogger.collision.FroggerCollisionDetection;
import frogger.entities.Frogger;
import jig.engine.ResourceFactory;
import jig.engine.audio.AudioState;
import jig.engine.audio.jsound.AudioClip;
import jig.engine.audio.jsound.AudioStream;

/**
 * Controls the audio effects
 * 
 * @author vitaliy
 *
 */
public class AudioEfx {

    private static final ResourceFactory FACTORY = ResourceFactory.getFactory();
    private static final double LEVEL_COMPLETE_SOUND_DURATION_SEC = 2.0;
    private static final double SOUND_DURATION_SEC = 0.2;

    // These are referenced as to when to play the sound effects
    FroggerCollisionDetection fc;
    Frogger frog;

    public Random random = new Random(System.currentTimeMillis());

    // Background music
    private AudioStream gameMusic;

    private static final String A_FX_PATH = Main.RSC_PATH + "ambient_fx/";

    public static AudioClip frogJump = FACTORY.getAudioClip(Main.RSC_PATH + "jump.wav");

    public static AudioClip frogDie = FACTORY.getAudioClip(Main.RSC_PATH + "frog_die.ogg");

    public static AudioClip frogGoal = FACTORY.getAudioClip(Main.RSC_PATH + "goal.ogg");

    public static AudioClip levelGoal = FACTORY.getAudioClip(Main.RSC_PATH + "level_goal.ogg");

    public static AudioClip wind = FACTORY.getAudioClip(Main.RSC_PATH + "wind.ogg");

    public static AudioClip heat = FACTORY.getAudioClip(Main.RSC_PATH + "match.ogg");

    public static AudioClip bonus = FACTORY.getAudioClip(Main.RSC_PATH + "bonus.ogg");

    public static AudioClip siren = FACTORY.getAudioClip(A_FX_PATH + "siren.ogg");

    // one effect is randomly picked from road_effects or water_effects every
    // couple of seconds
    private List<AudioClip> roadEffects = new ArrayList<AudioClip>();
    private List<AudioClip> waterEffects = new ArrayList<AudioClip>();

    private int effectsDelay = 3000;
    private int deltaT = 0;

    /**
     * In order to know when to play-back certain effects, we track the state of
     * collision detector and Frogger
     * 
     * @param collisionDetection
     * @param frogger
     */
    public AudioEfx(FroggerCollisionDetection collisionDetection, Frogger frogger) {
        fc = collisionDetection;
        frog = frogger;

        ResourceFactory resourceFactory = FACTORY;

        roadEffects.addAll(Arrays.asList(resourceFactory.getAudioClip(A_FX_PATH + "long-horn.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "car-pass.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "siren.ogg")));

        waterEffects.addAll(Arrays.asList(resourceFactory.getAudioClip(A_FX_PATH + "water-splash.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "splash.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "frog.ogg")));

        gameMusic = new AudioStream(Main.RSC_PATH + "bg_music.ogg");
    }

    public void playGameMusic() {
        gameMusic.loop(SOUND_DURATION_SEC, 0);
    }

    public void playCompleteLevel() {
        gameMusic.pause();
        levelGoal.play(LEVEL_COMPLETE_SOUND_DURATION_SEC);
    }

    public void playRandomAmbientSound(final long deltaMs) {
        deltaT += deltaMs;

        if (deltaT > effectsDelay) {
            List<AudioClip> effects = null;
            if (fc.isOnRoad()) {
                effects = roadEffects;
            } else if (fc.isInRiver()) {
                effects = waterEffects;
            }
            if (effects != null) {
                deltaT = 0;
                effects.get(random.nextInt(effects.size())).play(SOUND_DURATION_SEC);
            }
        }

    }

    public void update(final long deltaMs) {
        playRandomAmbientSound(deltaMs);

        if (frog.isAlive && gameMusic.getState() == AudioState.PAUSED)
            gameMusic.resume();
        else if (!frog.isAlive && gameMusic.getState() == AudioState.PLAYING)
            gameMusic.pause();

    }

}
