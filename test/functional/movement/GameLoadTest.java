package functional.movement;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import functional.util.SikuliExecuter;


public class GameLoadTest {
	
	@Test
	public void testGameLoad() {
	        SikuliExecuter sikuliExecuter = new SikuliExecuter();
	        assertTrue(sikuliExecuter.ensureExists("testingPictures\\startScreen.png"));			
	}

}
