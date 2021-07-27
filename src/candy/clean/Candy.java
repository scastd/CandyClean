package candy.clean;

public class Candy {
	private Color color;
	private char letter;
	private int special = Constants.NORMAL_CANDY;

	public Candy(char letter) {
		this.setLetterAndColor(letter);
	}

	public Candy(int num) {
		char[] letters = {'R', 'G', 'Y', 'B', 'P', 'C', 'W'};
		this.setLetterAndColor(letters[num]);
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public char getLetter() {
		return this.letter;
	}

	public void setLetterAndColor(char letter) {
		this.letter = letter;

		switch (letter) {
			case 'R':
				this.color = new Color(BackgroundColor.RED);
				break;

			case 'G':
				this.color = new Color(BackgroundColor.GREEN);
				break;

			case 'Y':
				this.color = new Color(BackgroundColor.YELLOW);
				break;

			case 'B':
				this.color = new Color(BackgroundColor.BLUE);
				break;

			case 'P':
				this.color = new Color(BackgroundColor.PURPLE);
				break;

			case 'C':
				this.color = new Color(BackgroundColor.CYAN);
				break;

			case 'W':
				this.color = new Color(BackgroundColor.WHITE);
				break;

			default:
				this.color = new Color(BackgroundColor.BLACK);
		}
	}

	public boolean isSpecial() {
		return this.special != Constants.NORMAL_CANDY;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public void setToBlank() {
		this.setLetterAndColor('E');
	}

	public boolean isBlank() {
		return this.letter == 'E';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Candy) return this.letter == ((Candy) o).letter;

		return false;
	}

	@Override
	public String toString() {
		String out;

		if (this.isSpecial()) {
			switch (this.special) {
				case Constants.ROW:
					out = Constants.BLACK_BLINK + "RR";
					break;

				case Constants.COLUMN:
					out = Constants.BLACK_BLINK + "CC";
					break;

				case Constants.ROW_COLUMN:
					out = Constants.BLACK_BLINK + "RC";
					break;

				case Constants.BOMB:
					out = Constants.BLACK_BLINK + "BB";
					break;

				case Constants.FULL_BOARD:
					out = Constants.BLACK_BLINK + "FF";
					break;

				default:
					out = "  ";
			}
		} else {
			out = "  ";
		}

		return this.toString(out);
	}

	public String toString(String str) {
		return this.color.toString(str);
	}
}
