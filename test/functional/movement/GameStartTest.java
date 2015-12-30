package functional.movement;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Test;

import functional.util.SikuliExecuter;

public class GameStartTest {

	@Test
	public void testGameStart() {
		Robot robot;
		try {
			robot = new Robot();
			SikuliExecuter sikuliExecuter = new SikuliExecuter();
			sikuliExecuter.clickImage("testingPictures\\levelAndScore.png");
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.delay(1000);
	        assertTrue(sikuliExecuter.ensureExists("testingPictures\\froggerUp.png"));	
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
