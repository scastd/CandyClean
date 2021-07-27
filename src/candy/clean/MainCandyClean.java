package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainCandyClean {
	private static final Logger logger = LogManager.getLogger(MainCandyClean.class);

	public static void main(String[] args) {
		new UI().init();
	}
}

/*
    0 1 2 3
  0 R B B B
  1 R B B B
  2 R B B B
  3 R B B B
 */
