package functional.movement;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.*;
import java.awt.event.*;

import org.junit.Test;

import functional.util.SikuliExecuter;

public class MoveLeftTest {

	@Test
	public void testMoveLeft() {
		try {
			Robot robot = new Robot();
			SikuliExecuter sikuliExecuter = new SikuliExecuter();
			robot.keyPress(KeyEvent.VK_LEFT);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_LEFT);
			robot.delay(1000);
	        assertTrue(sikuliExecuter.ensureExists("testingPictures\\froggerLeft.png"));
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
