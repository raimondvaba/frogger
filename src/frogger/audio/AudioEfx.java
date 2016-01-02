package frogger.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import frogger.collision.FroggerCollisionDetection;
import frogger.entities.Frogger;
import frogger.graphics.Graphics;
import jig.engine.ResourceFactory;
import jig.engine.audio.AudioState;
import jig.engine.audio.jsound.AudioClip;
import jig.engine.audio.jsound.AudioStream;

public class AudioEfx {

    private static final ResourceFactory FACTORY = ResourceFactory.getFactory();
    private static final double LEVEL_COMPLETE_SOUND_DURATION_SEC = 2.0;
    private static final double SOUND_DURATION_SEC = 0.2;

    FroggerCollisionDetection froggerCollisionDetector;
    Frogger frog;

    public Random random = new Random(System.currentTimeMillis());

    private AudioStream gameMusic;

    private static final String A_FX_PATH = Graphics.RSC_PATH + "ambient_fx/";

    public static AudioClip frogJump = FACTORY.getAudioClip(Graphics.RSC_PATH + "jump.wav");

    public static AudioClip frogDie = FACTORY.getAudioClip(Graphics.RSC_PATH + "frog_die.ogg");

    public static AudioClip frogGoal = FACTORY.getAudioClip(Graphics.RSC_PATH + "goal.ogg");

    public static AudioClip levelGoal = FACTORY.getAudioClip(Graphics.RSC_PATH + "level_goal.ogg");

    public static AudioClip wind = FACTORY.getAudioClip(Graphics.RSC_PATH + "wind.ogg");

    public static AudioClip heat = FACTORY.getAudioClip(Graphics.RSC_PATH + "match.ogg");

    public static AudioClip bonus = FACTORY.getAudioClip(Graphics.RSC_PATH + "bonus.ogg");

    public static AudioClip siren = FACTORY.getAudioClip(A_FX_PATH + "siren.ogg");

    private List<AudioClip> roadEffects = new ArrayList<AudioClip>();
    private List<AudioClip> waterEffects = new ArrayList<AudioClip>();

    private int effectsDelay = 3000;
    private int deltaT = 0;

    public AudioEfx(FroggerCollisionDetection collisionDetection, Frogger frogger) {
    	froggerCollisionDetector = collisionDetection;
        frog = frogger;

        ResourceFactory resourceFactory = FACTORY;

        roadEffects.addAll(Arrays.asList(resourceFactory.getAudioClip(A_FX_PATH + "long-horn.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "car-pass.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "siren.ogg")));

        waterEffects.addAll(Arrays.asList(resourceFactory.getAudioClip(A_FX_PATH + "water-splash.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "splash.ogg"),
                resourceFactory.getAudioClip(A_FX_PATH + "frog.ogg")));

        gameMusic = new AudioStream(Graphics.RSC_PATH + "bg_music.ogg");
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
            if (froggerCollisionDetector.isOnRoad()) {
                effects = roadEffects;
            } else if (froggerCollisionDetector.isInRiver()) {
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

        if (frog.isAlive() && gameMusic.getState() == AudioState.PAUSED)
            gameMusic.resume();
        else if (!frog.isAlive() && gameMusic.getState() == AudioState.PLAYING)
            gameMusic.pause();

    }

}
