package candy.clean;

public class Game {
	private Board board;
	private Score score;

	public Game(Board board, Score score) {
		this.board = board;
		this.score = score;
	}

	public Board getBoard() {
		return this.board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Score getScore() {
		return this.score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return this.score.toString() + "\n\n" + this.board.toString();
	}
}
