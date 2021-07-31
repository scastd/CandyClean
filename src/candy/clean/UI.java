package candy.clean;

import candy.clean.sql.DBAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UI {
	private static final Logger logger = LogManager.getLogger(UI.class);
	private Game game;
	private DBAccess dbAccess;

	public UI(Game game) {
		this.game = game;
	}

	public UI() {
		this(null);
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public DBAccess getDbAccess() {
		return this.dbAccess;
	}

	public void setDbAccess(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	public void init() {
		logger.trace("Initializing the game");
		this.dbAccess.getTop3Users();
		this.registerUser();
		this.showDifficulties();
		logger.debug(this.game.getBoard().debugBoard());

		while (!game.objectiveCompleted()) {
			logger.trace("\n");
			logger.trace(this.game);

			try {
				this.shoot();
			} catch (CandyCleanException e) {
				logger.warn(e.getMessage());
			}
		}

		logger.trace(this.game);
	}

	private void shoot() throws CandyCleanException {
		try {
			logger.trace("Enter the row to shoot");
			int row = Integer.parseInt(Keyboard.read().trim());

			logger.trace("Enter the column to shoot");
			int column = Integer.parseInt(Keyboard.read().trim());

			this.game.shoot(row, column);
		} catch (NumberFormatException e) {
			throw new CandyCleanException(String.format("Please introduce a number between %d and %d",
					Constants.MIN_POS, Constants.getCurrentSize() - 1));
		}
	}

	private void showDifficulties() {
		logger.trace("Select a difficulty:");
		logger.trace(" 1 - Very Easy");
		logger.trace(" 2 - Easy");
		logger.trace(" 3 - Medium");
		logger.trace(" 4 - Hard");
		logger.trace(" 5 - Very Hard");
		logger.trace(" 6 - Extreme");

		try {
			int difficulty;

			do {
				difficulty = Integer.parseInt(Keyboard.read());
			} while (!this.generateGameFromDifficulty(difficulty));

		} catch (CandyCleanException e) {
			logger.warn(e.getMessage());
		}
	}

	private boolean generateGameFromDifficulty(int difficulty) throws CandyCleanException {
		int objectiveScore;
		Board board;

		switch (difficulty) {
			case 1:
				board = new Board(4, 2);
				objectiveScore = 300;
				break;
			case 2:
				board = new Board(6, 2);
				objectiveScore = 600;
				break;
			case 3:
				board = new Board(8, 3);
				objectiveScore = 1200;
				break;
			case 4:
				board = new Board(12, 4);
				objectiveScore = 2700;
				break;
			case 5:
				board = new Board(17, 5);
				objectiveScore = 4000;
				break;
			case 6:
				board = new Board(Constants.MAX_SIZE, 7);
				objectiveScore = 10000;
				break;

			default:
				return false;
		}

		this.game = new Game(board, new Score(objectiveScore));

		return true;
	}

	private void registerUser() {
		logger.trace("Enter the username");
		String name = "";

		try {
			name = Keyboard.read().trim();
		} catch (CandyCleanException e) {
			logger.warn(e.getMessage());
		}

		this.dbAccess.registerUser(name);
	}
}
