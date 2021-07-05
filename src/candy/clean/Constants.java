package candy.clean;

import java.util.Random;

public final class Constants {
	private Constants() {
		throw new UnsupportedOperationException("Utility class");
	}

	public static final String BLACK = "\u001B[40m";
	public static final String RED = "\u001B[41m";
	public static final String GREEN = "\u001B[42m";
	public static final String YELLOW = "\u001B[43m";
	public static final String BLUE = "\u001B[44m";
	public static final String PURPLE = "\u001B[45m";
	public static final String CYAN = "\u001B[46m";
	public static final String WHITE = "\u001B[47m";
	public static final String RESET = "\u001B[0m";
	public static final Random random = new Random();
	public static final int BASE_SCORE = 100;
	public static final int MIN_SIZE = 4;
	public static final int MAX_SIZE = 30;
	public static final int MIN_POS = 0;
	public static final int MIN_NUM_COLORS = 2;
	public static final int MAX_NUM_COLORS = 7;
	private static int currentSize;

	public static int getCurrentSize() {
		return currentSize;
	}

	public static void setCurrentSize(int currentSize) {
		Constants.currentSize = currentSize;
	}
}
