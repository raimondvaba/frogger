package functional.movement;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Test;

import functional.util.SikuliExecuter;

public class MoveRightTest {

	@Test
	public void testMoveRight() {
		Robot robot;
		try {
			robot = new Robot();
			SikuliExecuter sikuliExecuter = new SikuliExecuter();
			robot.keyPress(KeyEvent.VK_RIGHT);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_RIGHT);
			robot.delay(1000);
	        assertTrue(sikuliExecuter.ensureExists("testingPictures\\froggerRight.png"));	
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
