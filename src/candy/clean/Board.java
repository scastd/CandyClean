package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Board {
	private static final Logger logger = LogManager.getLogger(Board.class);

	private Candy[][] table;
	private final int size;
	private final int numColors;

	public Board(int size, int numColors) throws CandyCleanException {
		StringBuilder error = new StringBuilder();

		if (size < Constants.MIN_SIZE || size > Constants.MAX_SIZE) {
			error.append(String.format("Size must be between %d and %d (both inclusive)%s",
					Constants.MIN_SIZE, Constants.MAX_SIZE, "\n"));
		}

		if (numColors < Constants.MIN_NUM_COLORS || numColors > Constants.MAX_NUM_COLORS) {
			error.append(String.format("Number of colors must be between %d and %d (both inclusive)%s",
					Constants.MIN_NUM_COLORS, Constants.MAX_NUM_COLORS, "\n"));
		}

		if (error.length() > 0) {
			throw new CandyCleanException(error.toString());
		}

		this.table = this.generateRandomBoard(size, numColors);
		this.size = size;
		this.numColors = numColors;

		Constants.setCurrentSize(this.size);
	}

	public Board(String[] board, int numColors) {
		this.size = board[0].length();
		this.numColors = numColors;
		this.table = new Candy[this.size][this.size];

		this.generateBoardFromString(board);

		Constants.setCurrentSize(this.size);
	}

	private @NotNull Candy[][] generateRandomBoard(int size, int numColors) {
		Candy[][] candies = new Candy[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				candies[i][j] = new Candy(Constants.random.nextInt(numColors));
			}
		}

		return candies;
	}

	private void generateBoardFromString(String[] boardStr) {
		for (int i = 0; i < boardStr.length; i++) {
			for (int j = 0; j < boardStr[i].length(); j++) {
				this.table[i][j] = new Candy(boardStr[i].charAt(j));
			}
		}
	}

	public void shoot(int row, int column) throws CandyCleanException {
		if (this.areInvalidCoordinates(row, column)) {
			throw new CandyCleanException(String.format("Invalid coordinates, they must be between %d and %d",
					Constants.MIN_POS, Constants.getCurrentSize() - 1));
		}

		if (this.table[row][column].isSpecial()) {
			switch (this.table[row][column].getSpecial()) {
				case ROW:
					this.breakRowAndCompact(row, column);
					break;

				case COLUMN:
					this.breakColumnAndCompact(row, column);
					break;

				case ROW_COLUMN:
					this.breakRowAndCompact(row, column);
					this.breakColumnAndCompact(row, column);
					break;

				case BOMB:
					this.breakBombArea(row, column);
					break;

				case FULL_BOARD:
					this.breakFullBoard();
					break;

			}
		} else {
			int firstLeftCandyPos = this.firstLeftCandyPos(row, column);
			int lastRightCandyPos = this.lastRightCandyPos(row, column);
			int firstTopCandyPos = this.firstTopCandyPos(row, column);
			int lastBottomCandyPos = this.lastBottomCandyPos(row, column);

			if (!this.checkSurroundingCandies(firstLeftCandyPos, lastRightCandyPos,
					firstTopCandyPos, lastBottomCandyPos)) {
				throw new CandyCleanException("Insufficient surrounding candies in the specified position (minimum 3)");
			}

			this.makeSpecialCandy(row, column, firstLeftCandyPos, lastRightCandyPos, firstTopCandyPos, lastBottomCandyPos);

			this.removeCandies(row, column, firstLeftCandyPos,
					lastRightCandyPos,
					firstTopCandyPos,
					lastBottomCandyPos);

			this.compactBoard(row, column, firstLeftCandyPos, lastRightCandyPos, lastBottomCandyPos);
		}
	}

	private void breakRowAndCompact(int row, int column) throws CandyCleanException {
		for (int i = 0; i < Constants.getCurrentSize(); i++) {
			if (this.table[row][i].isSpecial() && i != column) {
				this.shoot(row, i);
			}

			this.table[row][i].setSpecial(CandyType.NORMAL_CANDY);
			this.table[row][i].setToBlank();
		}

		this.compactBoard(row, column, 0, Constants.getCurrentSize() - 1, row);
	}

	private void breakColumnAndCompact(int row, int column) throws CandyCleanException {
		for (int i = 0; i < Constants.getCurrentSize(); i++) {
			if (this.table[i][column].isSpecial() && i != row) {
				this.shoot(i, column);
			}

			this.table[i][column].setSpecial(CandyType.NORMAL_CANDY);
			this.table[i][column].setToBlank();
		}

		this.dropOneColumn(column, Constants.getCurrentSize() - 1);
	}

	private void breakFullBoard() {
		this.table = this.generateRandomBoard(Constants.getCurrentSize(), this.numColors);
	}

	private void breakBombArea(int row, int column) throws CandyCleanException {
		this.table[row][column].setSpecial(CandyType.NORMAL_CANDY);
		this.table[row][column].setToBlank();

		boolean[] directions = this.checkValidCandiesAroundBomb(row, column);

		final int left = 0;
		final int top = 1;
		final int right = 2;
		final int bottom = 3;

		if (directions[top]) {
			this.breakConnectedBombs(row - 1, column);

			if (directions[left]) {
				this.breakConnectedBombs(row - 1, column - 1);
				this.breakConnectedBombs(row, column - 1);
			}

			if (directions[right]) {
				this.breakConnectedBombs(row - 1, column + 1);
				this.breakConnectedBombs(row, column + 1);
			}
		}

		if (directions[bottom]) {
			this.breakConnectedBombs(row + 1, column);

			if (directions[left]) {
				this.breakConnectedBombs(row + 1, column - 1);
				this.breakConnectedBombs(row, column - 1);
			}

			if (directions[right]) {
				this.breakConnectedBombs(row + 1, column + 1);
				this.breakConnectedBombs(row, column + 1);
			}
		}
	}

	private void breakConnectedBombs(int row, int column) throws CandyCleanException {
		if (this.table[row][column].getSpecial() == CandyType.BOMB) {
			this.shoot(row, column);
		} else {
			this.table[row][column].setToBlank();
		}
	}

	private boolean[] checkValidCandiesAroundBomb(int row, int column) {
		boolean[] directions = new boolean[4];
		final int left = 0;
		final int top = 1;
		final int right = 2;
		final int bottom = 3;

		if (row - 1 >= Constants.MIN_POS) {
			directions[top] = true;
		}

		if (row + 1 < Constants.getCurrentSize()) {
			directions[bottom] = true;
		}

		if (column - 1 >= Constants.MIN_POS) {
			directions[left] = true;
		}

		if (column + 1 < Constants.getCurrentSize()) {
			directions[right] = true;
		}

		return directions;
	}

	private void removeCandies(int row, int column, int firstLeftCandyPos,
	                           int lastRightCandyPos, int firstTopCandyPos, int lastBottomCandyPos) throws CandyCleanException {
		for (int i = firstLeftCandyPos; i <= lastRightCandyPos; i++) {
			if (this.table[row][i].isSpecial()) {
//				this.shoot(row, i);
			} else {
				this.table[row][i].setToBlank(); // If not special, break it
			}
		}

		for (int i = firstTopCandyPos; i <= lastBottomCandyPos; i++) {
			if (this.table[i][column].isSpecial()) {
//				this.shoot(i, column);
			} else {
				this.table[i][column].setToBlank(); // If not special, break it
			}
		}
	}

	private boolean checkSurroundingCandies(int firstLeftCandyPos, int lastRightCandyPos,
	                                        int firstTopCandyPos, int lastBottomCandyPos) {

		return (lastRightCandyPos - firstLeftCandyPos + 1 >= Constants.MIN_CANDIES_FOR_BREAK) ||
				(lastBottomCandyPos - firstTopCandyPos + 1 >= Constants.MIN_CANDIES_FOR_BREAK);
	}

	private int firstLeftCandyPos(int row, int column) {
		boolean found = false;
		int leftCandyPos = column;
		char letter = this.table[row][column].getLetter();

		while (!found) {
			if (leftCandyPos == -1) {
				break;
			}

			if (this.table[row][leftCandyPos].getLetter() == letter) {
				leftCandyPos--;
			} else {
				found = true;
			}
		}

		return leftCandyPos + 1;
	}

	private int lastRightCandyPos(int row, int column) {
		boolean found = false;
		int rightCandyPos = column;
		char letter = this.table[row][column].getLetter();

		while (!found) {
			if (rightCandyPos == Constants.getCurrentSize()) {
				break;
			}

			if (this.table[row][rightCandyPos].getLetter() == letter) {
				rightCandyPos++;
			} else {
				found = true;
			}
		}

		return rightCandyPos - 1;
	}

	private int firstTopCandyPos(int row, int column) {
		boolean found = false;
		int topCandyPos = row;
		char letter = this.table[row][column].getLetter();

		while (!found) {
			if (topCandyPos == -1) {
				break;
			}

			if (this.table[topCandyPos][column].getLetter() == letter) {
				topCandyPos--;
			} else {
				found = true;
			}
		}

		return topCandyPos + 1;
	}

	private int lastBottomCandyPos(int row, int column) {
		boolean found = false;
		int bottomCandyPos = row;
		char letter = this.table[row][column].getLetter();

		while (!found) {
			if (bottomCandyPos == Constants.getCurrentSize()) {
				break;
			}

			if (this.table[bottomCandyPos][column].getLetter() == letter) {
				bottomCandyPos++;
			} else {
				found = true;
			}
		}

		return bottomCandyPos - 1;
	}

	private void compactBoard(int row, int column, int firstLeftCandyPos, int lastRightCandyPos, int lastBottomCandyPos) {
		for (int i = firstLeftCandyPos; i <= lastRightCandyPos; i++) {
			if (this.table[row][i].isSpecial()) {
				this.dropColumnsInRow(i, row - 1);
			} else {
				this.dropColumnsInRow(i, row);
			}
		}

		this.dropOneColumn(column, lastBottomCandyPos);
	}

	private void dropColumnsInRow(int column, int initialRow) {
		for (int i = initialRow; i >= 0; i--) {
			if (i == 0) {
				this.table[i][column] = new Candy(Constants.random.nextInt(this.numColors));
			} else if (!this.table[i][column].isSpecial()) {
				//? Exchange candies. Take black candy to the top

				Candy old = this.table[i][column];
				this.table[i][column] = this.table[i - 1][column];
				this.table[i - 1][column] = old;
			}
		}
	}

	private void dropOneColumn(int column, int lastBottomCandyPos) {
		int count = 0;

		for (int i = lastBottomCandyPos; i >= 0; i--) {
			if (this.table[i][column].isBlank()) {
				count++;
			}
		}

		for (int i = 0; i < count; i++) {
			this.dropColumnsInRow(column, lastBottomCandyPos);
		}
	}

	private boolean areInvalidCoordinates(int row, int column) {
		return (row < Constants.MIN_POS || row > Constants.getCurrentSize() - 1) ||
				(column < Constants.MIN_POS || column > Constants.getCurrentSize() - 1);
	}

	private void makeSpecialCandy(int row, int column, int firstLeftCandyPos, int lastRightCandyPos,
	                              int firstTopCandyPos, int lastBottomCandyPos) {
		// Check for ROW special candy
		boolean rowCandy = lastRightCandyPos - firstLeftCandyPos + 1 >= Constants.MIN_CANDIES_FOR_SPECIAL;

		// Check for COLUMN special candy
		boolean columnCandy = lastBottomCandyPos - firstTopCandyPos + 1 >= Constants.MIN_CANDIES_FOR_SPECIAL;

		// Check for ROW_COLUMN special candy
		if (rowCandy && columnCandy) {
			this.table[row][column].setSpecial(CandyType.ROW_COLUMN);
		} else if (rowCandy) {
			this.table[row][column].setSpecial(CandyType.ROW);
		} else if (columnCandy) {
			this.table[row][column].setSpecial(CandyType.COLUMN);
		}

		// Check for BOMB special candy


		// Check for FULL_BOARD special candy

	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("   ");

		/*
		                       |1|1|1|1|1|1|
		   |0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|5|
		 */

		if (this.size >= 10) {
			for (int i = 0; i < this.size; i++) {
				if (i < 10) {
					out.append("  ");
				} else if (i == 10) {
					out.replace(out.length() - 1, out.length(), "").append("|").append(1).append("|");
				} else {
					out.append(Integer.toString(i).charAt(0)).append("|");
				}
			}
		}

		out.append("\n  |");

		for (int i = 0; i < this.size; i++) {
			out.append(i % 10).append("|");
		}

		out.append("\n");

		for (int i = 0; i < this.size; i++) {
			if (i < 10) {
				out.append(" ");
			}

			out.append(i).append("|");

			for (Candy candy : this.table[i]) {
				out.append(candy.toString());
			}

			out.append("\n");
		}

		out.replace(out.length() - 1, out.length(), ""); // Remove the last '\n'

		return out.toString();
	}

	private void slowPrinting() throws InterruptedException {
		logger.trace(this);
		Thread.sleep(800);
	}

	public String debugBoard() {
		StringBuilder out = new StringBuilder("Debugging board\n");

		for (Candy[] row : this.table) {
			for (Candy candy : row) {
				out.append(candy.getLetter());
			}

			out.append("\n");
		}

		return out.toString();
	}
}
