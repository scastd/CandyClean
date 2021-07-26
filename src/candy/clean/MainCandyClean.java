package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainCandyClean {
	private static final Logger logger = LogManager.getLogger(MainCandyClean.class);

	public static void main(String[] args) {
		try {
			new UI(new Game(new Board(5, 3), new Score(5000))).init();
		} catch (CandyCleanException e) {
			logger.error(e.getMessage());
		}

//		Game game = new Game();
//		game.generatePredefinedBoard();
//		new UI(game).init();
	}
}

/*
    0 1 2 3
  0 R B B B
  1 R B B B
  2 R B B B
  3 R B B B
 */
