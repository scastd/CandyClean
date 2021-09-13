package candy.clean;

public class Game {
	private Board board;
	private Score score;

	public Game(Board board, Score score) {
		this.board = board;
		this.score = score;
	}

	public Game() {
		this(null, null);
	}

	public Board getBoard() {
		return this.board;
	}

	public Score getScore() {
		return this.score;
	}

	public void generatePredefinedBoard() {
		this.board = new Board(new String[] {
				"GYYYGRRY",
				"GYYRGYRG",
				"YYYYYRYG",
				"GYYRYGRR",
				"RRGGGRYR",
				"YRRYYGGY",
				"GYGRRGRY",
				"YGYRGRYG"}, 3);
		this.score = new Score();
	}

	public boolean objectiveCompleted() {
		return this.score.objectiveReached();
	}

	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.board.shoot(row, column);
			this.score.incrementScore();
		} catch (CandyCleanException e) {
			this.score.resetScore();

			throw new CandyCleanException(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return this.score.toString() + "\n\n" + this.board.toString();
	}
}
