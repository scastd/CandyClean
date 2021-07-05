package candy.clean;

import org.jetbrains.annotations.NotNull;

public class Board {
	private final Candy[][] table;
	private final int size;
	private final int numColors;

	public Board(boolean predefined, int size, int numColors) throws CandyCleanException {
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

		if (predefined) {
			this.table = this.generatePredefinedBoard(); // Todo: Hacer predefinido en Game
			this.size = this.table.length;
			this.numColors = 3;
		} else {
			this.table = this.generateRandomBoard(size, numColors);
			this.size = size;
			this.numColors = numColors;
		}

		Constants.setCurrentSize(this.size);
	}

	private @NotNull Candy[][] generateRandomBoard(int size, int numColors) throws CandyCleanException {
		Candy[][] candies = new Candy[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				candies[i][j] = new Candy(Math.abs(Constants.random.nextInt(numColors)));
			}
		}

		return candies;
	}

	private @NotNull Candy[][] generatePredefinedBoard() throws CandyCleanException {
		String[] predefBoard = {"RRRG", "BBRR", "RBGG", "GBRR"};
		Candy[][] candies = new Candy[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				candies[i][j] = new Candy(predefBoard[i].charAt(j));
			}
		}

		return candies;
	}

	// Todo: Comprobar si hay caramelos del mismo color alrededor. Si se falla, actualizar puntuación
	public void shoot(int row, int column) throws CandyCleanException {
		if (this.areInvalidCoordinates(row, column)) {
			throw new CandyCleanException("Invalid coordinates");
		}

		int firstLeftCandyPos = this.firstLeftCandyPos(row, column);
		int lastRightCandyPos = this.lastRightCandyPos(row, column);
		int firstTopCandyPos = this.firstTopCandyPos(row, column);
		int lastBottomCandyPos = this.lastBottomCandyPos(row, column);

		for (int i = firstLeftCandyPos; i < lastRightCandyPos; i++) {
			this.table[row][i].setToBlank();
		}

		for (int i = firstTopCandyPos; i < lastBottomCandyPos; i++) {
			this.table[i][column].setToBlank();
		}
		this.dropCandies(row,
				column,
				firstLeftCandyPos,
				lastRightCandyPos,
				lastBottomCandyPos);
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

		return rightCandyPos;
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

		return bottomCandyPos;
	}

	private void dropCandies(int row, int column, int firstLeftCandyPos, int lastRightCandyPos, int lastBottomCandyPos) throws CandyCleanException {
		for (int i = firstLeftCandyPos; i < lastRightCandyPos; i++) {
			this.dropEachColumn(row, i);
			this.dropOneColumn(column, lastBottomCandyPos);
		}
	}

	private void dropEachColumn(int initialRow, int column) throws CandyCleanException {
		for (int i = initialRow; i >= 0; i--) {
			if (i == 0) {
				this.table[i][column] = new Candy(Math.abs(Constants.random.nextInt(this.numColors)));
			} else {
				this.table[i][column] = this.table[i - 1][column]; // Exchange candies
			}
		}
	}

	// Todo: Pasar como parámetro las coordenadas (Top y bottom), luego mover desde top hasta bottom
	//  Y si top es 0, generar hasta bottom caramelos aleatorios
	private void dropOneColumn(int column, int lastBottomCandyPos) throws CandyCleanException {
		int count = 0;

		for (int i = lastBottomCandyPos; i >= 0; i--) {
			if (this.table[i][column].isBlank()) {
				count++;
			}
		}

		for (int i = lastBottomCandyPos; i > count; i--) {
			this.table[i][column] = this.table[i - count][column];
		}

		for (int i = 0; i < count; i++) {
			this.table[i][column] = new Candy(Math.abs(Constants.random.nextInt(this.numColors)));
		}
	}

	private boolean areInvalidCoordinates(int row, int column) {
		return (row < Constants.MIN_POS || row > Constants.getCurrentSize() - 1) ||
				(column < Constants.MIN_POS || column > Constants.getCurrentSize() - 1);
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
				out.append(candy.toString("  "));
			}

			out.append("\n");
		}

		out.replace(out.length() - 1, out.length(), ""); // Remove the last '\n'

		return out.toString();
	}

	// Todo: Hacer método para hacer mejor debug en fichero
}
