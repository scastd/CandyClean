package candy.clean;

public class Game {
	private final Board board;
	private final Score score;

	public Game(Board board, Score score) {
		this.board = board;
		this.score = score;
	}

	public Board getBoard() {
		return this.board;
	}

	public Score getScore() {
		return this.score;
	}

	public void shoot(int row, int column) throws CandyCleanException {
		this.board.shoot(row, column);
	}

	@Override
	public String toString() {
		return this.score.toString() + "\n\n" + this.board.toString();
	}
}
