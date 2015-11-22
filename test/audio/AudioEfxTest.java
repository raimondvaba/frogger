package audio;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frogger.Main;
import frogger.audio.AudioEfx;
import frogger.collision.FroggerCollisionDetection;
import frogger.entities.Frogger;

public class AudioEfxTest {

	static Frogger frogger = new Frogger(new Main());
	static FroggerCollisionDetection froggerCollisionDetector = new FroggerCollisionDetection(frogger);
	private static AudioEfx audioEffects = new AudioEfx(froggerCollisionDetector, frogger);

	@Test
	public void testPlayGameMusic() {
		assertEquals(true,audioEffects.playGameMusic());
	}

	@Test
	public void testPlayCompleteLevel() {
		assertEquals(true,audioEffects.playCompleteLevel());
	}

	@Test
	public void testPlayRandomAmbientSound() {
		assertEquals(true,audioEffects.playRandomAmbientSound(1000));
	}

	@Test
	public void testUpdate() {
		assertEquals(true,audioEffects.update(1000));
	}

}
