package functional.movement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	GameLoadTest.class, 
	GameStartTest.class,
	MoveLeftTest.class, 
	MoveRightTest.class,
	MoveUpTest.class,
	MoveDownTest.class
	})
public class AllTests {

}
