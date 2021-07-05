package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UI {
	private static final Logger logger = LogManager.getLogger(UI.class);
	private Game game;

	public UI(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void init() {
		logger.trace("Initializing the game");

		while (game.getScore().getTotalScore() < 1000) {
			logger.trace("\n");
			logger.trace(this.game);

			try {
			    this.shoot();
			} catch (CandyCleanException e) {
			    logger.warn(e.getMessage());
			}
		}
	}

	private void shoot() throws CandyCleanException {
		try {
			logger.trace("Enter the row to shoot");
			int row = Integer.parseInt(Keyboard.read().trim());

			logger.trace("Enter the column to shoot");
			int column = Integer.parseInt(Keyboard.read().trim());

			this.game.getBoard().shoot(row, column);
		} catch (NumberFormatException e) {
		    throw new CandyCleanException(String.format("Please introduce a number between %d and %d",
				    Constants.MIN_POS, Constants.getCurrentSize() - 1));
		}
	}
}
