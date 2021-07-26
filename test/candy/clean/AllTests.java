package candy.clean;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BoardTest.class, CandyTest.class, ColorTest.class, GameTest.class, ScoreTest.class})

public class AllTests {
}
