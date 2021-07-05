package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainCandyClean {
	private static final Logger logger = LogManager.getLogger(MainCandyClean.class);

	public static void main(String[] args) {
		try {
			Game game = new Game(new Board(false, 12, 3), new Score());
			UI ui = new UI(game);
			ui.init();
		} catch (CandyCleanException e) {
			logger.error(e.getMessage());
		}
	}
}

/*
    0 1 2 3
  0 R B B B
  1 R B B B
  2 R B B B
  3 R B B B
 */
